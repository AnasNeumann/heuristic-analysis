package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour le test d'indépendance
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
public class IndependenceTestDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private Integer nbrSequences;
	private Double standardDeviation;
	private Double averageSequenceNbr;
	private Double Z;
	private boolean success;

	/**
	 * @return the nbrSequences
	 */
	public Integer getNbrSequences() {
		return nbrSequences;
	}

	/**
	 * @param nbrSequences
	 *            the nbrSequences to set
	 */
	public IndependenceTestDto setNbrSequences(Integer nbrSequences) {
		this.nbrSequences = nbrSequences;
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
	public IndependenceTestDto setStandardDeviation(Double standardDeviation) {
		this.standardDeviation = standardDeviation;
		return this;
	}

	/**
	 * @return the averageSequenceNbr
	 */
	public Double getAverageSequenceNbr() {
		return averageSequenceNbr;
	}

	/**
	 * @param averageSequenceNbr
	 *            the averageSequenceNbr to set
	 */
	public IndependenceTestDto setAverageSequenceNbr(Double averageSequenceNbr) {
		this.averageSequenceNbr = averageSequenceNbr;
		return this;
	}

	/**
	 * @return the z
	 */
	public Double getZ() {
		return Z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public IndependenceTestDto setZ(Double z) {
		Z = z;
		return this;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public IndependenceTestDto setSuccess(boolean success) {
		this.success = success;
		return this;
	}
}
