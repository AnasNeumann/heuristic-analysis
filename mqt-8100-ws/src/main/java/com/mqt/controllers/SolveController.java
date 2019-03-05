package com.mqt.controllers;

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
import com.mqt.engine.heuristics.flowshop.CDSHeuristic;
import com.mqt.engine.heuristics.flowshop.GenericFlowShopHeuristic;
import com.mqt.engine.heuristics.flowshop.NEHHeuristic;
import com.mqt.engine.heuristics.flowshop.PalmerHeuristic;
import com.mqt.pojo.Response;
import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;

/**
 * Controller pour la résolution à l'aide heuristiques
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 04/03/2019
 */
@CrossOrigin
@RestController
public class SolveController extends GenericController {

	/**
	 * Injection of dependencies
	 */
	@Autowired
	private NEHHeuristic nehHeuristic;
	@Autowired
	private CDSHeuristic cdsHeuristic;
	@Autowired
	private PalmerHeuristic palmerHeuristic;
	
	/**
	 * Résolution de problème de flow shop avec permutation
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/solve/flowshop", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> flowshop(HttpServletRequest request) {
		List<FlowShopInstanceDto> instances = GenericFlowShopHeuristic.parse(request.getParameter("file"));
		for(FlowShopInstanceDto i : instances) {
			i.getHeuristics().add(nehHeuristic.solve(i));
			i.getHeuristics().add(palmerHeuristic.solve(i));
		}
		return many(instances);
	}
	
}
