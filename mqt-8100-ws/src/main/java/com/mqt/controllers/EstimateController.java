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
import com.mqt.comparators.EstimateComparator;
import com.mqt.pojo.Response;
import com.mqt.pojo.vo.EstimateVo;
import com.mqt.services.EstimateService;

public class EstimateController extends GenericController {

	/**
	 * Injection of dependencies
	 */
	@Autowired
	private EstimateService estimateSerivce;
	@Autowired
	private EstimateComparator compartor;

	/**
	 * get all estimate
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/estimate", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> getAll(HttpServletRequest request) {
		return many(estimateSerivce.getAll());
	}

	/**
	 * create a new instance
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/estimate", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> create(HttpServletRequest request) {
		EstimateVo e = new EstimateVo()
				.setValue(update(request, "value", 1))
				.setTimestamps(GregorianCalendar.getInstance());
		e.setId(estimateSerivce.createOrUpdate(e));
		return one(e);
	}

	/**
	 * delete a specific instance
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/estimate/{id}", method = RequestMethod.DELETE, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> delete(HttpServletRequest request, @PathVariable("id") Long id) {
		EstimateVo e = estimateSerivce.getById(id);
		if(null == e) {
			return refuse();
		}
		deleteService.cascade(e);
		return success();
	}
	
	/**
	 * Test of independences
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/estimate/independence", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> testIndependence(HttpServletRequest request) {
		return refuse();
	}
	
	/**
	 * Test of kolmogorov
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/estimate/kolmogorov", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> testKolmogorov(HttpServletRequest request) {
		return refuse();
	}
	
	/**
	 * Estimate the bound
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/estimate/bound", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> estimateBound(HttpServletRequest request) {
		return refuse();
	}

}