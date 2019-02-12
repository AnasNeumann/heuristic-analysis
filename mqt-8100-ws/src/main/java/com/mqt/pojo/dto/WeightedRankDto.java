package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour une ressource de base
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 * @version 1.0
 */
public class WeightedRankDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private Integer sign;
	private Double abs;

	/**
	 * @return the sign
	 */
	public Integer getSign() {
		return sign;
	}

	/**
	 * @param sign
	 *            the sign to set
	 */
	public WeightedRankDto setSign(Integer sign) {
		this.sign = sign;
		return this;
	}

	/**
	 * @return the abs
	 */
	public Double getAbs() {
		return abs;
	}

	/**
	 * @param abs
	 *            the abs to set
	 */
	public WeightedRankDto setAbs(Double abs) {
		this.abs = abs;
		return this;
	}
}
