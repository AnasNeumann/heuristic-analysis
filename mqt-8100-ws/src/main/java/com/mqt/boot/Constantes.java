package com.mqt.boot;

/**
 * Classe contenant toutes les constantes utilisées dans WS
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 14/07/2018
 * @version 1.0
 */
public class Constantes {
	
	/**
	 * Les types d'URLs
	 */
	public final static String POST = "post";
	public final static String GET = "get";
	public final static String DELETE = "delete";
	public final static String PUT = "put";
	
	/**
	 * Charsets
	 */
	public final static String ENCODING_UTF8 = "UTF-8";
	public final static String ENCODING_ISO_8859_1 = "ISO-8859-1";

	/**
	 * Mime-types
	 */
	public final static String MIME_HTML = "text/html";
	public final static String MIME_JSON_UTF8 = "application/json;charset=UTF-8";
	public final static String MIME_JSON = "application/json";
	public final static String MIME_TEXT = "text/plain";

	/**
	 * Type of parts
	 */
	public final static Integer TYPE_TEXT = 0;
	public final static Integer TYPE_PICTURE  = 1;
	public final static Integer TYPE_VIDEO  = 2;
	public final static Integer TYPE_LINK  = 3;
	public final static Integer TYPE_TITLE = 4;
	public final static Integer TYPE_PDF  = 5;

	/**
	 * Autres
	 */
	public final static String EMPTY_STRING = "";
	public final static String SEPARATOR_DEFAULT = ";";
	public final static String EMPTY_JSON = "[]";
	public final static String SUCCES_JSON = "{\"result\":\"success\"}";
	public final static String BLANK_SEPARATOR = "\\s+";
	public final static String COMMA_SEPARATOR = "\\,";
	public final static String NAMESPACE_URI = "http://www.styl-decor.com";
	
	/**
	 * TARGET TYPE FOR PART
	 */
	public final static Integer TARGET_HOME = 0;
	public final static Integer TARGET_FIELD = 1;
	public final static Integer TARGET_CATEGORY = 2;
	public final static Integer TARGET_RUBRIC = 3;
	
}
