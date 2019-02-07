package com.mqt.pojo.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mqt.pojo.AbstractResource;

/**
 * Message table mapping
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @version 1.0
 */
@Entity
@Table(name = "MESSAGE")
@SequenceGenerator(name = "message_id_seq_generator", sequenceName = "message_id_seq")
public class MessageEntity extends AbstractResource implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq_generator")
  @Basic(optional = false)
  @Column(name = "ID")
  private Long id;

  @Column(name = "MAIL")
  private String mail;

  @Column(name = "NAME")
  private String name;

  @Column(name = "SUBJECT")
  private String subject;

  @Column(name = "MESSAGE")
  private String message;

  @Column(name = "STATE")
  private Integer state;

  @Column(name = "TIMESTAMPS")
  private Calendar timestamps;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public MessageEntity setId(Long id) {
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
  public MessageEntity setMail(String mail) {
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
  public MessageEntity setName(String name) {
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
  public MessageEntity setSubject(String subject) {
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
  public MessageEntity setMessage(String message) {
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
  public MessageEntity setState(Integer state) {
    this.state = state;
    return this;
  }

  /**
   * @return the timestamps
   */
  public Calendar getTimestamps() {
    return timestamps;
  }

  /**
   * @param timestamps the timestamps to set
   */
  public MessageEntity setTimestamps(Calendar timestamps) {
    this.timestamps = timestamps;
    return this;
  }
}
