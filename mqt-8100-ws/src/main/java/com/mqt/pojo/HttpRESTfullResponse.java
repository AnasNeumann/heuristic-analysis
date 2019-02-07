package com.mqt.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.mqt.utils.JsonUtils;

/**
 * Created by ineumann on 10/1/17.
 */
public class HttpRESTfullResponse implements Serializable {

  /**
   * Attributes
   */
  protected static final long serialVersionUID = 1L;
  protected Calendar buildDate;
  protected Integer code;
  protected Map<String, String> errors = new HashMap<>();

  /**
   * @return the buildDate
   */
  public Calendar getBuildDate() {
    return buildDate;
  }

  /**
   * @param buildDate the buildDate to set
   */
  public HttpRESTfullResponse setBuildDate(Calendar buildDate) {
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
  public HttpRESTfullResponse setCode(Integer code) {
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
  public HttpRESTfullResponse setErrors(Map<String, String> errors) {
    this.errors = errors;
    return this;
  }

  /**
   * parse Java Object to Json
   * 
   * @return json format
   */
  public String buildJson() {
    buildDate = GregorianCalendar.getInstance();
    return JsonUtils.objectTojsonQuietly(this, HttpRESTfullResponse.class);
  }

}

