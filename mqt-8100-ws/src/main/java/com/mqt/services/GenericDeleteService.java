package com.mqt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mqt.pojo.vo.*;
import com.mqt.repositories.*;

/**
 * Service pour la suppression en cascade
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/08/2017
 * @verion 1.0
 */
@Service("deleteService")
public class GenericDeleteService extends GenericService {
	private static final long serialVersionUID = 1L;

	/**
	 * Les repositories
	 */
	@Autowired
	protected ProfileRepository profileRepository;

	@Autowired
	protected UserAccountRepository userAccountRepository;

	@Autowired
	protected MessageRepository messageRepository;
	
	@Autowired
	protected HeuristicRepository heuristicRepository;
	
	@Autowired
	protected ValueRepository valueRepository;
	
	@Autowired
	protected InstanceRepository instanceRepository;
	
	@Autowired
	protected EstimateRepository estimateRepository;

	/**
	 * Les services
	 */
	@Autowired
	protected ProfileService profileService;
	@Autowired
	private HeuristicService heuristicService;
	@Autowired
	protected ValueService valueService;
	@Autowired
	protected InstanceService instanceService;
	
	/**
	 * Purge the database
	 */
	public void purge() {
		for(HeuristicVo h : heuristicService.getAll()) {
			this.cascade(h);
		}
		for(InstanceVo i : instanceService.getAll()) {
			this.cascade(i);
		}
	}
	
	/**
	 * cascade delete a estimate
	 * @param estimate
	 */
	@Modifying
	@Transactional
	public void cascade(EstimateVo estimate) {
		estimateRepository.delete(estimate.getId());
	}

	/**
	 * cascade delete a value
	 * @param value
	 */
	@Modifying
	@Transactional
	public void cascade(ValueVo value) {
		valueRepository.delete(value.getId());
	}
	
	/**
	 * cascade delete an heuristic
	 * @param heuristic
	 */
	@Modifying
	@Transactional
	public void cascade(HeuristicVo heuristic) {
		for(ValueVo v : heuristic.getValues()) {
			cascade(v);
		}
		heuristicRepository.delete(heuristic.getId());
	}
	
	/**
	 * cascade delete an instance
	 * @param instance
	 */
	@Modifying
	@Transactional
	public void cascade(InstanceVo instance) {
		for(ValueVo v : valueService.getAll()) {
			if(v.getInstance().getId().equals(instance.getId())) {
				cascade(v);
			}
		}
		instanceRepository.delete(instance.getId());
	}

	/**
	 * cascade delete a message
	 * @param message
	 */
	@Modifying
	@Transactional
	public void cascade(MessageVo message) {
		messageRepository.delete(message.getId());
	}

	/**
	 * cascade delete a profile
	 * @param profile
	 */
	@Modifying
	@Transactional
	public void cascade(ProfileVo profile) {
		profileRepository.delete(profile.getId());
	}

	/**
	 * cascade delete a userAccount
	 * 
	 * @param userAccount
	 */
	@Modifying
	@Transactional
	public void cascade(UserAccountVo userAccount) {
		if (null != userAccount.getProfile()) {
			cascade(beanMapper.map(profileService.getById(userAccount.getProfile().getId()), ProfileVo.class));
		}
		userAccountRepository.delete(userAccount.getId());
	}
}
