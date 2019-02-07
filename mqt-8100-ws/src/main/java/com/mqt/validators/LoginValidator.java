package com.mqt.validators;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mqt.pojo.vo.UserAccountVo;
import com.mqt.services.UserAccountService;
import com.mqt.utils.StringsUtils;

/**
 * validator for login form
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @version 1.0
 */
@Component
public class LoginValidator extends GenericValidator {

  /**
   * Injection de dépendances
   */
  @Autowired
  UserAccountService userService;

  /**
   * Validation d'un forumlaire
   * 
   * @param request
   * @return all errors
   */
  public Map<String, String> validate(HttpServletRequest request) {
    Map<String, String> result = super.validate(request);
    String mail = request.getParameter("mail");
    String password = request.getParameter("password");
    if (!isNotEmpty(mail)) {
      result.put("mail", "empty");
    }
    if (!isNotEmpty(password)) {
      result.put("password", "empty");
    }
    UserAccountVo user = userService.connect(mail, StringsUtils.md5Hash(password));
    if (null == user || !user.getState().equals(1)) {
      result.put("exist", "exist");
    }
    return result;
  }
}
