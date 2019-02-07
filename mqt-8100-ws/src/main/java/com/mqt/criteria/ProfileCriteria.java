package com.mqt.criteria;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;

/**
 * Criteria for research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/08/2017
 * @version 1.0
 */
public class ProfileCriteria extends AbstractResource implements Criteria {
	private static final long serialVersionUID = 1L;
	private String lastName;
	private String firstName;
	private Calendar bornDate;
	private String job;
	private String phoneNumber;
	private Boolean isVisible;
	private String category;

	/**
	 * @param id
	 *            the id to set
	 */
	public ProfileCriteria setId(Long id) {
		this.id = id;
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
	public ProfileCriteria setLastName(String lastName) {
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
	public ProfileCriteria setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * @return the bornDate
	 */
	public Calendar getBornDate() {
		return bornDate;
	}

	/**
	 * @param bornDate
	 *            the bornDate to set
	 */
	public ProfileCriteria setBornDate(Calendar bornDate) {
		this.bornDate = bornDate;
		return this;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public ProfileCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job
	 *            the job to set
	 */
	public ProfileCriteria setJob(String job) {
		this.job = job;
		return this;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public ProfileCriteria setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public ProfileCriteria setCategory(String category) {
		this.category = category;
		return this;
	}

	/**
	 * @return the isVisible
	 */
	public Boolean getIsVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public ProfileCriteria setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
		return this;
	}
}
