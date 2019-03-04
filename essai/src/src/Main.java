package src;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.cplex.*;

public class Main {

	public static void main(String[] args) {
		try {
	         IloCplex cplex = new IloCplex();
	         
	         // Essai de création de variables format unique ou tableau
	         // problème de création de bound ? comment connaitres les bounds ?
	         IloNumVar x1 = cplex.numVar(0, 0, IloNumVarType.Float, "x1");
	         IloNumVar[] x = cplex.numVarArray(3, 0.0, 100.0);
	         
	         // Une experession pour la fonction objectif ou les contraintes
	         IloNumExpr expr = cplex.sum(x[0], 
                     cplex.prod(6.0, x[1]),
                     cplex.prod(5.0, x[2]));
	         
	         // Autre méthode de création d'expression (meilleur pour les boucles notamment)
	         IloLinearNumExpr expr2 = cplex.linearNumExpr();
	         expr2.addTerm(1.0, x[0]);
	         expr2.addTerm(2.0, x[1]);
	         expr2.addTerm(3.0, x[2]);	         
	         
	         // Ajouter une contrainte sous le format expr < value
	         cplex.addLe(expr2, 20, "c1");
	         
	         // Ajouter une fonction objectif
	         cplex.maximize(expr);
	         
	         //Résoudre le problème
	         System.out.println("==> Solve : ");
	         cplex.solve();
	         
	         //en savoir plus sur la solution
	         System.out.println("==> Status : ");
	         System.out.println(cplex.getStatus());
	         
	         // Récuperer les valeurs des variables
	         double[] xval = cplex.getValues(x);
	         for(double v : xval) {
	        	 System.out.println(v);
	         }
	         
	       } catch (IloException e) {
	          System.err.println("Concert exception caught: " + e);
	       }
	}
}