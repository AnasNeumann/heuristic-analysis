package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour tous les resultats de l'analyse des estimations
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
public class EstimateDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private IndependenceTestDto independence = new IndependenceTestDto();
	private BoundDto bound = new BoundDto();
	private KolmogorovDto kolmogorov = new KolmogorovDto();

	/**
	 * @return the independence
	 */
	public IndependenceTestDto getIndependence() {
		return independence;
	}

	/**
	 * @param independence
	 *            the independence to set
	 */
	public EstimateDto setIndependence(IndependenceTestDto independence) {
		this.independence = independence;
		return this;
	}

	/**
	 * @return the bound
	 */
	public BoundDto getBound() {
		return bound;
	}

	/**
	 * @param bound the bound to set
	 */
	public EstimateDto setBound(BoundDto bound) {
		this.bound = bound;
		return this;
	}

	/**
	 * @return the kolmogorov
	 */
	public KolmogorovDto getKolmogorov() {
		return kolmogorov;
	}

	/**
	 * @param kolmogorov the kolmogorov to set
	 */
	public EstimateDto setKolmogorov(KolmogorovDto kolmogorov) {
		this.kolmogorov = kolmogorov;
		return this;
	}
}
