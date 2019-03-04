package com.mqt.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mqt.boot.Constantes;
import com.mqt.engine.heuristics.GenericHeuristic;
import com.mqt.engine.heuristics.flowshop.CDSHeuristic;
import com.mqt.engine.heuristics.flowshop.NEHHeuristic;
import com.mqt.engine.heuristics.flowshop.PalmerHeuristic;
import com.mqt.pojo.Response;

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
	private GenericHeuristic genericHeuristic;
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
		return refuse();
	}
	
}
