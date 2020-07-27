package com.floorspace.venus.pojo;

import java.util.UUID;

public class BuyerInfo extends PIIUserInfo {

	
	public BuyerInfo() {
		super();
	}
	
	public BuyerInfo(UUID userID, String fullName, Integer registrationDate, String mobileNumber, String emailID,
			String panNumber, String aadharNumber) {
		super(userID, fullName, registrationDate, mobileNumber, emailID, panNumber, aadharNumber);
	}
}