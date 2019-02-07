package com.mqt.controllers;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.mqt.comparators.NumerotableResourcesComparator;
import com.mqt.pojo.AbstractNumerotableResource;
import com.mqt.pojo.AuthException;
import com.mqt.pojo.Response;
import com.mqt.pojo.SerializableObject;
import com.mqt.pojo.vo.UserAccountVo;
import com.mqt.services.GenericDeleteService;
import com.mqt.services.ProfileService;
import com.mqt.services.UserAccountService;
import com.mqt.utils.DateUtils;
import com.mqt.utils.FilesUtils;
import com.mqt.utils.StringsUtils;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

/**
 * Controller hérité par tous les autres
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 25/08/2017
 */
public class GenericController {

	/**
	 * Injection pour l'envoi de mails
	 */
	@Autowired
	private JavaMailSender sender;

	/**
	 * Injection de dépendances DAO
	 */
	@Autowired
	protected UserAccountService userService;

	@Autowired
	protected NumerotableResourcesComparator comparatorByPosition;

	@Autowired
	protected GenericDeleteService deleteService;

	@Autowired
	protected ProfileService profileService;

	/**
	 * Send a new email to a user
	 * 
	 * @param address
	 * @param content
	 * @param subject
	 */
	protected void sendEmail(String address, String content, String subject) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(address);
			helper.setText(content);
			helper.setSubject(subject);
			sender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the new refresh token
	 * 
	 * @param mail
	 * @param password
	 * @return
	 */
	protected String getRefreshToken(String mail, String password) {
		UserAccountVo user = userService.connect(mail, StringsUtils.md5Hash(password));
		if (null == user) {
			return null;
		}
		return user.getRefreshToken();
	}

	/**
	 * Récuperer le nouvel access token
	 * 
	 * @param id
	 * @param refresh
	 * @return
	 */
	protected String getAccessToken(Long id, String refresh) {
		UserAccountVo user = userService.getByRefreshToken(id, refresh);
		if (null != user && DateUtils.verifyRefresh(user.getAccessDelay())) {
			createRefresh(user);
			throw new AuthException("refresh token expired");
		}
		return user.getAccessToken();
	}

	/**
	 * Récuperer le user connecté
	 * 
	 * @param id
	 * @param access
	 * @return
	 */
	protected UserAccountVo getConnectedUser(Long id, String access) {
		UserAccountVo user = userService.getByAccessToken(id, access);
		if (null != user && DateUtils.verifyAccess(user.getAccessDelay())) {
			createAccess(user);
			throw new AuthException("access token expired");
		}
		return user;
	}
	
	/**
	 * Notify all users
	 * @param message
	 * @param object
	 */
	protected void notifyAllUser(String message, String object) {
		List<UserAccountVo> users = userService.getAll();
		for(UserAccountVo u : users) {
			if(u.getIsAdmin()) {
				sendEmail(u.getMail(),message, object);
			}
		}
	}

