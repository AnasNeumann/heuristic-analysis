package com.mqt.pojo.dto.flowshop;

import java.util.ArrayList;
import java.util.List;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour les problèmes de Permutation Flow Shop
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/03/2019
 * @version 1.0
 */
public class JobDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private List<Integer> processingTimes = new ArrayList<Integer>();

	/**
	 * @return the processingTimes
	 */
	public List<Integer> getProcessingTimes() {
		return processingTimes;
	}

	/**
	 * @param processingTimes
	 *            the processingTimes to set
	 */
	public JobDto setProcessingTimes(List<Integer> processingTimes) {
		this.processingTimes = processingTimes;
		return this;
	}
}
