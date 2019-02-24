package com.mqt.engine.estimate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mqt.comparators.EstimateComparator;
import com.mqt.comparators.ResourcesComparator;
import com.mqt.pojo.vo.EstimateVo;

/**
 * Module d'estimation : fonctions génériques
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/02/2019
 */
public class GenericEstimatorEngine {
	
	/**
	 * Injection of dependancies
	 */
	@Autowired
	protected ResourcesComparator comparator;
	@Autowired
	protected EstimateComparator comparatorByValue;

	/**
	 * Get the mediane value
	 * @param estimates
	 * @return
	 */
	protected Double getMediane(List<EstimateVo> estimates) {
		if(estimates.size() % 2 != 0) {
			return new Double(estimates.get(Math.floorDiv(estimates.size(), 2)).getValue());
		}
		return new Double((estimates.get(Math.floorDiv(estimates.size(),2)).getValue() + estimates.get(Math.floorDiv(estimates.size(),2)+1).getValue()) / 2.00);
	}
}
