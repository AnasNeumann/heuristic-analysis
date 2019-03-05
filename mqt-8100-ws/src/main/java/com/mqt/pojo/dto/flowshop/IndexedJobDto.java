package com.mqt.pojo.dto.flowshop;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour les problèmes de Permutation Flow Shop
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/03/2019
 * @version 1.0
 */
public class IndexedJobDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private JobDto job = new JobDto();
	private Integer slopeIndex;

	/**
	 * @return the job
	 */
	public JobDto getJob() {
		return job;
	}

	/**
	 * @param job
	 *            the job to set
	 */
	public IndexedJobDto setJob(JobDto job) {
		this.job = job;
		return this;
	}

	/**
	 * @return the slopeIndex
	 */
	public Integer getSlopeIndex() {
		return slopeIndex;
	}

	/**
	 * @param slopeIndex
	 *            the slopeIndex to set
	 */
	public IndexedJobDto setSlopeIndex(Integer slopeIndex) {
		this.slopeIndex = slopeIndex;
		return this;
	}

}
