package com.mqt.pojo;

import java.util.Calendar;

/**
 * Mother class for all entities
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 25/08/2017
 * 
 */
public abstract class AbstractResource extends SerializableObject {
  private static final long serialVersionUID = 1L;
  protected Calendar timestamps;
  protected Long id;

  /**
   * @return the timestamps
   */
  public Calendar getTimestamps() {
    return timestamps;
  }

  /**
   * @param timestamps the timestamps to set
   */
  public AbstractResource setTimestamps(Calendar timestamps) {
    this.timestamps = timestamps;
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
  public AbstractResource setId(Long id) {
    this.id = id;
    return this;
  }
}
