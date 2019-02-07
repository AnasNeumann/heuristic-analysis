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
public class ValueVo extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private Integer value;
	private Long heuristicId;
	private InstanceVo instance;

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
	public ValueVo setValue(Integer value) {
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
	public ValueVo setHeuristicId(Long heuristicId) {
		this.heuristicId = heuristicId;
		return this;
	}

	/**
	 * @return the instance
	 */
	public InstanceVo getInstance() {
		return instance;
	}

	/**
	 * @param instance
	 *            the instance to set
	 */
	public ValueVo setInstance(InstanceVo instance) {
		this.instance = instance;
		return this;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public ValueVo setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public ValueVo setId(Long id) {
		this.id = id;
		return this;
	}

}
