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
		int NBR_TOTAL_CONTRAINTES = 0;
		for(int j=0; j<m.nbrJobs; j++) {
			Job job = m.jobs.get(j);
			m.C13[j] = cplex.addGe(m.varDelay[j], 0, "C13("+j+")"); 
			NBR_TOTAL_CONTRAINTES++;
			Integer nbrOpJ = job.getOperations().size();
			m.C1[j] = new IloRange[nbrOpJ][][]; 
			m.C2[j] = new IloRange[nbrOpJ];
			m.C3[j] = new IloRange[nbrOpJ][][];
			m.C4[j] = new IloRange[nbrOpJ][][];
			m.C5[j] = new IloRange[nbrOpJ][][];
			m.C7[j] = new IloRange[nbrOpJ];
			m.C8[j] = new IloRange[nbrOpJ];
			m.C14[j] = new IloRange[nbrOpJ];
			m.C15[j] = new IloRange[nbrOpJ][][];
			for(int q=0; q<nbrOpJ; q++) {
				m.C1[j][q] = new IloRange[m.nbrJobs][];
				m.C3[j][q] = new IloRange[m.nbrJobs][]; 
				m.C4[j][q] = new IloRange[m.nbrJobs][]; 
				m.C5[j][q] = new IloRange[m.nbrJobs][];
				m.C15[j][q] = new IloRange[m.nbrJobs][];
				for(int i=0; i<m.nbrJobs; i++) {
					if(i != j) {
						Job job2 = m.jobs.get(i);
						Integer nbrOpI = job2.getOperations().size();
						m.C1[j][q][i] = new IloRange[nbrOpI];
						m.C3[j][q][i] = new IloRange[nbrOpI];
						m.C4[j][q][i] = new IloRange[nbrOpI];
						m.C5[j][q][i] = new IloRange[nbrOpI];
						m.C15[j][q][i] = new IloRange[nbrOpI];
						for(int k=0; k<nbrOpI; k++) {
							IloLinearNumExpr c1 = cplex.linearNumExpr();
							c1.addTerm(1.0, m.varPrecedence[j][q][i][k]);
							c1.addTerm(1.0,  m.varPrecedence[i][k][j][q]);
							m.C1[j][q][i][k] = cplex.addEq(c1, 1, "C1("+j+","+q+","+i+","+k+")");
							NBR_TOTAL_CONTRAINTES++;
							
							IloLinearNumExpr c3 = cplex.linearNumExpr();
							c3.addTerm(1.0, m.varWeld[j][q]);
							c3.addTerm(-1.0, m.varWeld[i][k]);
							c3.addTerm(-1.0 * job2.getPositionTime(), m.varMode[i][k][1]);
							c3.addTerm(-m.I, m.varPrecedence[i][k][j][q]);
							c3.addTerm(-m.I, m.varMode[i][k][0]);
							c3.addTerm(-m.I, m.varMode[i][k][2]);
							m.C3[j][q][i][k] = cplex.addGe(c3, job2.getOperations().get(k).getProcessingTime() -(2 * m.I), "C3("+j+","+q+","+i+","+k+")");
							NBR_TOTAL_CONTRAINTES++;
							
							IloLinearNumExpr c4 = cplex.linearNumExpr();
							c4.addTerm(1.0, m.varWeld[j][q]);
							c4.addTerm(-1.0, m.varWeld[i][k]);
							c4.addTerm(-1.0 * job2.getPositionTime(), m.varMode[i][k][1]);
							c4.addTerm(-m.I, m.varPrecedence[i][k][j][q]);
							c4.addTerm(-m.I, m.varMode[j][q][0]);
							c4.addTerm(-m.I, m.varMode[j][q][1]);
							m.C4[j][q][i][k] = cplex.addGe(c4, job2.getOperations().get(k).getProcessingTime() -(2 * m.I), "C4("+j+","+q+","+i+","+k+")");
							NBR_TOTAL_CONTRAINTES++;
							
							IloLinearNumExpr c5 = cplex.linearNumExpr();
							c5.addTerm(1.0, m.varWeld[j][q]);
							c5.addTerm(-1.0 * job2.getPositionTime(), m.varMode[i][k][1]);
							c5.addTerm(-1.0 * m.I, m.varPrecedence[i][k][j][q]);
							m.C5[j][q][i][k] = cplex.addGe(c5, -m.I, "C5("+j+","+q+","+i+","+k+")");
							NBR_TOTAL_CONTRAINTES++;
							
							IloLinearNumExpr c15 = cplex.linearNumExpr();
							c15.addTerm(1.0, m.varDelay[j]);
							c15.addTerm(-1.0, m.varWeld[i][k]);
							c15.addTerm(-job2.getPositionTime(), m.varMode[i][k][1]);
							c15.addTerm(-m.I, m.varPrecedence[j][q][i][k]);
							c15.addTerm(-m.I, m.varMode[j][q][1]);
							c15.addTerm(-m.I, m.varMode[i][k][2]);
							c15.addTerm(m.I, m.varWeld[i][k]);
							c15.addTerm(-m.I, m.varWeld[j][q]);
							m.C15[j][q][i][k] = cplex.addGe(c15, job2.getOperations().get(k).getProcessingTime() - job.getDueDate() -3*m.I + m.I*job.getPositionTime(),
									"C15("+j+","+q+","+i+","+k+")");
							NBR_TOTAL_CONTRAINTES++;
						}
					}
				}
				if(q > 0) {
					IloLinearNumExpr c2 = cplex.linearNumExpr();
					c2.addTerm(1.0, m.varWeld[j][q]);
					c2.addTerm(-1.0, m.varWeld[j][q-1]);
					c2.addTerm(-job.getPositionTime(), m.varMode[j][q-1][1]);
					m.C2[j][q] =  cplex.addGe(c2, job.getOperations().get(q-1).getProcessingTime(), "C2("+j+","+q+")");
					NBR_TOTAL_CONTRAINTES++;
				}
				IloLinearNumExpr c7 = cplex.linearNumExpr();
				for(int o=0; o<m.nbrModes; o++) {
					c7.addTerm(1.0, m.varMode[j][q][o]);
				}
				m.C7[j][q] = cplex.addEq(c7, 1, "C7("+j+","+q+")");
				m.C8[j][q] = cplex.addEq(m.varMode[j][q][2], job.getOperations().get(q).getWeldingProcess().equals(2)? 1 : 0, "C8("+j+","+q+")");
				NBR_TOTAL_CONTRAINTES++;
				
				IloLinearNumExpr c14 = cplex.linearNumExpr();
				c14.addTerm(1.0, m.varDelay[j]);
				c14.addTerm(-1.0, m.varWeld[j][q]);
				c14.addTerm(-job.getPositionTime(), m.varMode[j][q][1]);
				m.C14[j][q] =  cplex.addGe(c14, job.getOperations().get(q).getProcessingTime() - job.getDueDate(), "C14("+j+","+q+")");	
				NBR_TOTAL_CONTRAINTES++;
			}
			IloLinearNumExpr c6 = cplex.linearNumExpr();
			IloLinearNumExpr c9 = cplex.linearNumExpr();
			c6.addTerm(1.0, m.varWeld[j][0]);
			m.C11[j] = new IloRange[m.nbrLoadStations][][];
			m.C12[j] = new IloRange[m.nbrLoadStations][][][][];
			for(int l=0; l<m.nbrLoadStations; l++) {
				c9.addTerm(1.0, m.varLoad[j][l]);
				c6.addTerm(-1.0, m.varBegin[j][l]);
				m.C11[j][l] = new IloRange[m.nbrJobs][];
				m.C12[j][l] = new IloRange[m.nbrJobs][][][];
				for(int i=0; i<m.nbrJobs; i++) {
					if(i != j) {
						Job job2 = m.jobs.get(i);
						Integer nbrOpI = job2.getOperations().size();
						m.C11[j][l][i] = new IloRange[nbrOpI];
						m.C12[j][l][i] = new IloRange[nbrOpI][m.nbrJobs][];
						for(int q=0; q<nbrOpI; q++) {
							IloLinearNumExpr c11 = cplex.linearNumExpr();
							c11.addTerm(1.0, m.varBegin[j][l]);
							c11.addTerm(-1.0, m.varWeld[i][q]);
							c11.addTerm(-job2.getPositionTime(), m.varMode[i][q][1]);
							c11.addTerm(-m.I, m.varPrecedence[i][0][j][0]);
							c11.addTerm(-m.I, m.varLoad[i][l]);
							c11.addTerm(-m.I, m.varLoad[j][l]);
							m.C11[j][l][i][q] = cplex.addGe(c11, job2.getOperations().get(q).getProcessingTime() - 3*m.I, 
									"C11("+j+","+l+","+i+","+q+")");
							NBR_TOTAL_CONTRAINTES++;
							
							m.C12[j][l][i][q] = new IloRange[m.nbrJobs][];
							for(int y=0; y<m.nbrJobs; y++) {
								if(y != j && y != i) {
									Job job3 = m.jobs.get(y);
									Integer nbrOpY = job3.getOperations().size();
									m.C12[j][l][i][q][y] = new IloRange[nbrOpY];
									for(int k=0; k<nbrOpY; k++) {
										IloLinearNumExpr c12 = cplex.linearNumExpr();
										c12.addTerm(1.0, m.varBegin[j][l]);
										c12.addTerm(-1.0,  m.varWeld[y][k]);
										c12.addTerm(-job3.getPositionTime(), m.varMode[y][k][1]);
										c12.addTerm(-m.I, m.varPrecedence[i][q][y][k]);
										c12.addTerm(-m.I, m.varMode[i][q][1]);
										c12.addTerm(-m.I, m.varMode[y][k][2]);
										c12.addTerm(m.I, m.varWeld[y][k]);
										c12.addTerm(-m.I, m.varWeld[i][q]);
										c12.addTerm(-m.I, m.varPrecedence[i][0][j][0]);
										c12.addTerm(-m.I, m.varLoad[i][l]);
										c12.addTerm(-m.I, m.varLoad[j][l]);
										m.C12[j][l][i][q][y][k] = cplex.addGe(c12, job3.getOperations().get(k).getProcessingTime() - 6*m.I + job2.getPositionTime()*m.I, 
												"C12("+j+","+l+","+i+","+q+","+y+","+k+")");
										NBR_TOTAL_CONTRAINTES++;
									}
								}
							}
						}
					}
				}
			}
			m.C6[j] = cplex.addGe(c6, 0, "C6("+j+")");
			NBR_TOTAL_CONTRAINTES++;
			m.C9[j] = cplex.addEq(c9, 1, "C9("+j+")");
			NBR_TOTAL_CONTRAINTES++;
			m.C10[j] = cplex.addGe(m.varLoad[j][1], job.isSize()? 1 : 0, "C10("+j+")");
			NBR_TOTAL_CONTRAINTES++;
		}
		System.out.println("==> Nombre total de contraintes générées "+NBR_TOTAL_CONTRAINTES);
	}

	/**
	 * Solve the problem with Cplex
	 * @param problem
	 */
	public void solve(Instance problem) {
		try {		
			IloCplex cplex = new IloCplex();
			Model m = new Model(problem);		
			createVariables(cplex, m);
			createContraintes(cplex, m);
			cplex.exportModel("lpex1.lp");
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
