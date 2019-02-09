package com.mqt.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mqt.boot.Constantes;
import com.mqt.engine.analyze.DominanceAnalyzer;
import com.mqt.engine.analyze.EvaluationCriteriaAnalyzer;
import com.mqt.engine.analyze.StudentTestAnalyzer;
import com.mqt.pojo.Response;
import com.mqt.pojo.dto.AverageStudentTestDto;
import com.mqt.pojo.dto.CriteriaDto;
import com.mqt.pojo.dto.DominanceDto;
import com.mqt.pojo.dto.SingleAverageStudentTestDto;
import com.mqt.pojo.vo.HeuristicVo;

/**
 * Controller pour la gestion des analyze
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 07/02/2019
 */
@CrossOrigin
@RestController
public class AnalyzeController extends GenericController {

	/**
	 * Injection of dependancies
	 */
	@Autowired
	EvaluationCriteriaAnalyzer ECAnalyzer;
	@Autowired
	DominanceAnalyzer dAnalyzer;
	@Autowired
	StudentTestAnalyzer STAnalyzer;
	
	/**
	 * Analyze réalisée sur la base des critères d'évaluation de la performance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/analyzer/criteria", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> evaluationCriteriaAnalyze(HttpServletRequest request) {
		List<CriteriaDto> analyze = new ArrayList<CriteriaDto>();
		List<HeuristicVo> heuristics = heuristicService.getAll();
		for(HeuristicVo h : heuristics) {
			analyze.add(ECAnalyzer.analyze(h, heuristics.size()));
		}
		return many(analyze);
	}
	
	/**
	 * Analyze de la dominance stochastique empirique des heuristiques
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/analyzer/dominance", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> dominanceAnalyze(HttpServletRequest request) {
		List<DominanceDto> result = new ArrayList<DominanceDto>();
		Integer total = instanceService.getAll().size();
		for(HeuristicVo h : heuristicService.getAll()) {
			result.add(dAnalyzer.analyze(h, total));
		}
		return many(result);
	}
	
	/**
	 * Analyze des tests de moyenne (student, a=5%)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/analyzer/average", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> averageStudentAnalyze(HttpServletRequest request) {
		List<AverageStudentTestDto> result = new ArrayList<AverageStudentTestDto>();
		Integer total = instanceService.getAll().size();
		List<HeuristicVo> heuristics = heuristicService.getAll();
		for(HeuristicVo h1 : heuristics) {
			List<SingleAverageStudentTestDto> tests = new ArrayList<SingleAverageStudentTestDto>();
			for(HeuristicVo h2 : heuristics) {
				if(!h1.getId().equals(h2.getId())) {
					tests.add(STAnalyzer.analyze(h1, h2, total));
				}
			}
			result.add(new AverageStudentTestDto().setH(h1.getName()).setTests(tests));
		}
		return many(result);
	}
}
