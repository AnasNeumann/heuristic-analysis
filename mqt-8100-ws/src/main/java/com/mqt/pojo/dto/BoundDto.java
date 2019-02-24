package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour la génération d'une borne inferieure
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
public class BoundDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private Integer min;
	private Integer max;
	private Integer a;
	private Integer b;
	private Double c;

	/**
	 * @return the min
	 */
	public Integer getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public BoundDto setMin(Integer min) {
		this.min = min;
		return this;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public BoundDto setMax(Integer max) {
		this.max = max;
		return this;
	}

	/**
	 * @return the a
	 */
	public Integer getA() {
		return a;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public BoundDto setA(Integer a) {
		this.a = a;
		return this;
	}

	/**
	 * @return the b
	 */
	public Integer getB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public BoundDto setB(Integer b) {
		this.b = b;
		return this;
	}

	/**
	 * @return the c
	 */
	public Double getC() {
		return c;
	}

	/**
	 * @param c
	 *            the c to set
	 */
	public BoundDto setC(Double c) {
		this.c = c;
		return this;
	}

}
