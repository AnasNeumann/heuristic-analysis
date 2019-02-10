package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour le test du sign entre 2 heuristiques
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/02/2019
 * @version 1.0
 */
public class SingleSignTestDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private String name;
	private Double alpha;
	private Integer Ymax;
	private Integer value;

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
	public SingleSignTestDto setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the alpha
	 */
	public Double getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha
	 *            the alpha to set
	 */
	public SingleSignTestDto setAlpha(Double alpha) {
		this.alpha = alpha;
		return this;
	}

	/**
	 * @return the ymax
	 */
	public Integer getYmax() {
		return Ymax;
	}

	/**
	 * @param ymax
	 *            the ymax to set
	 */
	public SingleSignTestDto setYmax(Integer ymax) {
		Ymax = ymax;
		return this;
	}

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
	public SingleSignTestDto setValue(Integer value) {
		this.value = value;
		return this;
	}
}
