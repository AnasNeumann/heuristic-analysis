package com.mqt.controllers;

import javax.servlet.http.HttpServletRequest;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mqt.boot.Constantes;
import com.mqt.criteria.ProfileCriteria;
import com.mqt.pojo.Response;
import com.mqt.pojo.dto.LightProfileDto;
import com.mqt.pojo.dto.ProfileDto;
import com.mqt.pojo.vo.ProfileVo;
import com.mqt.pojo.vo.UserAccountVo;
import com.mqt.utils.ListUtils;
import com.mqt.utils.StringsUtils;

/**
 * Controller pour la gestion de son profil
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 16/07/2018
 */
@CrossOrigin
@RestController
public class ProfileController extends GenericController {

	/**
	 * get all with index
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/profile/all", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> gettAll(HttpServletRequest request) {
		Optional<Integer> index = StringsUtils.parseIntegerQuietly(request.getParameter("index"));
		if (!index.isPresent()) {
			return refuse();
		}
		List<LightProfileDto> result = new ArrayList<LightProfileDto>();
		for(ProfileVo p : ListUtils.getElementsInInterval(profileService.searchAllByCriteria(new ProfileCriteria().setIsVisible(true), true), (index.get() * 20),20)) {
			result.add(new LightProfileDto(p));
		}
		return many(result);
	}

	/**
	 * get one by id
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> getById(@PathVariable("id") Long id, HttpServletRequest request) {
		ProfileVo profile = profileService.getById(id);
		Boolean isOwner = isOwner(profile, getConnectedUser(request));
		if (null == profile ) {
			return refuse();
		}
		UserAccountVo u = userService.getById(profile.getUserId());
		return one(new ProfileDto(profile, userService.getById(profile.getUserId()), isOwner, u.getIsAdmin()));
	}

	/**
	 * upload a new avatar file
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/profile/{id}/avatar", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> avatar(@PathVariable("id") Long id, HttpServletRequest request) {
		ProfileVo profile = verify(id, request);
		if (null == profile || !isNotEmpty(request.getParameter("file"))) {
			return refuse();
		}
		profileService.createOrUpdate(profile.setAvatar(upload(request.getParameter("file"), true)));
		return success();
	}	

	/**
	 * Toogle is admin for profile
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/profile/{id}/admin", method = RequestMethod.PUT, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> toogleAdmin(@PathVariable("id") Long id, HttpServletRequest request) {
		ProfileVo profile = verify(id, request);
		if (null == profile) {
			return refuse();
		}
		UserAccountVo u = userService.getById(profile.getUserId());
		userService.createOrUpdate(u.setIsAdmin(!u.getIsAdmin()));
		return success();
	}

	/**
	 * upload a new cover file
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/profile/{id}/cover", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> cover(@PathVariable("id") Long id, HttpServletRequest request) {
		ProfileVo profile = verify(id, request);
		if (null == profile || !isNotEmpty(request.getParameter("file"))) {
			return refuse();
		}
		profileService.createOrUpdate(profile.setCover(upload(request.getParameter("file"), true)));
		return success();
	}

	/**
	 * update one by id
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/profile/{id}", method = RequestMethod.PUT, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> update(@PathVariable("id") Long id, HttpServletRequest request) {
		ProfileVo profile = verify(id, request);
		if (null == profile) {
			return refuse();
		}
		profileService.createOrUpdate(profile.setFirstName(update(request, "firstName", profile.getFirstName()))
				.setLastName(update(request, "lastName", profile.getFirstName()))
				.setAddress(update(request, "address", profile.getAddress()))
				.setPhoneNumber(update(request, "phoneNumber", profile.getPhoneNumber()))
				.setJob(update(request, "job", profile.getJob()))
				.setBornDate(update(request, "bornDate", profile.getBornDate())));
		return success();
	}

	/**
	 * Modify user password
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/profile/{id}/password", method = RequestMethod.PUT, produces = Constantes.MIME_JSON)
	public ResponseEntity<Response> password(@PathVariable("id") Long id, HttpServletRequest request) {
		String oldPassword = request.getParameter("old");
		String newPassword = request.getParameter("new");
		ProfileVo profile = verify(id, request);
		if (null == profile || null == oldPassword || null == newPassword) {
			return refuse();
		}
		UserAccountVo user = userService.getById(profile.getUserId());
		if (!user.getPassword().equals(StringsUtils.md5Hash(oldPassword))) {
			return refuse();
		}
		userService.createOrUpdate(user.setPassword(StringsUtils.md5Hash(newPassword)));
		return success();
	}
	
	/**
	 * verify if a user is owner of a profile or admin
	 * 
	 * @param profileId
	 * @param request
	 * @return
	 */
	private ProfileVo verify(Long profileId, HttpServletRequest request) {
		ProfileVo profile = profileService.getById(profileId);
		UserAccountVo user = super.getConnectedUser(request);
		if(null == profile || !isOwner(profile, user)) {
			return null;
		}
		return profile;
	}

	/**
	 * verify the right too see a profile
	 * @param profile
	 * @param user
	 * @return
	 */
	private Boolean isOwner(ProfileVo profile, UserAccountVo user) {
		if (null == user || (!user.getIsAdmin() && !user.getProfile().getId().equals(profile.getId()))) {
			return false;
		}
		return true;
	}
	
}
