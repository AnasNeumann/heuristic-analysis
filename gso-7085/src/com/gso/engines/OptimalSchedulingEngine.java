package com.gso.engines;

import com.gso.model.Instance;
import com.gso.model.Job;
import com.gso.model.Model;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

/**
 * Classe de résolution d'une instance à l'aide de Cplex
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @author Safwan Al-Shabakji
 * @since 08/03/2019
 * @version 1.0
 */
public class OptimalSchedulingEngine {
	private static OptimalSchedulingEngine engine;

	private OptimalSchedulingEngine() {
		
	}

	/**
	 * Récupérer le signleton
	 * @return
	 */
	public static OptimalSchedulingEngine getEngine() {
		if(null == engine) {
			engine = new OptimalSchedulingEngine();
		}
		return engine;
	}
	
	/**
	 * Création de l'ensemble des variables
	 * @param cplex
	 * @param m
	 * @throws IloException
	 */
	private void createVariables(IloCplex cplex, Model m) throws IloException {
		String[] namesDelay = new String[m.nbrJobs];
		for(int j=0; j<m.nbrJobs; j++) {
			namesDelay[j] = "D"+j;
			Integer nbrOpJ = m.jobs.get(j).getOperations().size();
			String[] namesLoads = new String[m.nbrLoadStations];
			String[] namesBegin = new String[m.nbrLoadStations];
			String[] namesWeld = new String[nbrOpJ];
			m.varMode[j] = new IloNumVar[nbrOpJ][m.nbrModes];
			m.varPrecedence[j] = new IloNumVar[nbrOpJ][m.nbrJobs][];
			for(int q = 0; q <nbrOpJ; q++) {
				namesWeld[q] = "W("+j+","+q+")";
				String[] namesModes = new String[m.nbrModes];
				for(int o = 0; o<m.nbrModes; o++) {
					namesModes[o] = "M("+j+","+q+","+o+")"; 
				}
				for(int i=0; i<m.nbrJobs; i++) {
					Integer nbrOpI = m.jobs.get(i).getOperations().size();
					String[] namesPrecedences = new String[nbrOpI];
					for(int k = 0; k <nbrOpI; k++) {
						namesPrecedences[k] = "P("+j+","+q+","+i+","+k+")";
					}
					m.varPrecedence[j][q][i] = cplex.numVarArray(nbrOpI, 0, 1, namesPrecedences);
				}
				m.varMode[j][q] = cplex.numVarArray(m.nbrModes, 0, 1, namesModes);
			}
			for(int l=0; l<m.nbrLoadStations; l++) {
				namesLoads[l] = "L("+j+","+l+")";
				namesBegin[l] = "B("+j+","+l+")";
			}
			m.varWeld[j] = cplex.numVarArray(nbrOpJ, 0.0, Double.MAX_VALUE, namesWeld);
			m.varLoad[j] = cplex.numVarArray(m.nbrLoadStations, 0, 1, namesLoads);
			m.varBegin[j] = cplex.numVarArray(m.nbrLoadStations, 0.0, Double.MAX_VALUE, namesBegin);
		}
		m.varDelay = cplex.numVarArray(m.nbrJobs, 0.0, Double.MAX_VALUE, namesDelay);
	}
	
