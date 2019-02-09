package com.mqt.engine.analyze;

import java.util.List;

/**
 * Module d'analyse : fonctions génériques 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 */
public class GenericAnalyzer {

	/**
	 * Calculer l'écart type des déviations
	 * @param deviations
	 * @param averageDeviation
	 * @return
	 */
	protected Double standardDeviation(List<Integer> deviations, Double averageDeviation) {
		Double sd = 0.0;
		for(Integer d : deviations) {
			sd += Math.pow(d - averageDeviation, 2);
		}
		return Math.sqrt(sd/deviations.size());	
	}

}
