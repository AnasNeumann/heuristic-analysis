package com.mqt.pojo.dto.flowshop;

import java.util.ArrayList;
import java.util.List;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour les problèmes de Permutation Flow Shop
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/03/2019
 * @version 1.0
 */
public class FlowShopInstanceDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private List<JobDto> jobs = new ArrayList<JobDto>(); 
	private List<FlowShopHeuristicDto> heuristics = new ArrayList<FlowShopHeuristicDto>();

	/**
	 * @return the jobs
	 */
	public List<JobDto> getJobs() {
		return jobs;
	}

	/**
	 * @param jobs the jobs to set
	 */
	public FlowShopInstanceDto setJobs(List<JobDto> jobs) {
		this.jobs = jobs;
		return this;
	}

	/**
	 * @return the heuristics
	 */
	public List<FlowShopHeuristicDto> getHeuristics() {
		return heuristics;
	}

	/**
	 * @param heuristics the heuristics to set
	 */
	public FlowShopInstanceDto setHeuristics(List<FlowShopHeuristicDto> heuristics) {
		this.heuristics = heuristics;
		return this;
	} 
}
