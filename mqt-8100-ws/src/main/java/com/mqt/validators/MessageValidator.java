package com.mqt.validators;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * validator for contact us form
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @version 1.0
 */
@Component
public class MessageValidator extends GenericValidator {

	/**
	 * Validation d'un forumlaire
	 * @param request
	 * @return all errors
	 */
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		String mail = request.getParameter("mail");
		if(!isNotEmpty(mail)){
			result.put("mail", "empty");
		} else if(!isEmail(mail)){
			result.put("mail", "mail");
		}
		if(!isNotEmpty(request.getParameter("subject"))){
			result.put("subject", "empty");
		}
		if(!isNotEmpty(request.getParameter("name"))){
			result.put("name", "empty");
		}
		if(!isNotEmpty(request.getParameter("message"))){
			result.put("message", "empty");
		} 
		return result;
	}
	
}
