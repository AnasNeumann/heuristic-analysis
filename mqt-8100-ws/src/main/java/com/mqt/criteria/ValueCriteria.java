package com.mqt.criteria;

import com.mqt.pojo.AbstractResource;

/**
 * Criteria for research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
public class ValueCriteria extends AbstractResource implements Criteria {
	private static final long serialVersionUID = 1L;
	private Integer value;
	private Long heuristicId;

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public ValueCriteria setValue(Integer value) {
		this.value = value;
		return this;
	}

	/**
	 * @return the heuristicId
	 */
	public Long getHeuristicId() {
		return heuristicId;
	}

	/**
	 * @param heuristicId
	 *            the heuristicId to set
	 */
	public ValueCriteria setHeuristicId(Long heuristicId) {
		this.heuristicId = heuristicId;
		return this;
	}

}
