package com.floorspace.venus.pojo;

import java.util.UUID;

public class UserCreationPayload {

	private String userName;
	private String mobileNumber;
	
	public UserCreationPayload(String userName, String mobileNumber) {
		this.userName = userName;
		this.mobileNumber = mobileNumber;
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
	
}
