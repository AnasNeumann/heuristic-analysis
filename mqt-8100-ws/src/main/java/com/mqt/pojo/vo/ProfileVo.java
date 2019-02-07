package com.mqt.pojo.vo;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;

/**
 * Profile table mapping
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/08/2017
 * @version 1.0
 */
public class ProfileVo extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String lastName;
	private String firstName;
	private String address;
	private String job;
	private String phoneNumber;
	private Calendar bornDate;
	private byte[] avatar;
	private byte[] cover;
	private String category;

	/**
	 * @param id
	 *            the id to set
	 */
	public ProfileVo setId(Long id) {
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
	public ProfileVo setUserId(Long userId) {
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
	public ProfileVo setLastName(String lastName) {
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
	public ProfileVo setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public ProfileVo setAddress(String address) {
		this.address = address;
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
	public ProfileVo setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public ProfileVo setBornDate(Calendar bornDate) {
		this.bornDate = bornDate;
		return this;
	}

	/**
	 * @return the avatar
	 */
	public byte[] getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar
	 *            the avatar to set
	 */
	public ProfileVo setAvatar(byte[] avatar) {
		this.avatar = avatar;
		return this;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public ProfileVo setTimestamps(Calendar timestamps) {
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
	public ProfileVo setJob(String job) {
		this.job = job;
		return this;
	}

	/**
	 * @return the cover
	 */
	public byte[] getCover() {
		return cover;
	}

	/**
	 * @param cover
	 *            the cover to set
	 */
	public ProfileVo setCover(byte[] cover) {
		this.cover = cover;
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
	public ProfileVo setCategory(String category) {
		this.category = category;
		return this;
	}
}
