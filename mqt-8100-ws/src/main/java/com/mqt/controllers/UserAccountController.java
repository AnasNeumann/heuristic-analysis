package com.mqt.controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.mqt.criteria.UserAccountCriteria;
import com.mqt.pojo.Response;
import com.mqt.pojo.SearchResult;
import com.mqt.pojo.dto.LightSessionDto;
import com.mqt.pojo.dto.LightUserDto;
import com.mqt.pojo.dto.SessionDto;
import com.mqt.pojo.vo.ProfileVo;
import com.mqt.pojo.vo.UserAccountVo;
import com.mqt.utils.StringsUtils;
import com.mqt.validators.LoginValidator;
import com.mqt.validators.SigninValidator;

/**
 * Controller pour la mise en place authentification Oauth2.0
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 05/08/2017
 */
@CrossOrigin
@RestController
public class UserAccountController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	LoginValidator loginValidator;
	@Autowired
	SigninValidator signinValidator;

	/**
	 * log in an existing account
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> login(HttpServletRequest request) {
		Map<String, String> errors = loginValidator.validate(request);
		if (errors.size() > 0) {
			return refuse(errors);
		}
		UserAccountVo user = userService.connect(request.getParameter("mail"),
				StringsUtils.md5Hash(request.getParameter("password")));
		return one(new SessionDto(user));
	}

	/**
	 * get the refresh token
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/refresh", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> refresh(HttpServletRequest request) {
		Map<String, String> errors = loginValidator.validate(request);
		if (errors.size() > 0) {
			return refuse(errors);
		}
		UserAccountVo user = userService.connect(request.getParameter("mail"),
				StringsUtils.md5Hash(request.getParameter("password")));
		return one(new LightSessionDto(user));
	}

	/**
	 * get the access token
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/access", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> accesss(HttpServletRequest request) {
		String refresh = request.getParameter("refresh");
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("user"));
		if (null == refresh || !id.isPresent()) {
			return refuse();
		}
		return message(getAccessToken(id.get(), refresh));
	}


	
	/**
	 * create a new gardens account
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/signin", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> signin(HttpServletRequest request) {
		Map<String, String> errors = signinValidator.validate(request);
		if (errors.size() > 0) {
			return refuse(errors);
		}
		UserAccountVo user = genericAccountCreation(request);
		super.sendEmail(user.getMail(),
				"Félicitation, votre compte sera à présent disponible en utilisant le lien suivant : https://www.josephyto.com/#/validation/" + user.getId() + "/" + user.getValidation(),
				"Styl Decor : inscription réussie !");
		return one(new SessionDto(user));
	}

	/**
	 * Generic account creation
	 * @param request
	 * @param isVisible
	 * @return
	 */
	private UserAccountVo genericAccountCreation(HttpServletRequest request) {
		UserAccountVo user = super.createSession(new UserAccountVo()).setMail(request.getParameter("mail"))
				.setPassword(StringsUtils.md5Hash(request.getParameter("password"))).setValidation(generateToken())
				.setState(0).setIsAdmin(false).setTimestamps(GregorianCalendar.getInstance());
		user.setId(userService.createOrUpdate(user));
		ProfileVo profile = new ProfileVo().setLastName(request.getParameter("lastName"))
				.setFirstName(request.getParameter("firstName")).setTimestamps(GregorianCalendar.getInstance())
				.setCategory(request.getParameter("category"))
				.setJob(request.getParameter("job")).setUserId(user.getId());
		profile.setId(profileService.createOrUpdate(profile));
		return user.setProfile(profile);
	}
	
	/**
	 * generate a new user password
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/regenerate", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> regenerate(HttpServletRequest request) {
		List<UserAccountVo> users = userService
				.searchAllByCriteria(new UserAccountCriteria().setMail(request.getParameter("mail")), false);
		if (users.size() <= 0) {
			return refuse();
		}
		String password = generateToken();
		userService.createOrUpdate(users.get(0).setPassword(StringsUtils.md5Hash(password)));
		super.sendEmail(users.get(0).getMail(), "Votre nouveau mot de passe : " + password,
				"Génération d'un nouveau mot de passe");
		return success();
	}

	/**
	 * validate an account
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validate/{id}", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> validate(@PathVariable("id") Long id, HttpServletRequest request) {
		UserAccountVo user = userService.getById(id);
		if (null == user || !request.getParameter("validation").equals(user.getValidation())) {
			return refuse();
		}
		userService.createOrUpdate(user.setState(1));
		return success();
	}

	/**
	 * Delete a user by id if connectedUser is admin
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> delete(@PathVariable("id") Long id, HttpServletRequest request) {
		UserAccountVo user = userService.getById(id);
		if (null == user || !getConnectedUser(request).getIsAdmin()) {
			return refuse();
		}
		deleteService.cascade(user);
		return success();
	}

	/**
	 * Unvalidate a user by id
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> update(@PathVariable("id") Long id, HttpServletRequest request) {
		UserAccountVo user = userService.getById(id);
		if (null == user || !isAdmin(request)) {
			return refuse();
		}
		Integer state = (0 == user.getState()) ? 1 : 0;
		userService.createOrUpdate(user.setState(state));
		return success();
	}

	/**
	 * get users in infinity scroll
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> get(HttpServletRequest request) {
		Optional<Long> index = StringsUtils.parseLongQuietly(request.getParameter("index"));
		if (!index.isPresent() || !isAdmin(request)) {
			return refuse();
		}
		SearchResult<UserAccountVo> listResult = userService.searchByCriteria(null, index.get(), 20L);
		List<LightUserDto> users = new ArrayList<>();
		listResult.getResults().forEach(u -> users.add(new LightUserDto(u)));
		return many(users);
	}

}
