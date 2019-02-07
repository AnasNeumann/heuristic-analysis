package com.mqt.criteria;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;

/**
 * Criteria for research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/02/2017
 * @version 1.0
 */
public class UserAccountCriteria extends AbstractResource implements Criteria {
  private static final long serialVersionUID = 1L;

  private String mail;

  /**
   * @param id the id to set
   */
  public UserAccountCriteria setId(Long id) {
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
  public UserAccountCriteria setMail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * @param timestamps the timestamps to set
   */
  public UserAccountCriteria setTimestamps(Calendar timestamps) {
    this.timestamps = timestamps;
    return this;
  }
}
