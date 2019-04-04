package com.gso.engines;

import java.util.Collections;
import java.util.Date;

import com.gso.model.Instance;
import com.gso.solution.CeduledJob;
import com.gso.solution.CeduledOperation;
import com.gso.solution.Solution;

/**
 * Engin de recherche dans un voisinage multiple (Heuristique hybride)
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/03/2019
 * @version 1.0
 */
public class MultiNeighborhoodSearchEngin {
	private static MultiNeighborhoodSearchEngin engine;

	private MultiNeighborhoodSearchEngin() {

	}

	/**
	 * Récupérer le singleton
	 * 
	 * @return
	 */
	public static MultiNeighborhoodSearchEngin getEngine() {
		if (null == engine) {
			engine = new MultiNeighborhoodSearchEngin();
		}
		return engine;
	}

	/**
	 * Solve the problem
	 * @param problem
	 * @param MT
	 * @param LT
	 */
	public Solution solve(Instance problem, Double MT, Double LT) {
		Long startTime = System.currentTimeMillis();
		Solution s = buildInitialSolution(problem, MT, LT);
		for(int index = 0; index < s.getJobs().size()-1; index++) {
			if(s.computeAverageDelay().equals(0.0)) {
				displaySolution(s, startTime, System.currentTimeMillis());
				return s;
			}
			Solution newSolution = localSearch(s, index);
			if(newSolution.computeAverageDelay() <= s.computeAverageDelay()) {
				s = newSolution;	
			}
		}
		displaySolution(s, startTime, System.currentTimeMillis());
		return s;
	}

	/**
	 * Build a first version of the solution
	 * @param i
	 * @param MT
	 * @param LT
	 * @return
	 */
	public Solution buildInitialSolution(Instance i, Double MT, Double LT) {
		Solution s = Solution.fromInstance(i, MT, LT);
		Collections.sort(s.getJobs());
		int position = 0;
		for(CeduledJob j : s.getJobs()) {
			for(CeduledOperation o : j.getCeduledOperations()) {
				o.setPosition(position);
				o.setMode(o.getWeldingProcess().equals(1)? CeduledOperation.MODE_A : CeduledOperation.MODE_C_ALONE);
				position ++;
			}
		}
		return amelioration(s);
	}

	/**
	 * Améliorer une solution en cherchant à y intéger du paralélisme
	 * @param s
	 * @return
	 */
	public Solution amelioration(Solution s) {
		// TODO la méthode d'amélioration
		return s;
	}

	/**
	 * chercher à intervetir deux pièces à partir d'une position (en améliorant la solution trouvée)
	 * @param s
	 * @param index
	 * @return
	 */
	public Solution localSearch(Solution s, int index) {
		// TODO la méthode de recherche dans le voisinage
		return amelioration(s);
	}
	
	/**
	 * Display a solution in details
	 * @param s
	 */
	public void displaySolution(Solution s, Long start, Long end) {
		System.out.println("\n=== RESULTATS DE L'HEURISTIQUE ===");
		displayPerformance(start,end);
		System.out.println("=> Retard moyen final = "+s.computeAverageDelay());
		for(CeduledJob j : s.getJobs()) {
			System.out.println("\n--- Pièce n°"+j.getId()+" chargée sur la station "+j.getLoadedStation()+" à "+j.getLoadedDate()+" minutes ---");
			System.out.println("-> Fin (déchargée) à "+j.getEndDate()+" avec une due date à "+j.getDueDate());
			if(j.isRemoved()) {
				System.out.println("-> Cette pièce à été déchargée de la station "+j.getLoadingHistory()+" !");
			}
			System.out.println("\nOPERATIONS :");
			for(CeduledOperation o : j.getCeduledOperations()) {
				System.out.println("");
				System.out.println("Opération n°"+(j.getCeduledOperations().indexOf(o)+1)+" débutée à "+o.getBeginDate()+" finie (libre) à "+o.getEndDate());
				System.out.println("--> Cette opération a été réalisée en postion : "+o.getPosition());
				System.out.println("--> Cette opération a été réalisée en mode : "+o.modeToString());
			}
			System.out.println("\n------------------------------------------------");
		}
	}
	
	/**
	 * Afficher la durée totale d'éxécution du programme
	 * @param start
	 * @param end
	 */
	public void displayPerformance(Long start, Long end) {
		Date dateDebut = new Date (start);
		Date dateFin = new Date (end);
		Date duree = new Date (end);
		duree.setTime (dateFin.getTime () - dateDebut.getTime());
		long secondes = duree.getTime () / 1000;
		long min = secondes / 60;
		long heures = min / 60;
		long mili = duree.getTime () % 1000;
		secondes %= 60;
		System.out.println ("Temps total de traitement : "+heures+" heures; " + min + " minutes;  " + secondes + " segondes; " + mili);
	}
}