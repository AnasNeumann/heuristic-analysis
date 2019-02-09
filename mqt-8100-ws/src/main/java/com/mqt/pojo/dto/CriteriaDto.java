package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;
import com.mqt.pojo.vo.HeuristicVo;

/**
 * DTO pour l'affichage des critères d'une heuristique
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2019
 * @version 1.0
 */
public class CriteriaDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private HeuristicVo h;
	private Integer nbrWorstValue = 0;
	private Integer nbrOptimal = 0;
	private Integer optimalAlone = 0;
	private Float averageDeviation = 0.0F;
	private Integer maxDeviation = 0;
	private Double standardDeviation = 0.0;

	/**
	 * @return the h
	 */
	public HeuristicVo getH() {
		return h;
	}

	/**
	 * @param h
	 *            the h to set
	 */
	public CriteriaDto setH(HeuristicVo h) {
		this.h = h;
		return this;
	}

	/**
	 * @return the nbrWorstValue
	 */
	public Integer getNbrWorstValue() {
		return nbrWorstValue;
	}

	/**
	 * @param nbrWorstValue
	 *            the nbrWorstValue to set
	 */
	public CriteriaDto setNbrWorstValue(Integer nbrWorstValue) {
		this.nbrWorstValue = nbrWorstValue;
		return this;
	}

	/**
	 * @return the nbrOptimal
	 */
	public Integer getNbrOptimal() {
		return nbrOptimal;
	}

	/**
	 * @param nbrOptimal
	 *            the nbrOptimal to set
	 */
	public CriteriaDto setNbrOptimal(Integer nbrOptimal) {
		this.nbrOptimal = nbrOptimal;
		return this;
	}

	/**
	 * @return the optimalAlone
	 */
	public Integer getOptimalAlone() {
		return optimalAlone;
	}

	/**
	 * @param optimalAlone
	 *            the optimalAlone to set
	 */
	public CriteriaDto setOptimalAlone(Integer optimalAlone) {
		this.optimalAlone = optimalAlone;
		return this;
	}

	/**
	 * @return the averageDeviation
	 */
	public Float getAverageDeviation() {
		return averageDeviation;
	}

	/**
	 * @param averageDeviation
	 *            the averageDeviation to set
	 */
	public CriteriaDto setAverageDeviation(Float averageDeviation) {
		this.averageDeviation = averageDeviation;
		return this;
	}

	/**
	 * @return the maxDeviation
	 */
	public Integer getMaxDeviation() {
		return maxDeviation;
	}

	/**
	 * @param maxDeviation
	 *            the maxDeviation to set
	 */
	public CriteriaDto setMaxDeviation(Integer maxDeviation) {
		this.maxDeviation = maxDeviation;
		return this;
	}

	/**
	 * @return the standardDeviation
	 */
	public Double getStandardDeviation() {
		return standardDeviation;
	}

	/**
	 * @param standardDeviation
	 *            the standardDeviation to set
	 */
	public CriteriaDto setStandardDeviation(Double standardDeviation) {
		this.standardDeviation = standardDeviation;
		return this;
	}
}
