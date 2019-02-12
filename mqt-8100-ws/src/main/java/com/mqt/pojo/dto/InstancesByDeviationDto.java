package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour l'analyse de la dominance stochastique empirique
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2019
 * @version 1.0
 */
public class InstancesByDeviationDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private Integer instances;
	private Double deviation;

	/**
	 * @return the instances
	 */
	public Integer getInstances() {
		return instances;
	}

	/**
	 * @param instances
	 *            the instances to set
	 */
	public InstancesByDeviationDto setInstances(Integer instances) {
		this.instances = instances;
		return this;
	}

	/**
	 * @return the deviation
	 */
	public Double getDeviation() {
		return deviation;
	}

	/**
	 * @param deviation
	 *            the deviation to set
	 */
	public InstancesByDeviationDto setDeviation(Double deviation) {
		this.deviation = deviation;
		return this;
	}

}
