package com.mqt.controllers;

import java.util.GregorianCalendar;
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
import com.mqt.engine.heuristics.flowshop.GenericFlowShopHeuristic;
import com.mqt.engine.heuristics.flowshop.LocalSearchHeuristic;
import com.mqt.engine.heuristics.flowshop.NEHHeuristic;
import com.mqt.engine.heuristics.flowshop.PalmerHeuristic;
import com.mqt.engine.heuristics.flowshop.SimulatedAnnealingHeuristic;
import com.mqt.engine.solver.FlowShopSolver;
import com.mqt.pojo.Response;
import com.mqt.pojo.dto.flowshop.FlowShopInstanceDto;
import com.mqt.pojo.vo.HeuristicVo;
import com.mqt.pojo.vo.InstanceVo;
import com.mqt.pojo.vo.ValueVo;

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
	private LocalSearchHeuristic LSHeuristic;
	@Autowired
	private NEHHeuristic NEHHeuristic;
	@Autowired
	private SimulatedAnnealingHeuristic SAHeuristic;
	@Autowired
	private PalmerHeuristic palmerHeuristic;
	@Autowired
	private FlowShopSolver solver;
	
	/**
	 * Résolution de problème de flow shop avec permutation
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/solve/flowshop", headers = "Content-Type= multipart/form-data", 
					method = RequestMethod.POST, 
					produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> flowshop(HttpServletRequest request) {
		List<FlowShopInstanceDto> instances = GenericFlowShopHeuristic.parse(request.getParameter("file"));
		deleteService.purge();
		Long idLocalSearch = heuristicService.createOrUpdate(new HeuristicVo().setName("Local Search"));
		Long idSA = heuristicService.createOrUpdate(new HeuristicVo().setName("Simulated Annealing"));
		Long idNEH = heuristicService.createOrUpdate(new HeuristicVo().setName("NEH"));
		for(FlowShopInstanceDto i : instances) {		
			InstanceVo instance = new InstanceVo().
					setId(i.getId())
					.setTimestamps(GregorianCalendar.getInstance())
					.setOptimal(0);
			instance.setId(instanceService.createOrUpdate(instance));
			solver.solve(i, instance);			
			i.getHeuristics().add(NEHHeuristic.solve(i));
			i.getHeuristics().add(LSHeuristic.solveOnlyOneNeightborhood(i, i.getHeuristics().get(0)));
			i.getHeuristics().add(SAHeuristic.solve(i, palmerHeuristic.solve(i), 60.0));					
			valueService.createOrUpdate(new ValueVo()
					.setHeuristicId(idNEH)
					.setInstance(instance)
					.setValue(i.getHeuristics().get(0).getOptimal().intValue()));
			valueService.createOrUpdate(new ValueVo()
					.setHeuristicId(idLocalSearch)
					.setInstance(instance)
					.setValue(i.getHeuristics().get(1).getOptimal().intValue()));
			valueService.createOrUpdate(new ValueVo()
					.setHeuristicId(idSA)
					.setInstance(instance)
					.setValue(i.getHeuristics().get(2).getOptimal().intValue()));
		}
		return many(instances);
	}
	
}
