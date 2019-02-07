package com.mqt.pojo.dto;

import java.util.Calendar;

import com.mqt.pojo.AbstractResource;
import com.mqt.pojo.vo.ProfileVo;
import com.mqt.pojo.vo.UserAccountVo;

/**
 * Complete Profile Response
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/09/2017
 * @version 1.0
 */
public class ProfileDto extends AbstractResource {
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
	private String mail;
	private Integer state;
	private Boolean isOwner;
	private Boolean isAdmin;
	
	/**
	 * Constructor from profile
	 * 
	 * @param profile
	 * @param user
	 */
	public ProfileDto(ProfileVo profile, UserAccountVo user, Boolean isOwner, Boolean isAdmin) {
		this.id = profile.getId();
		this.userId = profile.getUserId();
		this.lastName = profile.getLastName();
		this.firstName = profile.getFirstName();
		this.address = profile.getAddress();
		this.category = profile.getCategory();
		this.job = profile.getJob();
		this.phoneNumber = profile.getPhoneNumber();
		this.bornDate = profile.getBornDate();
		this.avatar = profile.getAvatar();
		this.cover = profile.getCover();
		this.timestamps = profile.getTimestamps();
		this.mail = user.getMail();
		this.state = user.getState();
		this.isOwner = isOwner;
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the isAdmin
	 */
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public ProfileDto setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
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
	public ProfileDto setCategory(String category) {
		this.category = category;
		return this;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public ProfileDto setId(Long id) {
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
	public ProfileDto setUserId(Long userId) {
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
	public ProfileDto setLastName(String lastName) {
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
	public ProfileDto setFirstName(String firstName) {
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
	public ProfileDto setAddress(String address) {
		this.address = address;
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
	public ProfileDto setJob(String job) {
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
	public ProfileDto setPhoneNumber(String phoneNumber) {
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
	public ProfileDto setBornDate(Calendar bornDate) {
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
	public ProfileDto setAvatar(byte[] avatar) {
		this.avatar = avatar;
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
	public ProfileDto setCover(byte[] cover) {
		this.cover = cover;
		return this;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public ProfileDto setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            the mail to set
	 */
	public ProfileDto setMail(String mail) {
		this.mail = mail;
		return this;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public ProfileDto setState(Integer state) {
		this.state = state;
		return this;
	}

	/**
	 * @return the isOwner
	 */
	public Boolean getIsOwner() {
		return isOwner;
	}

	/**
	 * @param isOwner the isOwner to set
	 */
	public ProfileDto setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
		return this;
	}

}
