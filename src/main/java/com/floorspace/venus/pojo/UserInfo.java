package com.floorspace.venus.pojo;

import java.util.Date;
import java.util.UUID;

public class UserInfo {

	private UUID userID;
	private String userName;
	private String mobileNumber;
	private Integer registrationDate;
	
	public UserInfo(UUID userID, String userName, String mobileNumber, Integer registrationDate) {
		this.userID = userID;
		this.userName = userName;
		this.mobileNumber = mobileNumber;
		this.registrationDate = registrationDate;
	}
	
	public UserInfo() {
		
	}
	
	public UUID getUserID() {
		return userID;
	}
	public void setUserID(UUID userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Integer getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Integer registrationDate) {
		this.registrationDate = registrationDate;
	}
	
}
