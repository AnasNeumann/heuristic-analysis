package com.mqt.engine.estimate;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mqt.pojo.dto.BoundDto;
import com.mqt.pojo.vo.EstimateVo;

/**
 * Module d'estimation : génération d'une borne min est d'un interval de confiance
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/02/2019
 */
@Service("boundEstimator")
public class BoundEstimator extends GenericEstimatorEngine {

	/**
	 * generation des bornes et intervales de confiances
	 * @param estimates
	 * @return
	 */
	public BoundDto generate(List<EstimateVo> estimates) {
		BoundDto result = new BoundDto();
		if(estimates.size() < 2) {
			return result;
		}
		Collections.sort(estimates, comparatorByValue);
		Double mediane = getMediane(estimates);
		Integer x1 = estimates.get(0).getValue(), x2 = estimates.get(1).getValue();
		Double index = Math.floor(0.63*estimates.size() + 1.00);
		return result.setA(x1 - (x2 - x1))
				.setB(estimates.get(index.intValue()).getValue() - result.getA())
				.setC((ln(-ln(0.5)))/(ln(mediane - result.getA())-ln(result.getB())))
				.setMax(x1)
				.setMin(x1 - result.getB());
	}
	
	/**
	 * Ln(x)
	 * @param x
	 * @return
	 */
	private Double ln(Integer x) {
		return Math.log(x);
	}
	
	/**
	 * Ln(x)
	 * @param x
	 * @return
	 */
	private Double ln(Double x) {
		return Math.log(x);
	}
	
}
