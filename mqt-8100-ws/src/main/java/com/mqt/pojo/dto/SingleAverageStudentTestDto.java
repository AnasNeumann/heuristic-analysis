package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour le résultat du test de moyenne pour deux heuristiques
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 * @version 1.0
 */
public class SingleAverageStudentTestDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private String name;
	private Double t5;
	private Double t20;
	private Double value;

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
	public SingleAverageStudentTestDto setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the t20
	 */
	public Double getT20() {
		return t20;
	}

	/**
	 * @param t20 the t20 to set
	 */
	public SingleAverageStudentTestDto setT20(Double t20) {
		this.t20 = t20;
		return this;
	}

	/**
	 * @return the t5
	 */
	public Double getT5() {
		return t5;
	}

	/**
	 * @param t5
	 *            the t5 to set
	 */
	public SingleAverageStudentTestDto setT5(Double t5) {
		this.t5 = t5;
		return this;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value5 to set
	 */
	public SingleAverageStudentTestDto setValue(Double value) {
		this.value = value;
		return this;
	}
}