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
import com.mqt.engine.heuristics.flowshop.CDSHeuristic;
import com.mqt.engine.heuristics.flowshop.GenericFlowShopHeuristic;
import com.mqt.engine.heuristics.flowshop.NEHHeuristic;
import com.mqt.engine.heuristics.flowshop.PalmerHeuristic;
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
		Long idNEH = heuristicService.createOrUpdate(new HeuristicVo().setName("NEH"));
		Long idCDS = heuristicService.createOrUpdate(new HeuristicVo().setName("CDS"));
		long idPalmer = heuristicService.createOrUpdate(new HeuristicVo().setName("Palmer"));
		deleteService.purge();
		for(FlowShopInstanceDto i : instances) {
			i.getHeuristics().add(nehHeuristic.solve(i));
			i.getHeuristics().add(palmerHeuristic.solve(i));
			i.getHeuristics().add(cdsHeuristic.solve(i));
			Double NEHValue = i.getHeuristics().get(0).getOptimal();
			Double CDSValue = i.getHeuristics().get(1).getOptimal();
			Double palmerValue = i.getHeuristics().get(2).getOptimal();
			Double optimal = Math.min(NEHValue,Math.min(CDSValue,palmerValue));
			InstanceVo instance = new InstanceVo().
					setId(i.getId())
					.setTimestamps(GregorianCalendar.getInstance())
					.setOptimal(optimal.intValue());
			instance.setId(instanceService.createOrUpdate(instance));
			valueService.createOrUpdate(new ValueVo().setHeuristicId(idNEH).setInstance(instance).setValue(NEHValue.intValue()));
			valueService.createOrUpdate(new ValueVo().setHeuristicId(idPalmer).setInstance(instance).setValue(CDSValue.intValue()));
			valueService.createOrUpdate(new ValueVo().setHeuristicId(idCDS).setInstance(instance).setValue(palmerValue.intValue()));
		}
		return many(instances);
	}
	
}
