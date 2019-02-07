package com.mqt.validators;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mqt.criteria.UserAccountCriteria;
import com.mqt.pojo.vo.UserAccountVo;
import com.mqt.services.UserAccountService;

/**
 * validator for signin form
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/08/2017
 * @version 1.0
 */
@Component
public class SigninValidator extends GenericValidator {

  /**
   * User account service.
   */
  @Autowired
  private UserAccountService userAccountService;

  /**
   * Validation d'un forumlaire
   * 
   * @param request
   * @return all errors
   */
  public Map<String, String> validate(HttpServletRequest request) {
    Map<String, String> result = super.validate(request);
    String mail = request.getParameter("mail");
    if (!isNotEmpty(mail)) {
      result.put("mail", "empty");
    } else if (!isEmail(mail)) {
      result.put("mail", "mail");
    } else if (existMail(mail)) {
      result.put("mail", "already");
    }
    String password = request.getParameter("password");
    if (!isNotEmpty(password) || password.length() < 4) {
      result.put("password", "empty");
    } else if (!isNotEmpty(request.getParameter("repeat"))
        || !request.getParameter("repeat").equals(password)) {
      result.put("repeat", "matching");
    }
    if (!isNotEmpty(request.getParameter("firstName"))) {
      result.put("firstName", "empty");
    }
    if (!isNotEmpty(request.getParameter("job"))) {
      result.put("job", "empty");
    }
    if (!isNotEmpty(request.getParameter("lastName"))) {
      result.put("lastName", "empty");
    }
    return result;
  }

  /**
   * Fonction de verification si le mail existe en base de données FIXME utiliser une requête plus
   * optimisée pour ça + apache commons collection
   * 
   * @param mail
   * @return
   */
  private boolean existMail(String mail) {
    List<UserAccountVo> users =
        userAccountService.searchAllByCriteria(new UserAccountCriteria().setMail(mail), false);
    return null != users && !users.isEmpty();
  }
}