	/**
	 * Récuperer le user connecté
	 * 
	 * @param request
	 * @return
	 */
	public UserAccountVo getConnectedUser(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("user"));
		String access = request.getParameter("access");
		if (!id.isPresent() || null == access) {
			return null;
		}
		return getConnectedUser(id.get(), access);
	}

	/**
	 * return if a user is admin and connected or not
	 * 
	 * @param request
	 * @return
	 */
	public Boolean isAdmin(HttpServletRequest request) {
		UserAccountVo user = getConnectedUser(request);
		return (null != user && user.getIsAdmin());
	}

	/**
	 * Generer un nouveau access token
	 * 
	 * @param user
	 * @return
	 */
	private String createAccess(UserAccountVo user) {
		String token = generateToken();
		userService.createOrUpdate(user.setAccessToken(token).setAccessDelay(GregorianCalendar.getInstance()));
		return token;
	}

	/**
	 * generer un nouveau refresh token
	 * 
	 * @param user
	 * @return
	 */
	private String createRefresh(UserAccountVo user) {
		String token = generateToken();
		userService.createOrUpdate(user.setRefreshToken(token).setRefreshDelay(GregorianCalendar.getInstance()));
		return token;
	}

	/**
	 * Create a new session for a user
	 * 
	 * @param user
	 * @return
	 */
	protected UserAccountVo createSession(UserAccountVo user) {
		return user.setRefreshToken(generateToken()).setAccessToken(generateToken())
				.setAccessDelay(GregorianCalendar.getInstance()).setRefreshDelay(GregorianCalendar.getInstance());
	}

	/**
	 * vérifier si l'utilisateur est connecté
	 * 
	 * @param request
	 * @return
	 */
	protected Boolean isConnected(Long id, String access) {
		return null != this.getConnectedUser(id, access);
	}

	/**
	 * Vérifier si le user est admin ou non
	 * 
	 * @param request
	 * @return
	 */
	protected Boolean isAdmin(Long id, String access) {
		UserAccountVo user = this.getConnectedUser(id, access);
		return null != user && user.getIsAdmin();
	}

	/**
	 * send a HTTP response
	 * 
	 * @param json
	 * @return
	 */
	ResponseEntity<Response> send(Response reponse, Integer code) {
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS))
				.body(reponse.setCode(code));
	}

	/**
	 * succes simple
	 * 
	 * @return
	 */
	protected ResponseEntity<Response> success() {
		return send(new Response(), 1);
	}

	/**
	 * send a success HTTP reponse
	 * 
	 * @param reponse
	 * @return
	 */
	protected ResponseEntity<Response> success(Response reponse) {
		return send(reponse, 1);
	}

	/**
	 * send a success HTTP reponse with unique data
	 * 
	 * @param data
	 * @return
	 */
	protected ResponseEntity<Response> one(SerializableObject data) {
		return send(new Response().setOne(data), 1);
	}

	/**
	 * send a success HTTP reponse with many data
	 * 
	 * @param data
	 * @return
	 */
	protected ResponseEntity<Response> many(List<? extends SerializableObject> data) {
		return send(new Response().setMany(data), 1);
	}

	/**
	 * send a success HTTP reponse with only id
	 * 
	 * @param id
	 * @return
	 */
	protected ResponseEntity<Response> id(Long id) {
		return send(new Response().setId(id), 1);
	}

	/**
	 * send a success HTTP reponse with only id and message
	 * 
	 * @param id
	 * @param message
	 * @return
	 */
	protected ResponseEntity<Response> idAndMessage(Long id, String message) {
		return send(new Response().setId(id).setMessage(message), 1);
	}

	/**
	 * send a success HTTP reponse with only message
	 * 
	 * @param message
	 * @return
	 */
	protected ResponseEntity<Response> message(String message) {
		return send(new Response().setMessage(message), 1);
	}

	/**
	 * send a refused HTTP reponse
	 * 
	 * @param reponse
	 * @return
	 */
	protected ResponseEntity<Response> refuse() {
		return send(new Response(), 0);
	}

	/**
	 * refuse with errors
	 * 
	 * @param errors
	 * @return
	 */
	protected ResponseEntity<Response> refuse(Map<String, String> errors) {
		return send(new Response().setErrors(errors), 0);
	}

	/**
	 * Methode permettant de générer un token aléatoire pour la session
	 * 
	 * @return
	 */
	protected String generateToken() {
		return StringsUtils.md5Hash(new BigInteger(130, new SecureRandom()).toString());
	}

	/**
	 * get a file from a string
	 * 
	 * @param file
	 * @return
	 */
	protected byte[] upload(String file) {
		return decodeBase64(file);
	}

	/**
	 * Extract Float if is present
	 * 
	 * @param request
	 * @param field
	 * @return
	 */
	protected Float extractFloat(HttpServletRequest request, String field, Float defaultValue) {
		Optional<Float> result = StringsUtils.parseFloatQuiclky(request.getParameter(field));
		if (result.isPresent()) {
			return result.get();
		}
		return defaultValue;
	}

	/**
	 * Extract Long if is present
	 * 
	 * @param request
	 * @param field
	 * @return
	 */
	protected Long extractLong(HttpServletRequest request, String field, Long defaultValue) {
		Optional<Long> result = StringsUtils.parseLongQuickly(request.getParameter(field));
		if (result.isPresent()) {
			return result.get();
		}
		return defaultValue;
	}

	/**
	 * Extract Integer if is present
	 * 
	 * @param request
	 * @param field
	 * @return
	 */
	protected Integer extractInteger(HttpServletRequest request, String field, Integer defaultValue) {
		Optional<Integer> result = StringsUtils.parseIntegerQuietly(request.getParameter(field));
		if (result.isPresent()) {
			return result.get();
		}
		return defaultValue;
	}

	/**
	 * Uplad with compression of the picture
	 * 
	 * @param file
	 * @param compress
	 * @return
	 */
	protected byte[] upload(String file, Boolean compress) {
		return compress ? FilesUtils.compressImage(decodeBase64(file), FilesUtils.FORMAT_PNG) : decodeBase64(file);
	}

	/**
	 * is 18N word/text complete
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isI18nComplete(HttpServletRequest request) {
		return (isNotEmpty(request.getParameter("fr")) && isNotEmpty(request.getParameter("en")));
	}

	/**
	 * is 18N word/text complete
	 * 
	 * @param request
	 * @param base
	 * @return
	 */
	protected boolean isI18nComplete(HttpServletRequest request, String base) {
		return (isNotEmpty(request.getParameter(base + "FR")) && isNotEmpty(request.getParameter(base + "EN")));
	}

	/**
	 * Update a string field if is present
	 * 
	 * @param request
	 * @param filed
	 * @param previous
	 * @return
	 */
	protected String update(HttpServletRequest request, String field, String previous) {
		return isNotEmpty(request.getParameter(field)) ? request.getParameter(field) : previous;
	}

	/**
	 * Embed a string field if is present
	 * 
	 * @param request
	 * @param filed
	 * @param previous
	 * @return
	 */
	protected String embed(HttpServletRequest request, String field, String previous) {
		return (isNotEmpty(request.getParameter(field)) && !request.getParameter(field).equals(previous))
				? StringsUtils.buildYoutubeEmbed(request.getParameter(field))
				: previous;
	}

	/**
	 * Update a long field if is present
	 * 
	 * @param request
	 * @param filed
	 * @param previous
	 * @return
	 */
	protected Long update(HttpServletRequest request, String field, Long previous) {
		Optional<Long> value = StringsUtils.parseLongQuickly(request.getParameter(field));
		return value.isPresent() ? value.get() : previous;
	}

	/**
	 * Update a integer field if is present
	 * 
	 * @param request
	 * @param filed
	 * @param previous
	 * @return
	 */
	protected Integer update(HttpServletRequest request, String field, Integer previous) {
		Optional<Integer> value = StringsUtils.parseIntegerQuietly(request.getParameter(field));
		return value.isPresent() ? value.get() : previous;
	}
	

	/**
	 * Update a float field if is present
	 * 
	 * @param request
	 * @param filed
	 * @param previous
	 * @return
	 */
	protected Float update(HttpServletRequest request, String field, Float previous) {
		Optional<Float> value = StringsUtils.parseFloatQuiclky(request.getParameter(field));
		return value.isPresent() ? value.get() : previous;
	}

	/**
	 * Update a calendar field if is present
	 * 
	 * @param request
	 * @param filed
	 * @param previous
	 * @return dateOnly
	 */
	protected Calendar update(HttpServletRequest request, String field, Calendar previous) {
		return (!isEmpty(request.getParameter(field)))
				? DateUtils.stringToCalendarQuietly(request.getParameter(field), DateUtils.FORMAT_AMERICAN, previous)
				: previous;
	}

	/**
	 * Build new position after drop or move
	 * 
	 * @param sens
	 * @param ressource
	 * @return
	 */
	protected Integer buildDropPosition(Integer sens, AbstractNumerotableResource ressource) {
		if (sens <= 0) {
			return ressource.getPosition() + 1;
		}
		return ressource.getPosition() - 1;
	}
}