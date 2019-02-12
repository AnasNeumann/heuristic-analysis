package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour le résultat du test des rands pondérés de Wilcoxon pour deux
 * heuristiques
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 * @version 1.0
 */
public class SingleWilcoxonTestDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer w95;
	private Integer w99;
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
	public SingleWilcoxonTestDto setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the w95
	 */
	public Integer getW95() {
		return w95;
	}

	/**
	 * @param w95
	 *            the w95 to set
	 */
	public SingleWilcoxonTestDto setW95(Integer w95) {
		this.w95 = w95;
		return this;
	}

	/**
	 * @return the w99
	 */
	public Integer getW99() {
		return w99;
	}

	/**
	 * @param w99
	 *            the w99 to set
	 */
	public SingleWilcoxonTestDto setW99(Integer w99) {
		this.w99 = w99;
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
	 *            the value to set
	 */
	public SingleWilcoxonTestDto setValue(Double value) {
		this.value = value;
		return this;
	}
}
