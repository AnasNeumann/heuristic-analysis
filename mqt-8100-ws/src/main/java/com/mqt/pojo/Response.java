package com.mqt.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mqt.utils.JsonUtils;

/**
 * Class for all Http Restfull Services dto
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
public class Response implements Serializable {

  /**
   * Attributes
   */
  protected static final long serialVersionUID = 1L;
  protected Calendar buildDate;
  protected Integer code;
  protected Map<String, String> errors = new HashMap<String, String>();
  protected List<? extends SerializableObject> many;
  protected SerializableObject one;
  protected Long id;
  protected String message;

  /**
   * @return the buildDate
   */
  public Calendar getBuildDate() {
    return buildDate;
  }

  /**
   * @param buildDate the buildDate to set
   */
  public Response setBuildDate(Calendar buildDate) {
    this.buildDate = buildDate;
    return this;
  }

  /**
   * @return the code
   */
  public Integer getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public Response setCode(Integer code) {
    this.code = code;
    return this;
  }

  /**
   * @return the errors
   */
  public Map<String, String> getErrors() {
    return errors;
  }

  /**
   * @param errors the errors to set
   */
  public Response setErrors(Map<String, String> errors) {
    this.errors = errors;
    return this;
  }

  /**
   * @return the many
   */
  public List<? extends SerializableObject> getMany() {
    return many;
  }

  /**
   * @param many the many to set
   */
  public Response setMany(List<? extends SerializableObject> many) {
    this.many = many;
    return this;
  }

  /**
   * @return the one
   */
  public SerializableObject getOne() {
    return one;
  }

  /**
   * @param one the one to set
   */
  public Response setOne(SerializableObject one) {
    this.one = one;
    return this;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public Response setId(Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message the message to set
   */
  public Response setMessage(String message) {
    this.message = message;
    return this;
  }

  /**
   * parse Java Object to Json
   * 
   * @return json format
   */
  public String buildJson() {
    buildDate = GregorianCalendar.getInstance();
    return JsonUtils.objectTojsonQuietly(this, Response.class);
  }

}
