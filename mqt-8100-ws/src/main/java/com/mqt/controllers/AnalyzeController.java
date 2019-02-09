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
import com.mqt.pojo.Response;
import com.mqt.pojo.dto.CriteriaDto;
import com.mqt.pojo.dto.DominanceDto;
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
}
