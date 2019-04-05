package com.gso.engines;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;

import com.gso.model.Instance;
import com.gso.solution.CeduledJob;
import com.gso.solution.CeduledOperation;
import com.gso.solution.DateManager;
import com.gso.solution.Solution;
import com.gso.solution.StationManager;

/**
 * Engin de recherche dans un voisinage multiple (Heuristique hybride)
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/03/2019
 * @version 1.0
 */
public class MultiNeighborhoodSearchEngin {
	private static MultiNeighborhoodSearchEngin engine;

	/**
	 * Private constuctor
	 */
	private MultiNeighborhoodSearchEngin() {

	}

	/**
	 * Récupérer le singleton
	 * 
	 * @return the engine
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
		for(int index = 0; index < s.getJobs().size() - 1; index++) {
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
		return amelioration(runSolution(s));
	}

	/**
	 * Améliorer une solution en cherchant à y intéger du paralélisme
	 * @param s
	 * @return
	 */
	public Solution amelioration(Solution s) {
		Double currentDelay = s.computeAverageDelay();
		for(CeduledJob j : s.getJobs()) {
			for(CeduledOperation o : j.getCeduledOperations()) {
				switch (o.getMode()) {
					case CeduledOperation.MODE_A:
						o.setMode(CeduledOperation.MODE_B);
						runSolution(s);
						if(currentDelay < s.computeAverageDelay()) {
							o.setMode(CeduledOperation.MODE_A);
						}
						break;
					case CeduledOperation.MODE_C_ALONE:
						o.setMode(CeduledOperation.MODE_C_PARALLEL);
						runSolution(s);
						if(currentDelay < s.computeAverageDelay()) {
							o.setMode(CeduledOperation.MODE_C_ALONE);
						}
						break;
				}
			}
		}
		return runSolution(s);
	}

	/**
	 * chercher à intervetir deux pièces à partir d'une position (en améliorant la solution trouvée)
	 * @param s
	 * @param index
	 * @return
	 */
	public Solution localSearch(Solution s, int index) {
		Solution neighborhood = s.clone();
		CeduledJob j1 = neighborhood.getJobs().get(index);
		CeduledJob j2 = neighborhood.getJobs().get(index + 1);
		neighborhood.getJobs().add(index, j2);
		neighborhood.getJobs().add(index + 1, j1);
		neighborhood.getJobs().remove(index + 2);
		neighborhood.getJobs().remove(index + 2);
		return amelioration(runSolution(neighborhood));
	}

	/**
	 * Dérouler une solution et calculer tous les temps
	 * @param s
	 */
	public Solution runSolution(Solution s) {
		StationManager m = StationManager.createManager();
		DateManager d = DateManager.createManager(s.MT);
		CeduledJob blockedJob = null;
		CeduledOperation blockedOperation = null;
		for(CeduledJob j : s.getJobs()) {
			m.enterStation(j, s.LT);
			computeJobFromOperation(m, j, 0, d, s, blockedJob, blockedOperation);
		}
		return s;
	}

	/**
	 * Compute the informations of a job from a date and an operation
	 * @param m
	 * @param j
	 * @param index
	 * @param date
	 * @param s
	 * @param blockedJob
	 * @param blockedOperation
	 * @return
	 */
	public void computeJobFromOperation(StationManager m, CeduledJob j, int index, DateManager d, Solution s, CeduledJob blockedJob, CeduledOperation blockedOperation) {
		for(int i=index; i<j.getCeduledOperations().size(); i++) {
			CeduledOperation o = j.getCeduledOperations().get(i);
			d.doAMove();
			if(o.getMode().equals(CeduledOperation.MODE_B)) {
				d.date += j.getPositionTime();
				blockedJob = j;
				blockedOperation = o;
			}
			if(o.getMode().equals(CeduledOperation.MODE_C_PARALLEL) && null != blockedJob) {
				// TODO what is happening here ??
				o.setBeginDate(blockedOperation.getBeginDate() + s.MT); 
				o.setEndDate(o.getBeginDate() + o.getProcessingTime());
			} else {
				o.setBeginDate(d.date);
				d.date += o.getProcessingTime();
				o.setEndDate(d.date);
			}
		}
		d.doAMove();
		m.leaveStation(j, d.date, j.getLoadedStation(), s.LT);
	}

	/**
	 * Display a solution in details
	 * @param s
	 */
	public void displaySolution(Solution s, Long start, Long end) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		System.out.println("\n=== RESULTATS DE L'HEURISTIQUE ===");
		displayPerformance(start,end);
		System.out.println("=> Retard moyen final = "+df.format(s.computeAverageDelay()));
		for(CeduledJob j : s.getJobs()) {
			System.out.println("\n--- Pièce n°"+j.getId()+" chargée sur la station "+(j.getLoadedStation()+1)+" à "+df.format(j.getLoadedDate())+" minutes ---");
			if(j.isSize()) {
				System.out.println("-> Il s'agit d'une grande pièce !");
			}
			System.out.println("-> Fin (déchargée) à "+df.format(j.getEndDate())+" avec une due date à "+j.getDueDate());
			if(j.isRemoved()) {
				System.out.println("-> Cette pièce à été déchargée de la station "+j.getLoadingHistory()+" !");
			}
			System.out.println("\nOPERATIONS :");
			for(CeduledOperation o : j.getCeduledOperations()) {
				System.out.println("");
				System.out.println("Opération n°"+(j.getCeduledOperations().indexOf(o)+1)+" débutée à "+df.format(o.getBeginDate())+" finie à "+df.format(o.getEndDate()));
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