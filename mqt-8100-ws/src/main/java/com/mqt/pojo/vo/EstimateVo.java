package com.mqt.pojo.vo;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;

/**
 * Value object
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
public class EstimateVo extends AbstractResource {
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
	public EstimateVo setValue(Integer value) {
		this.value = value;
		return this;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public EstimateVo setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public EstimateVo setId(Long id) {
		this.id = id;
		return this;
	}
}
