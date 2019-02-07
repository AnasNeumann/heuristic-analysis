package com.mqt.controllers.advices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqt.pojo.AuthException;

/**
 * Advice pour catcher les erreurs de Auth2.0
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 */
@ControllerAdvice
public class AuthAdvice {

  /**
   * capter l'erreur de connexion
   * 
   * @param ex
   * @param request
   * @param response
   */
  @ExceptionHandler(AuthException.class)
  @ResponseBody
  public void handleAuthException(Exception ex,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
