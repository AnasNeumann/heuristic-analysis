package com.mqt.pojo.vo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mqt.pojo.AbstractResource;

/**
 * Value object
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
public class HeuristicVo extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private String name;
	private List<ValueVo> values = new ArrayList<ValueVo>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public HeuristicVo setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the values
	 */
	public List<ValueVo> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public HeuristicVo setValues(List<ValueVo> values) {
		this.values = values;
		return this;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public HeuristicVo setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public HeuristicVo setId(Long id) {
		this.id = id;
		return this;
	}

}
