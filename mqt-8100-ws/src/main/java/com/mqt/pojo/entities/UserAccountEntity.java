package com.mqt.pojo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.mqt.pojo.AbstractResource;

/**
 * USER_ACCOUNT table mapping
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @version 1.0
 */
@Entity
@Table(name = "USER_ACCOUNT")
@SequenceGenerator(name = "user_account_id_seq_generator", sequenceName = "user_account_id_seq")
public class UserAccountEntity extends AbstractResource implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_account_id_seq_generator")
  @Basic(optional = false)
  @Column(name = "ID")
  private Long id;

  @Column(name = "MAIL")
  private String mail;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "IS_ADMIN")
  private Boolean isAdmin;

  @Column(name = "REFRESH_TOKEN")
  private String refreshToken;

  @Column(name = "ACCESS_TOKEN")
  private String accessToken;

  @Column(name = "VALIDATION")
  private String validation;

  @Column(name = "STATE")
  private Integer state;

  @Column(name = "ACCESS_DELAY")
  private Calendar accessDelay;

  @Column(name = "REFRESH_DELAY")
  private Calendar refreshDelay;

  @Column(name = "TIMESTAMPS")
  private Calendar timestamps;

  @OneToMany(fetch = FetchType.EAGER,
             mappedBy = "userId",
             cascade = CascadeType.ALL,
             orphanRemoval = true)
  @Fetch(FetchMode.SELECT)
  private List<ProfileEntity> profile = new ArrayList<ProfileEntity>();

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public UserAccountEntity setId(Long id) {
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
  public UserAccountEntity setMail(String mail) {
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
  public UserAccountEntity setPassword(String password) {
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
  public UserAccountEntity setIsAdmin(Boolean isAdmin) {
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
  public UserAccountEntity setRefreshToken(String refreshToken) {
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
  public UserAccountEntity setAccessToken(String accessToken) {
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
  public UserAccountEntity setState(Integer state) {
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
  public UserAccountEntity setAccessDelay(Calendar accessDelay) {
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
  public UserAccountEntity setRefreshDelay(Calendar refreshDelay) {
    this.refreshDelay = refreshDelay;
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
  public UserAccountEntity setTimestamps(Calendar timestamps) {
    this.timestamps = timestamps;
    return this;
  }

  /**
   * @return the profile
   */
  public ProfileEntity getProfile() {
    return (null != this.profile && !this.profile.isEmpty()) ? this.profile.get(0) : null;
  }

  /**
   * @param profile the profile to set
   */
  public UserAccountEntity setProfile(ProfileEntity profile) {
    this.profile = new ArrayList<ProfileEntity>();
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
  public UserAccountEntity setValidation(String validation) {
    this.validation = validation;
    return this;
  }

}
