package com.mqt.pojo.dto;

import java.util.ArrayList;
import java.util.List;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour les tests du sign d'une heuristique
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/02/2019
 * @version 1.0
 */
public class SignTestDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private String h;
	private List<SingleSignTestDto> tests = new ArrayList<SingleSignTestDto>();

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
	public SignTestDto setH(String h) {
		this.h = h;
		return this;
	}

	/**
	 * @return the tests
	 */
	public List<SingleSignTestDto> getTests() {
		return tests;
	}

	/**
	 * @param tests
	 *            the tests to set
	 */
	public SignTestDto setTests(List<SingleSignTestDto> tests) {
		this.tests = tests;
		return this;
	}

}
