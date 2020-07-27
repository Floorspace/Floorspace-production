package com.floorspace.venus.manager;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;

import com.floorspace.venus.dao.UserInfoDao;
import com.floorspace.venus.pojo.InfoCreationResponse;
import com.floorspace.venus.pojo.UserCreationPayload;
import com.floorspace.venus.pojo.UserInfo;


public class InfoManager {

	private UserInfoDao userInfoDao;
	
	
	public InfoManager(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	
	public UserInfo getUserInfo(UUID userID) {
		UserInfo userInfo = userInfoDao.getUserInfo(userID);
		return userInfo;
	}
	
	public InfoCreationResponse createUser(UserCreationPayload payload) {
		UUID userID = UUID.randomUUID();
		int now = (int)System.currentTimeMillis()/1000;
		UserInfo userInfo = new UserInfo(userID, payload.getUserName(), payload.getMobileNumber(), now);
		boolean isUserCreated = userInfoDao.createUser(userInfo);
		return new InfoCreationResponse(userID, isUserCreated);
	}

	
	
}