	/**
	 * Création de l'ensemble des contraintes
	 * @param cplex
	 * @param m
	 * @throws IloException
	 */
	private void createContraintes(IloCplex cplex, Model m) throws IloException {
		for(int j=0; j<m.nbrJobs; j++) {
			Job job = m.jobs.get(j);
			m.C13[j] = cplex.addGe(m.varDelay[j], 0, "C13("+j+")"); 
			Integer nbrOpJ = job.getOperations().size();
			m.C1[j] = new IloRange[nbrOpJ][][]; 
			m.C2[j] = new IloRange[nbrOpJ];
			m.C3[j] = new IloRange[nbrOpJ][][];
			m.C4[j] = new IloRange[nbrOpJ][][];
			m.C5[j] = new IloRange[nbrOpJ][][];
			m.C7[j] = new IloRange[nbrOpJ];
			m.C8[j] = new IloRange[nbrOpJ];
			for(int q=0; q<nbrOpJ; q++) {
				m.C1[j][q] = new IloRange[m.nbrJobs][];
				m.C3[j][q] = new IloRange[m.nbrJobs][]; 
				m.C4[j][q] = new IloRange[m.nbrJobs][]; 
				m.C5[j][q] = new IloRange[m.nbrJobs][]; 
				for(int i=0; i<m.nbrJobs; i++) {
					if(i != j) {
						Job job2 = m.jobs.get(i);
						Integer nbrOpI = job2.getOperations().size();
						m.C1[j][q][i] = new IloRange[nbrOpI];
						m.C3[j][q][i] = new IloRange[nbrOpI];
						m.C4[j][q][i] = new IloRange[nbrOpI];
						m.C5[j][q][i] = new IloRange[nbrOpI];
						for(int k=0; k<nbrOpI; k++) {
							IloLinearNumExpr c1 = cplex.linearNumExpr();
							c1.addTerm(1.0, m.varPrecedence[j][q][i][k]);
							c1.addTerm(1.0,  m.varPrecedence[i][k][j][q]);
							m.C1[j][q][i][k] = cplex.addEq(c1, 1, "C1("+j+","+q+","+i+","+k+")");

							IloLinearNumExpr c3 = cplex.linearNumExpr();
							c3.addTerm(1.0, m.varWeld[j][q]);
							c3.addTerm(-1.0, m.varWeld[i][k]);
							c3.addTerm(-1.0 * job2.getPositionTime(), m.varMode[i][k][1]);
							c3.addTerm(-m.I, m.varPrecedence[i][k][j][q]);
							c3.addTerm(-m.I, m.varMode[i][k][0]);
							c3.addTerm(-m.I, m.varMode[i][k][2]);
							m.C3[j][q][i][k] = cplex.addGe(c3, job2.getOperations().get(k).getProcessingTime() -(2 * m.I), "C3("+j+","+q+","+i+","+k+")");

							IloLinearNumExpr c4 = cplex.linearNumExpr();
							c4.addTerm(1.0, m.varWeld[j][q]);
							c4.addTerm(-1.0, m.varWeld[i][k]);
							c4.addTerm(-1.0 * job2.getPositionTime(), m.varMode[i][k][1]);
							c4.addTerm(-m.I, m.varPrecedence[i][k][j][q]);
							c4.addTerm(-m.I, m.varMode[j][q][0]);
							c4.addTerm(-m.I, m.varMode[j][q][1]);
							m.C4[j][q][i][k] = cplex.addGe(c4, job2.getOperations().get(k).getProcessingTime() -(2 * m.I), "C4("+j+","+q+","+i+","+k+")");

							IloLinearNumExpr c5 = cplex.linearNumExpr();
							c5.addTerm(1.0, m.varWeld[j][q]);
							c5.addTerm(-1.0 * job2.getPositionTime(), m.varMode[i][k][1]);
							c5.addTerm(-1.0 * m.I, m.varPrecedence[i][k][j][q]);
							m.C5[j][q][i][k] = cplex.addGe(c5, -m.I, "C5("+j+","+q+","+i+","+k+")");
						}
					}
				}
				if(q > 0) {
					IloLinearNumExpr c2 = cplex.linearNumExpr();
					c2.addTerm(1.0, m.varWeld[j][q]);
					c2.addTerm(-1.0, m.varWeld[j][q-1]);
					c2.addTerm(-1.0 * job.getPositionTime(), m.varMode[j][q-1][1]);
					m.C2[j][q] =  cplex.addGe(c2, job.getOperations().get(q-1).getProcessingTime(), "C2("+j+","+q+")");
				}
				IloLinearNumExpr c7 = cplex.linearNumExpr();
				for(int o=0; o<m.nbrModes; o++) {
					c7.addTerm(1.0, m.varMode[j][q][o]);
				}
				m.C7[j][q] = cplex.addEq(c7, 1, "C7("+j+","+q+")");
				m.C8[j][q] = cplex.addEq(m.varMode[j][q][2], job.getOperations().get(q).getWeldingProcess().equals(2)? 1 : 0, "C8("+j+","+q+")");
			}
			IloLinearNumExpr c9 = cplex.linearNumExpr();
			for(int l=0; l<m.nbrLoadStations; l++) {
				c9.addTerm(1.0, m.varLoad[j][l]);
			}
			m.C9[j] = cplex.addEq(c9, 1, "C9("+j+")");
			m.C10[j] = cplex.addGe(m.varLoad[j][1], job.isSize()? 1 : 0, "C10("+j+")");
		}
	}

	/**
	 * Solve the problem with Cplex
	 * @param problem
	 */
	public void solve(Instance problem) {
		try {		
			// Instantiation du solver
			IloCplex cplex = new IloCplex();
			Model m = new Model(problem);
			
			// Initialisation des variables
			createVariables(cplex, m);
			
			// Création des contraintes
			createContraintes(cplex, m);
			
			//Ecrire le model dans une fichier pour vérification
			cplex.exportModel("lpex1.lp");

			// Résolution du problème et affichage du résultat
			if (cplex.solve()) {
				cplex.output().println("Solution status = " + cplex.getStatus());
				cplex.output().println("Solution value  = " + cplex.getObjValue());
			}
			cplex.end();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}

}
