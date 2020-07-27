package com.floorspace.venus.manager;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;

import com.floorspace.venus.dao.PIIUserInfoDao;
import com.floorspace.venus.pojo.InfoCreationResponse;
import com.floorspace.venus.pojo.PIIUserInfo;
import com.floorspace.venus.pojo.UserCreationPayload;


/**
 * 
 * @author tarun_itachi
 *
 * Manager class to handle all info related logic
 */

public class UserInfoManager {

	private PIIUserInfoDao piiUserInfoDao;
	//Merge the PII user data with other user information and return to other modules
	
	public UserInfoManager(PIIUserInfoDao userInfoDao) {
		this.piiUserInfoDao = userInfoDao;
	}
	
	public PIIUserInfo getUserInfo(UUID userID) {
		PIIUserInfo userInfo = piiUserInfoDao.getUserInfo(userID);
		return userInfo;
	}
	
	public InfoCreationResponse createUser(UserCreationPayload payload) {
		UUID userID = UUID.randomUUID();
		int now = (int)(System.currentTimeMillis()/1000);
		UUID newUID = UUID.randomUUID();
		PIIUserInfo userInfo = new PIIUserInfo(newUID, payload.getFullName(), now, payload.getMobileNumber(), payload.getEmailID(), payload.getPanNumber(), payload.getAadharNumber());
		boolean isUserCreated = piiUserInfoDao.createUser(userInfo);
		return new InfoCreationResponse(userID, isUserCreated);
	}
	
	public void updateUser(UserCreationPayload payload) {
		
		piiUserInfoDao.updateUser(payload);
	}

	
	
}
