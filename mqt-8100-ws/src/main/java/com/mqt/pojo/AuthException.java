package com.mqt.pojo;

/**
 * Erreur Auth2.0
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 25/08/2017
 */
public class AuthException extends RuntimeException {

	/**
	 * ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur d'une erreur Auth2.0
	 * @param message
	 */
	public AuthException(String message) { 
		 super(message); 
    }
	
}
