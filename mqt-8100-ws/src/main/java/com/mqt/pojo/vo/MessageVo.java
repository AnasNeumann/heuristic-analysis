package com.mqt.pojo.vo;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;

/**
 * Message table value object
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @version 1.0
 */
public class MessageVo extends AbstractResource {
  private static final long serialVersionUID = 1L;

  private String mail;

  private String name;

  private String subject;

  private String message;

  private Integer state;

  /**
   * @param id the id to set
   */
  public MessageVo setId(Long id) {
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
  public MessageVo setMail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public MessageVo setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public MessageVo setSubject(String subject) {
    this.subject = subject;
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
  public MessageVo setMessage(String message) {
    this.message = message;
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
  public MessageVo setState(Integer state) {
    this.state = state;
    return this;
  }

  /**
   * @param timestamps the timestamps to set
   */
  public MessageVo setTimestamps(Calendar timestamps) {
    this.timestamps = timestamps;
    return this;
  }
}
