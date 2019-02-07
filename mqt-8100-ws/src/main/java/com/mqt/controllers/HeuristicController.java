package com.mqt.controllers;

import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqt.boot.Constantes;
import com.mqt.pojo.Response;
import com.mqt.pojo.vo.HeuristicVo;
import com.mqt.pojo.vo.InstanceVo;
import com.mqt.pojo.vo.ValueVo;
import com.mqt.services.HeuristicService;
import com.mqt.services.InstanceService;
import com.mqt.services.ValueService;

/**
 * Controller pour la gestion des heuristiques
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 06/02/2019
 */
public class HeuristicController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private HeuristicService heuristicService;
	@Autowired
	private InstanceService instanceService;
	@Autowired
	private ValueService valueService;
	
	/**
	 * get All heuristics
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/heuristic", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> getAll(HttpServletRequest request) {
		return many(heuristicService.getAll());
	}

	/**
	 * create a new heuristic
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/heuristic", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> create(HttpServletRequest request) {
		return id(heuristicService.createOrUpdate(new HeuristicVo()
				.setName(request.getParameter("name"))
				.setTimestamps(GregorianCalendar.getInstance())));
	}
	
	/**
	 * delete a specific heuristic
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/heuristic/{id}", method = RequestMethod.DELETE, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> delete(HttpServletRequest request, @PathVariable("id") Long id) {
		HeuristicVo h = heuristicService.getById(id);
		if(null == h) {
			return refuse();
		}
		deleteService.cascade(h);
		return success();
	}
	
	/**
	 * add a value for an heuristic and an instance
	 * 
	 * @param request
	 * @param instance
	 * @param heuristic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/heuristic/{heuristic}/{instance}", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> addValue(HttpServletRequest request, @PathVariable("heuristic") Long heuristic, @PathVariable("instance") Long instance) {
		HeuristicVo h = heuristicService.getById(heuristic);
		InstanceVo i = instanceService.getById(instance);
		if(null == h || null == i) {
			return refuse();
		}
		return id(valueService.createOrUpdate(new ValueVo()
				.setHeuristicId(heuristic)
				.setInstance(i)
				.setTimestamps(GregorianCalendar.getInstance())
				.setValue(update(request, "value", 1))));
	}
}
