package com.mqt.pojo.dto;

import java.util.ArrayList;
import java.util.List;

import com.mqt.pojo.AbstractResource;
import com.mqt.pojo.vo.HeuristicVo;

/**
 * DTO pour le résultat des test de moyenne pour une heuristique
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/02/2019
 * @version 1.0
 */
public class AverageStudentTestDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private HeuristicVo h;
	private List<SingleAverageStudentTestDto> tests = new ArrayList<SingleAverageStudentTestDto>();

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
	public AverageStudentTestDto setH(HeuristicVo h) {
		this.h = h;
		return this;
	}

	/**
	 * @return the tests
	 */
	public List<SingleAverageStudentTestDto> getTests() {
		return tests;
	}

	/**
	 * @param tests
	 *            the tests to set
	 */
	public AverageStudentTestDto setTests(List<SingleAverageStudentTestDto> tests) {
		this.tests = tests;
		return this;
	}
}
