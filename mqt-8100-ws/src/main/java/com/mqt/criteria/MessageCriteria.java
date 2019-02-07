package com.mqt.criteria;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;

/**
 * Criteria for research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @version 1.0
 */
public class MessageCriteria extends AbstractResource implements Criteria {
  private static final long serialVersionUID = 1L;

  private String mail;
  private Integer state;

  /**
   * @param id the id to set
   */
  public MessageCriteria setId(Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return the mail
   */
  public String getMail() {
    return mail;
  }

  /**
   * @param mail the mail to set
   */
  public MessageCriteria setMail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * @return the state
   */
  public Integer getState() {
    return state;
  }

  /**
   * @param state the state to set
   */
  public MessageCriteria setState(Integer state) {
    this.state = state;
    return this;
  }

  /**
   * @param timestamps the timestamps to set
   */
  public MessageCriteria setTimestamps(Calendar timestamps) {
    this.timestamps = timestamps;
    return this;
  }
}
