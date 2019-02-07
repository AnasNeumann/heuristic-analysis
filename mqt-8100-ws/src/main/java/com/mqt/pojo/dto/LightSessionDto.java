package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;
import com.mqt.pojo.vo.UserAccountVo;

/**
 * Response of all session informations
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
public class LightSessionDto extends AbstractResource {
  private static final long serialVersionUID = 1L;

  private Long user;
  private String access;
  private String refresh;
  private Boolean isAdmin;

  /**
   * Constructor from a user
   * 
   * @param user
   */
  public LightSessionDto(UserAccountVo user) {
    this.user = user.getId();
    this.access = user.getAccessToken();
    this.refresh = user.getRefreshToken();
    this.isAdmin = user.getIsAdmin();
  }

  /**
   * @return the user
   */
  public Long getUser() {
    return user;
  }

  /**
   * @param user the id to set
   */
  public LightSessionDto setUser(Long user) {
    this.user = user;
    return this;
  }

  /**
   * @return the access
   */
  public String getAccess() {
    return access;
  }

  /**
   * @param access the access to set
   */
  public LightSessionDto setAccess(String access) {
    this.access = access;
    return this;
  }

  /**
   * @return the refresh
   */
  public String getRefresh() {
    return refresh;
  }

  /**
   * @param refresh the refresh to set
   */
  public LightSessionDto setRefresh(String refresh) {
    this.refresh = refresh;
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
  public LightSessionDto setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
    return this;
  }
}
