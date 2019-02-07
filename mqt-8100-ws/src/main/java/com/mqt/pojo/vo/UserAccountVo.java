package com.mqt.pojo.vo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mqt.pojo.AbstractResource;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * USER_ACCOUNT value object
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @version 1.0
 */
public class UserAccountVo extends AbstractResource {
  private static final long serialVersionUID = 1L;

  private String mail;

  private String password;

  private Boolean isAdmin;

  private String refreshToken;

  private String accessToken;

  private String validation;

  private Integer state;

  private Calendar accessDelay;

  private Calendar refreshDelay;

  private List<ProfileVo> profile = new ArrayList<>();

  /**
   * @param id the id to set
   */
  public UserAccountVo setId(Long id) {
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
  public UserAccountVo setMail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public UserAccountVo setPassword(String password) {
    this.password = password;
    return this;
  }

  /**
   * @return the isAdmin
   */
  public Boolean getIsAdmin() {
    return isAdmin;
  }

  /**
   * @param isAdmin the isAdmin to set
   */
  public UserAccountVo setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
    return this;
  }

  /**
   * @return the refreshToken
   */
  public String getRefreshToken() {
    return refreshToken;
  }

  /**
   * @param refreshToken the refreshToken to set
   */
  public UserAccountVo setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  /**
   * @return the accessToken
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * @param accessToken the accessToken to set
   */
  public UserAccountVo setAccessToken(String accessToken) {
    this.accessToken = accessToken;
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
  public UserAccountVo setState(Integer state) {
    this.state = state;
    return this;
  }

  /**
   * @return the accessDelay
   */
  public Calendar getAccessDelay() {
    return accessDelay;
  }

  /**
   * @param accessDelay the accessDelay to set
   */
  public UserAccountVo setAccessDelay(Calendar accessDelay) {
    this.accessDelay = accessDelay;
    return this;
  }

  /**
   * @return the refreshDelay
   */
  public Calendar getRefreshDelay() {
    return refreshDelay;
  }

  /**
   * @param refreshDelay the refreshDelay to set
   */
  public UserAccountVo setRefreshDelay(Calendar refreshDelay) {
    this.refreshDelay = refreshDelay;
    return this;
  }

  /**
   * @param timestamps the timestamps to set
   */
  public UserAccountVo setTimestamps(Calendar timestamps) {
    this.timestamps = timestamps;
    return this;
  }

  /**
   * @return the profile
   */
  public ProfileVo getProfile() {
    return (isNotEmpty(profile)) ? this.profile.get(0) : null;
  }

  /**
   * @param profile the profile to set
   */
  public UserAccountVo setProfile(ProfileVo profile) {
    if (null == profile) {
      this.profile = new ArrayList<>();
    }
    this.profile.add(profile);
    return this;
  }

  /**
   * @return the validation
   */
  public String getValidation() {
    return validation;
  }

  /**
   * @param validation the validation to set
   */
  public UserAccountVo setValidation(String validation) {
    this.validation = validation;
    return this;
  }

}
