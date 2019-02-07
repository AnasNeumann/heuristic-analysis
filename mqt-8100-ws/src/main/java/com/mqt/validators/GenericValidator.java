package com.mqt.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * Generic validator for all form
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
public class GenericValidator {
	
	/**
	 * Variables
	 */
	protected Pattern pattern;
	protected Matcher matcher;
	protected static final String EMAIL_PATTERN =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Validation d'un forumlaire
	 * @param request
	 * @return all errors
	 */
	protected Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = new HashMap<String, String>();
		return result;
	}

	/**
	 * Vérifier si une valeur est vide ou non
	 * @param value
	 * @return
	 */
	protected Boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}
	
	/**
	 * Verifier si une chaine de caractère est un email
	 * @param value
	 * @return
	 */
	protected Boolean isEmail(final String value) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(value);
		return matcher.matches();
	}	
}