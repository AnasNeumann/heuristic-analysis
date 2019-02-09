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
	private Boolean h0;
	private Double value;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public SingleAverageStudentTestDto setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the h0
	 */
	public Boolean getH0() {
		return h0;
	}

	/**
	 * @param h0 the h0 to set
	 */
	public SingleAverageStudentTestDto setH0(Boolean h0) {
		this.h0 = h0;
		return this;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public SingleAverageStudentTestDto setValue(Double value) {
		this.value = value;
		return this;
	}
}
