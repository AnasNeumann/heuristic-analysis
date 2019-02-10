package com.mqt.pojo.dto;

import java.util.ArrayList;
import java.util.List;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour le résultat des tests des rands pondérés de Wilcoxon d'une
 * heuristique
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 * @version 1.0
 */
public class WilcoxonTestDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private String h;
	private List<SingleWilcoxonTestDto> tests = new ArrayList<SingleWilcoxonTestDto>();

	/**
	 * @return the h
	 */
	public String getH() {
		return h;
	}

	/**
	 * @param h
	 *            the h to set
	 */
	public WilcoxonTestDto setH(String h) {
		this.h = h;
		return this;
	}

	/**
	 * @return the tests
	 */
	public List<SingleWilcoxonTestDto> getTests() {
		return tests;
	}

	/**
	 * @param tests
	 *            the tests to set
	 */
	public WilcoxonTestDto setTests(List<SingleWilcoxonTestDto> tests) {
		this.tests = tests;
		return this;
	}
}
