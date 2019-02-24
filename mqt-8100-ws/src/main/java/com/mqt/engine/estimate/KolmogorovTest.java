package com.mqt.engine.estimate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.BoundDto;
import com.mqt.pojo.dto.KolmogorovDto;
import com.mqt.pojo.vo.EstimateVo;

/**
 * Module d'estimation : test de Kolmogorov sur la précision de la borne
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/02/2019
 */
@Service("kolmogorovTest")
public class KolmogorovTest extends GenericEstimatorEngine {

	/**
	 * Constantes
	 */
	private Map<Integer,Double> KOLMOGOROV_TABLE_95 = new HashMap<Integer,Double>();
	
	/**
	 * generer les resultats du test de Kolmogorov
	 * @param estimates
	 * @param bound
	 * @return
	 */
	public KolmogorovDto generate(List<EstimateVo> estimates, BoundDto bound) {
		Double T = getT(getAllDistances(estimates, bound)), W  = getW(estimates.size());
		return new KolmogorovDto()
				.setT(T)
				.setW(W)
				.setSuccess(T <= W);
	}

	/**
	 * Calculer l'ensemble des distances
	 * @param estimates
	 * @param bound
	 * @return
	 */
	private List<Double> getAllDistances(List<EstimateVo> estimates, BoundDto bound){
		List<Double> result = new ArrayList<Double>();
		Collections.sort(estimates, comparatorByValue);
		for(int i = 0; i<estimates.size(); i++) {
			Integer value = estimates.get(i).getValue();
			result.add(Math.abs(Weibull(value, bound) - ((i+1.00)/estimates.size())));
		}
		return result;
	}

	/**
	 * Calculer la loi de weibull avec les valeurs a,b,c trouvés
	 * @param value
	 * @param bound
	 * @return
	 */
	private Double Weibull(Integer value, BoundDto bound) {
		Double a = new Double(bound.getA()), b = new Double(bound.getB()), c = bound.getC();
		return 1.00 - Math.exp(-1.00 * Math.pow((value -a)/b, c));
	}

	/**
	 * Récuperer la plus grande distance
	 * @param values
	 * @return
	 */
	private Double getT(List<Double> values) {
		Double result = values.get(0);
		for(Double d : values) {
			if(d > result) {
				result = d;
			}
		}
		return result;
	}

	/**
	 * Récuperer la valeur de W
	 * @param size
	 * @return
	 */
	private Double getW(Integer size) {
		if(!KOLMOGOROV_TABLE_95.isEmpty()) {
			   return KOLMOGOROV_TABLE_95.get(size);
		   }   
		KOLMOGOROV_TABLE_95.put(10, 0.409);
		KOLMOGOROV_TABLE_95.put(20, 0.294);
		KOLMOGOROV_TABLE_95.put(30, 0.242);
		KOLMOGOROV_TABLE_95.put(40, 0.210);
		return KOLMOGOROV_TABLE_95.get(size);
	}
	
}
