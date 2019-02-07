package com.mqt.criteria;

import com.mqt.pojo.AbstractResource;

/**
 * Criteria for research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
public class InstanceCriteria extends AbstractResource implements Criteria {
	private static final long serialVersionUID = 1L;
	private Integer optimal;

	/**
	 * @return the optimal
	 */
	public Integer getOptimal() {
		return optimal;
	}

	/**
	 * @param optimal
	 *            the optimal to set
	 */
	public InstanceCriteria setOptimal(Integer optimal) {
		this.optimal = optimal;
		return this;
	}

}
