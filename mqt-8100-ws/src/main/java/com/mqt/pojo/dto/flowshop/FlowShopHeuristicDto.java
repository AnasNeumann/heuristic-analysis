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
public class FlowShopHeuristicDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private String name;
	private Double optimal;
	private List<SequenceDto> sequences = new ArrayList<SequenceDto>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public FlowShopHeuristicDto setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the optimal
	 */
	public Double getOptimal() {
		return optimal;
	}

	/**
	 * @param optimal the optimal to set
	 */
	public FlowShopHeuristicDto setOptimal(Double optimal) {
		this.optimal = optimal;
		return this;
	}

	/**
	 * @return the sequences
	 */
	public List<SequenceDto> getSequences() {
		return sequences;
	}

	/**
	 * @param sequences the sequences to set
	 */
	public FlowShopHeuristicDto setSequences(List<SequenceDto> sequences) {
		this.sequences = sequences;
		return this;
	}
}
