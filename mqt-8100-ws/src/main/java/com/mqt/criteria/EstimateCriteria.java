package com.mqt.criteria;

import com.mqt.pojo.AbstractResource;

/**
 * Value object
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
public class EstimateCriteria extends AbstractResource implements Criteria {
	private static final long serialVersionUID = 1L;
	private Integer value;

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
	public EstimateCriteria setValue(Integer value) {
		this.value = value;
		return this;
	}
}