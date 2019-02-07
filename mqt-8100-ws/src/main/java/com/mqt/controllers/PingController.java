package com.mqt.controllers;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mqt.boot.Constantes;

/**
 * Controller permettant de vérifier si l'application tourne toujours ou si elle est tombée
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class PingController {

	/**
	 * Injection of dependencies
	 */
	@Autowired
	private JavaMailSender sender;
	
	/**
	 * Test de l'existance de l'application
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ping", method = RequestMethod.GET, produces = Constantes.MIME_TEXT)
    public String ping(HttpServletRequest request){
		return "pong\n";
	}
	
	/**
	 * Test du fonctionnement de l'envoi d'un email
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/mail", method = RequestMethod.GET, produces = Constantes.MIME_TEXT)
	public String mail() {
		 MimeMessage message = sender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
		 try {
			helper.setTo("anas.neumann.isamm@gmail.com");
			helper.setText("test content");
			helper.setSubject("test subject");
			sender.send(message);
		 } catch (MessagingException e) {
			e.printStackTrace();
		 } 
		 return "mail\n";
	}

}