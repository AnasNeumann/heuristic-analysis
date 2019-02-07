package com.mqt.pojo.dto;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;
import com.mqt.pojo.vo.UserAccountVo;

/**
 * Light user response
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 31/08/2017
 * @version 1.0
 */
public class LightUserDto extends AbstractResource {
  private static final long serialVersionUID = 1L;

  String mail;
  Long profilId;
  Integer state;

  /**
   * Constructor
   * 
   * @param user
   */
  public LightUserDto(UserAccountVo user) {
    this.id = user.getId();
    this.mail = user.getMail();
    this.timestamps = user.getTimestamps();
    this.profilId = user.getProfile().getId();
    this.state = user.getState();
  }

  /**
   * @param id the id to set
   */
  public LightUserDto setId(Long id) {
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
  public LightUserDto setMail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * @param timestamps the timestamps to set
   */
  public LightUserDto setTimestamps(Calendar timestamps) {
    this.timestamps = timestamps;
    return this;
  }

  /**
   * @return the profilId
   */
  public Long getProfilId() {
    return profilId;
  }

  /**
   * @param profilId the profilId to set
   */
  public LightUserDto setProfilId(Long profilId) {
    this.profilId = profilId;
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
  public LightUserDto setState(Integer state) {
    this.state = state;
    return this;
  }
}
