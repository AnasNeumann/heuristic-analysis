package com.mqt.controllers;

import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mqt.boot.Constantes;
import com.mqt.pojo.Response;
import com.mqt.pojo.vo.InstanceVo;
import com.mqt.services.InstanceService;

/**
 * Controller la gestion des instances d'essai
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 06/02/2019
 */
@CrossOrigin
@RestController
public class InstanceController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private InstanceService instanceService;

	/**
	 * get All instances
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/instance", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> getAll(HttpServletRequest request) {
		return many(instanceService.getAll());
	}

	/**
	 * create a new instance
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/instance", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> create(HttpServletRequest request) {
		Long id = instanceService.createOrUpdate(new InstanceVo()
				.setOptimal(update(request, "optimal", 1))
				.setId(instanceService.getAll().size() + 1L)
				.setTimestamps(GregorianCalendar.getInstance()));
		return id(id);
	}

	/**
	 * delete a specific instance
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/instance/{id}", method = RequestMethod.DELETE, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> delete(HttpServletRequest request, @PathVariable("id") Long id) {
		InstanceVo i = instanceService.getById(id);
		if(null == i) {
			return refuse();
		}
		deleteService.cascade(i);
		return success();
	}
}
