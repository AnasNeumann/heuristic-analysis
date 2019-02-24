package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour le test de Kolmorov
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
public class KolmogorovDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private Double T;
	private Double W;
	private Boolean success;

	/**
	 * @return the t
	 */
	public Double getT() {
		return T;
	}

	/**
	 * @param t
	 *            the t to set
	 */
	public KolmogorovDto setT(Double t) {
		T = t;
		return this;
	}

	/**
	 * @return the w
	 */
	public Double getW() {
		return W;
	}

	/**
	 * @param w
	 *            the w to set
	 */
	public KolmogorovDto setW(Double w) {
		W = w;
		return this;
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public KolmogorovDto setSuccess(Boolean success) {
		this.success = success;
		return this;
	}
}
