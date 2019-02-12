package com.mqt.engine.analyze;

import java.util.List;

import com.mqt.pojo.vo.ValueVo;

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
	protected Double standardDeviation(List<Double> deviations, Double averageDeviation) {
		Double sd = 0.0;
		for(Double d : deviations) {
			sd += Math.pow(d - averageDeviation, 2);
		}
		return Math.sqrt(sd/deviations.size());	
	}
	
	/**
	 * Calculer une déviation pour une valeur
	 * @param v
	 * @return
	 */
	protected double getDeviation(ValueVo v) {
		return ((double) v.getValue() - v.getInstance().getOptimal())/ v.getInstance().getOptimal();
	}

}
