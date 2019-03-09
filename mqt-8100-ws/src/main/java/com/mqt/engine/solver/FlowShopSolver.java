package com.mqt.engine.solver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;
import com.mqt.pojo.vo.InstanceVo;
import com.mqt.services.InstanceService;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

/**
 * Module de résolution avec le Solver Cplex de problèmes de Flow Shop Basé sur
 * le modèle de position
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/03/2019
 */
@Service("flowShopSolver")
public class FlowShopSolver extends Thread{

	/**
	 * Injection of dependencies
	 */
	@Autowired
	private InstanceService service;

	/**
	 * Solve with Cplex an flow shop instance i
	 * @param problem
	 */
	public void solve(FlowShopInstanceDto problem, InstanceVo database) {
		try {

			// Instantiation du solver (2 types de variables et 4 types de contraintes)
			IloCplex cplex = new IloCplex();
			IloNumVar[][][] var = new IloNumVar[2][][];
			IloRange[][] rng = new IloRange[4][];

			// Récupération des données de base
			Integer nbrJobs = problem.getJobs().size();
			Integer nbrMachines = problem.getJobs().get(0).getProcessingTimes().size();

			// Variables Xjp (booleénne) : le job j en position p
			IloNumVar[][] positionVariables = new IloNumVar[nbrJobs][nbrJobs];
			for (int i = 0; i < nbrJobs; i++) {
				String[] names = new String[nbrJobs];
				for(int j = 0; j < nbrJobs; j++) {
					names[j] = "X"+i+""+j;
				}
				positionVariables[i] = cplex.numVarArray(problem.getJobs().size(), 0, 1, names);
			}
			var[0] = positionVariables;

			// Variables Fip (réelle) : la date de fin en machine i d'une job en position p
			IloNumVar[][] endDateVariables = new IloNumVar[nbrMachines][nbrJobs];
			for(int i=0; i < nbrMachines; i++) {
				String[] names = new String[nbrJobs];
				for(int p = 0; p < nbrJobs; p++) {
					names[p] = "F"+i+""+p;
				}
				endDateVariables[i] = cplex.numVarArray(nbrJobs, 0.0, Double.MAX_VALUE, names);
			}
			var[1] = endDateVariables;

			// Fonction objectif : minimiser la date de fin de la dernière machine / dernier job
			cplex.addMinimize(var[1][nbrMachines-1][nbrJobs-1]);

			// Contraintes n°1 : un seul job par position
			// Contraintes n°2 : une seule position pour un job 
			rng[0] = new IloRange[nbrJobs];
			rng[1] = new IloRange[nbrJobs];
			for(int p=0; p<nbrJobs; p++) {
				IloLinearNumExpr expr1 = cplex.linearNumExpr();
				IloLinearNumExpr expr2 = cplex.linearNumExpr();
				for(int j=0; j<nbrJobs; j++) {
					expr1.addTerm(1.0, var[0][j][p]);
					expr2.addTerm(1.0, var[0][p][j]);
				}
				rng[0][p] = cplex.addEq(expr1, 1);
				rng[1][p] = cplex.addEq(expr2, 1);;
			}

			// Contraintes n°3 : Fip >= Fi(p-1) pour p>1
			rng[2] = new IloRange[nbrJobs-1];
			for(int p=1; p<nbrJobs; p++) {
				for(int i=0; i<nbrMachines; i++) {
					IloLinearNumExpr expr = cplex.linearNumExpr();
					expr.addTerm(1.0, var[1][i][p]);
					expr.addTerm(-1.0, var[1][i][p-1]);
					for(int j=0; j<nbrJobs; j++) {
						Double coef = problem.getJobs().get(j).getProcessingTimes().get(i) * -1.0;
						expr.addTerm(coef, var[0][j][p]);
					}
					rng[2][i] = cplex.addGe(expr, 0);
				}
			}

			// Contraintes n°4 : Fip >= F(i-1)p pour i>1
			rng[3] = new IloRange[nbrJobs];
			for(int p=0; p<nbrJobs; p++) {
				for(int i=1; i<nbrMachines; i++) {
					IloLinearNumExpr expr = cplex.linearNumExpr();
					expr.addTerm(1.0, var[1][i][p]);
					expr.addTerm(-1.0, var[1][i-1][p]);
					for(int j=0; j<nbrJobs; j++) {
						Double coef = problem.getJobs().get(j).getProcessingTimes().get(i) * -1.0;
						expr.addTerm(coef, var[0][j][p]);
					}
					rng[3][p] = cplex.addGe(expr, 0);
				}
			}

			//Ecrire le model dans une fichier pour vérification
			cplex.exportModel("lpex1.lp");

			// Résolution du problème et affichage du résultat
			if (cplex.solve()) {
				cplex.output().println("=== Résolution du problème n°"+problem.getId()+"");
				cplex.output().println("Solution status = " + cplex.getStatus());
				cplex.output().println("Solution value  = " + cplex.getObjValue());
				Double optimal = new Double(cplex.getObjValue());
				service.createOrUpdate(database.setOptimal(optimal.intValue()));
			}
			cplex.end();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
}
