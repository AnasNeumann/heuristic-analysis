package com.mqt.pojo.dto;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;
import com.mqt.pojo.vo.ProfileVo;

/**
 * DTO pour l'affichage léger d'un profile
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 01/11/2017
 * @version 1.0
 */
public class LightProfileDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected Long userId;
	protected String lastName;
	protected String firstName;
	protected String category;
	protected Calendar timestamps;
	private String job;
	
	/**
	 * Constructor from vo
	 * @param vo
	 */
	public LightProfileDto(ProfileVo vo) {
		this.id = vo.getId();
		this.firstName = vo.getFirstName();
		this.lastName = vo.getLastName();
		this.userId = vo.getUserId();
		this.timestamps = vo.getTimestamps();
		this.category = vo.getCategory();
		this.job = vo.getJob();
	}

	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public LightProfileDto setJob(String job) {
		this.job = job;
		return this;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public LightProfileDto setCategory(String category) {
		this.category = category;
		return this;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public LightProfileDto setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public LightProfileDto setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public LightProfileDto setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public LightProfileDto setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * @return the timestamps
	 */
	public Calendar getTimestamps() {
		return timestamps;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public LightProfileDto setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}	
}
