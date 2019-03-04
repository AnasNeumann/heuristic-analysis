package com.mqt.pojo.dto.flowshop;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour les problèmes de Permutation Flow Shop
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/03/2019
 * @version 1.0
 */
public class SequenceDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private Integer beginTime;
	private Integer endTime;
	private JobDto job = new JobDto();
	
	/**
	 * @return the beginTime
	 */
	public Integer getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */
	public SequenceDto setBeginTime(Integer beginTime) {
		this.beginTime = beginTime;
		return this;
	}

	/**
	 * @return the endTime
	 */
	public Integer getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public SequenceDto setEndTime(Integer endTime) {
		this.endTime = endTime;
		return this;
	}

	/**
	 * @return the job
	 */
	public JobDto getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public SequenceDto setJob(JobDto job) {
		this.job = job;
		return this;
	}
}
