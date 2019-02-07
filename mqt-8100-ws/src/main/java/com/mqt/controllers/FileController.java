package com.mqt.controllers;

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
import com.mqt.pojo.dto.ByteDto;
import com.mqt.pojo.vo.ProfileVo;
import com.mqt.services.ProfileService;
import com.mqt.utils.FilesUtils;

/**
 * Controller pour la gestion des fichiers
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 09/01/2018
 */
@CrossOrigin
@RestController
public class FileController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ProfileService profileService;


	/**
	 * Get one by Id
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/file/{type}/{id}", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> getById(@PathVariable("type") String type, @PathVariable("id") Long id, HttpServletRequest request) {
		switch(type) {
			case "profile" : return one(avatar(id));
		}
		return refuse();
	}
	
	/**
	 * Compress a file before sending
	 * @param file
	 * @return
	 */
	public ResponseEntity<Response> compress(ByteDto file) {
		 return one(file.setContent(FilesUtils.compressImage(file.getContent(), FilesUtils.FORMAT_PNG)));
	}

	/**
	 * get the file of a profile
	 * @param id
	 * @return
	 */
	private ByteDto avatar(Long id) {
		ByteDto result = new ByteDto();
		ProfileVo p = profileService.getById(id);
		if(null != p) {
			result.setContent(p.getAvatar());
		}
		return result;
	}
}