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
import com.mqt.engine.analyze.SignTestAnalyzer;
import com.mqt.engine.analyze.StudentTestAnalyzer;
import com.mqt.engine.analyze.WilcoxonWeightedRankAnalyzer;
import com.mqt.pojo.Response;
import com.mqt.pojo.dto.AverageStudentTestDto;
import com.mqt.pojo.dto.CriteriaDto;
import com.mqt.pojo.dto.DominanceDto;
import com.mqt.pojo.dto.SignTestDto;
import com.mqt.pojo.dto.SingleAverageStudentTestDto;
import com.mqt.pojo.dto.SingleSignTestDto;
import com.mqt.pojo.dto.SingleWilcoxonTestDto;
import com.mqt.pojo.dto.WilcoxonTestDto;
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
	 * Injection of dependencies
	 */
	@Autowired
	EvaluationCriteriaAnalyzer ECAnalyzer;
	@Autowired
	DominanceAnalyzer DAnalyzer;
	@Autowired
	StudentTestAnalyzer STAnalyzer;
	@Autowired
	WilcoxonWeightedRankAnalyzer WWRAnalyzer;
	@Autowired
	SignTestAnalyzer signTestAnalyzer;
	
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
		for(HeuristicVo h : heuristicService.getAll()) {
			result.add(DAnalyzer.analyze(h, h.getValues().size()));
		}
		return many(result);
	}
	
	/**
	 * Analyze des tests de moyenne
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/analyzer/average", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> averageStudentAnalyze(HttpServletRequest request) {
		List<AverageStudentTestDto> result = new ArrayList<AverageStudentTestDto>();
		List<HeuristicVo> heuristics = heuristicService.getAll();
		for(HeuristicVo h1 : heuristics) {
			List<SingleAverageStudentTestDto> tests = new ArrayList<SingleAverageStudentTestDto>();
			for(HeuristicVo h2 : heuristics) {
				if(!h1.getId().equals(h2.getId())) {
					tests.add(STAnalyzer.analyze(h1, h2, h1.getValues().size()));
				}
			}
			result.add(new AverageStudentTestDto().setH(h1.getName()).setTests(tests));
		}
		return many(result);
	}
	
	/**
	 * Analyze des tests des rangs pondérés de Wilcoxon
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/analyzer/wilcoxon", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> weightedRankWilcoxonAnalyze(HttpServletRequest request) {
		List<WilcoxonTestDto> result = new ArrayList<WilcoxonTestDto>();
		List<HeuristicVo> heuristics = heuristicService.getAll();
		for(HeuristicVo h1 : heuristics) {
			List<SingleWilcoxonTestDto> tests = new ArrayList<SingleWilcoxonTestDto>();
			for(HeuristicVo h2 : heuristics) {
				if(!h1.getId().equals(h2.getId())) {
					tests.add(WWRAnalyzer.analyze(h1, h2, h1.getValues().size()));
				}
			}
			result.add(new WilcoxonTestDto().setH(h1.getName()).setTests(tests));
		}
		return many(result);
	}
	
	/**
	 * Analyze des tests du signe
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/analyzer/sign", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> signAnalyze(HttpServletRequest request) {
		List<SignTestDto> result = new ArrayList<SignTestDto>();
		List<HeuristicVo> heuristics = heuristicService.getAll();
		for(HeuristicVo h1 : heuristics) {
			List<SingleSignTestDto> tests = new ArrayList<SingleSignTestDto>();
			for(HeuristicVo h2 : heuristics) {
				if(!h1.getId().equals(h2.getId())) {
					tests.add(signTestAnalyzer.analyze(h1, h2));
				}
			}
			result.add(new SignTestDto().setH(h1.getName()).setTests(tests));
		}
		return many(result);
	}
}
