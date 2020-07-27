package com.floorspace.venus.pojo;

import java.util.UUID;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreationPayload extends PIIUserInfo {

	public UserCreationPayload() {
		super();
	}
	public UserCreationPayload(UUID userID, String fullName, Integer registrationDate, String mobileNumber, String emailID,
			String panNumber, String aadharNumber) {
		super(userID, fullName, registrationDate, mobileNumber, emailID, panNumber, aadharNumber);
	}
}
	