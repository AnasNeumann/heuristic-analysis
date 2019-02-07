package com.mqt.pojo.vo;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;

/**
 * Value object
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
public class InstanceVo extends AbstractResource {
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
	public InstanceVo setOptimal(Integer optimal) {
		this.optimal = optimal;
		return this;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public InstanceVo setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public InstanceVo setId(Long id) {
		this.id = id;
		return this;
	}

}
