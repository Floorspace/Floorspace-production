package com.floorspace.venus.pojo;

import java.util.UUID;

public class LenderInfo extends PIIUserInfo {

	
	public LenderInfo() {
		super();
	}
	
	public LenderInfo(UUID userID, String fullName, Integer registrationDate, String mobileNumber, String emailID,
			String panNumber, String aadharNumber) {
		super(userID, fullName, registrationDate, mobileNumber, emailID, panNumber, aadharNumber);
	}
}
