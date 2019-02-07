package com.mqt.pojo.dto;

import com.mqt.pojo.vo.ProfileVo;
import com.mqt.pojo.vo.UserAccountVo;

/**
 * Response of all session informations and profile
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
public class SessionDto extends LightSessionDto {
  private static final long serialVersionUID = 1L;

  private ProfileVo profile = new ProfileVo();

  /**
   * Constructor from profile
   * 
   * @param user
   */
  public SessionDto(UserAccountVo user) {
    super(user);
    this.profile = user.getProfile();
  }

  /**
   * @return the profile
   */
  public ProfileVo getProfile() {
    return profile;
  }

  /**
   * @param profile the profile to set
   */
  public SessionDto setProfile(ProfileVo profile) {
    this.profile = profile;
    return this;
  }
}
