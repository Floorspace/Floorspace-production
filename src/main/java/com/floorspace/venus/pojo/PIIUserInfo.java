package com.floorspace.venus.pojo;

import java.util.UUID;

/**
 * 
 * @author tarun_itachi
 * 
 * PII information based class which will be hidden until user pays money
 * 
 * This information is stored in a seperate class
 * 
 */
public class PIIUserInfo {
	private UUID userID;
	private String fullName;
	private Integer registrationDate;
	private String mobileNumber;
	private String emailID;
	private String panNumber;
	private String aadharNumber;
	
	public PIIUserInfo(UUID userID, String fullName, Integer registrationDate, String mobileNumber, String emailID,
			String panNumber, String aadharNumber) {
		this.userID = userID;
		this.fullName = fullName;
		this.registrationDate = registrationDate;
		this.mobileNumber = mobileNumber;
		this.emailID = emailID;
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
	}

	public PIIUserInfo() {
		
	}
	
	public UUID getUserID() {
		return userID;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	public Integer getRegistrationDate() {
		return registrationDate;
	}

	public String getEmailID() {
		return emailID;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setUserID(UUID userID) {
		this.userID = userID;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setRegistrationDate(Integer registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
}
