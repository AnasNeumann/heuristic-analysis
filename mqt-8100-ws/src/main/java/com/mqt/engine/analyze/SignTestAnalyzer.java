package com.mqt.engine.analyze;

import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.SingleSignTestDto;
import com.mqt.pojo.vo.HeuristicVo;

/**
 * Module d'analyse : test des moyennes de student
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 */
@Service("signTestAnalyzer")
public class SignTestAnalyzer extends GenericAnalyzer {

	/**
	 * Constantes
	 */
	public static double ALPHA = 0.5;
	
	/**
	 * Analyzer h1 par rapport à h2 selon le test du signe
	 * @param h1
	 * @param h2
	 * @return
	 */
	public SingleSignTestDto analyze(HeuristicVo h1, HeuristicVo h2) {
		return new SingleSignTestDto().setName(h2.getName()).setAlpha(ALPHA).setYmax(getY(h1.getValues().size())).setValue(getSupDeviation(h1, h2));
	}
	
	/**
	 * Calculer pour combient d'instance h1 a eu une pire déviation que h2
	 * @param h1
	 * @param h2
	 * @return
	 */
	private Integer getSupDeviation(HeuristicVo h1, HeuristicVo h2) {
		Integer nbrSupDeviation = 0;
		for(int i=0; i<h1.getValues().size(); i++) {
			if(getDeviation(h1.getValues().get(i)) > getDeviation(h2.getValues().get(i))) {
				nbrSupDeviation++;
			}
		}
		return nbrSupDeviation;
	}

	/**
	 * Calculer la valeur de Y+ pour une valeur de a et une valeur de n
	 * @param n
	 * @return
	 */
	private Integer getY(int n) {
		Integer Ymax = 0;
		for(int y = 0; y<=n; y++) {
			if(binomialLow(y,n) >= ALPHA) {
				Ymax = y;
			} else {
				return Ymax;
			}
		}
		return Ymax;
	}

	/**
	 * Calculer la probabilité P(x >= y) selon la loi binomiale
	 * @param y
	 * @param n
	 * @return
	 */
	private double binomialLow(int y, int n) {
		double result = 0.0;
		for(int i=y; i<=n; i++) {
			result += binomialCoefficient(i, n) * Math.pow(0.5, n);
		}
		return result;
	}

	/**
	 * Calculer l'arrangement (coefficient binomial) de y dans n
	 * @param y
	 * @param n
	 * @return
	 */
	private double binomialCoefficient(int y, int n) {
		return ((double) factoriel(n)) / (factoriel(y) * factoriel(n - y));
	}

	/**
	 * Calculer le factoriel d'un nombre k (méthode recursive)
	 * @param k
	 * @return
	 */
	private long factoriel(long k) {
		if(k <= 1) {
			return 1;
		}
		return (k * factoriel(k - 1));
	}
}
