package com.mqt.controllers;

import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mqt.boot.Constantes;
import com.mqt.pojo.Response;
import com.mqt.pojo.vo.MessageVo;
import com.mqt.services.MessageService;
import com.mqt.validators.MessageValidator;

/**
 * Controller pour l'envoi de message via contactez-nous
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 28/08/2017
 */
@CrossOrigin
@RestController
public class MessageController extends GenericController {

  /**
   * Injection de dépendances
   */
  @Autowired
  MessageValidator validator;
  @Autowired
  MessageService messageService;

  /**
   * Search many by query
   * 
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/message", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
  public ResponseEntity<Response> getAll(HttpServletRequest request) {
    if (!isAdmin(request)) {
      return refuse();
    }
    return many(messageService.getAll());
  }

  /**
   * get one by id
   * 
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/message/{id}",
                  method = RequestMethod.GET,
                  produces = Constantes.MIME_JSON)
  public ResponseEntity<Response> getById(@PathVariable("id") Long id, HttpServletRequest request) {
    MessageVo message = messageService.getById(id);
    if (!isAdmin(request) || null == message) {
      return refuse();
    }
    return one(message);
  }

  /**
   * create one new
   * 
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/message", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
  public ResponseEntity<Response> create(HttpServletRequest request) {
    if (!validator.validate(request).isEmpty()) {
      return refuse(validator.validate(request));
    }
    MessageVo message = new MessageVo().setMail(request.getParameter("mail"))
        .setName(request.getParameter("name")).setState(0)
        .setSubject(request.getParameter("subject")).setMessage(request.getParameter("message"))
        .setTimestamps(GregorianCalendar.getInstance());
    messageService.createOrUpdate(message);
    super.sendEmail("moderation.josephyto@gmail.com",
        "Vous avez recu un nouveau message de " + message.getName()
            + " ["
            + message.getMail()
            + "] : "
            + message.getMessage(),
        "Nouveau message : " + message.getSubject());
    return success();
  }

  /**
   * update one by id
   * 
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/message/{id}",
                  method = RequestMethod.PUT,
                  produces = Constantes.MIME_JSON)
  public ResponseEntity<Response> update(@PathVariable("id") Long id, HttpServletRequest request) {
    MessageVo message = messageService.getById(id);
    if (!isAdmin(request) || null == message) {
      return refuse();
    }
    messageService.createOrUpdate(message.setState(1));
    return success();
  }

  /**
   * delete one by id
   * 
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/message/{id}",
                  method = RequestMethod.DELETE,
                  produces = Constantes.MIME_JSON)
  public ResponseEntity<Response> delete(@PathVariable("id") Long id, HttpServletRequest request) {
    MessageVo message = messageService.getById(id);
    if (!isAdmin(request) || null == message) {
      return refuse();
    }
    deleteService.cascade(message);
    return success();
  }

}
