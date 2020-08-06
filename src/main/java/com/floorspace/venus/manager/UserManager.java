package com.floorspace.venus.manager;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;

import com.floorspace.venus.dao.UserDao;
import com.floorspace.venus.misc.KnownException;
import com.floorspace.venus.pojo.InfoCreationResponse;
import com.floorspace.venus.pojo.PIIUserInfo;
import com.floorspace.venus.pojo.UserInfo;


/**
 * 
 * @author tarun_itachi
 *
 * Manager class to handle all info related logic
 */

public class UserManager {

	private UserDao userDao;
	
	public UserManager(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserInfo getUserInfo(UUID userID, boolean hidePII) {
		UserInfo userInfo = userDao.getUser(userID,hidePII);
		return userInfo;
	}
	
	public InfoCreationResponse createUser(UserInfo payload) {
		UUID userID = UUID.randomUUID();
		int now = (int)(System.currentTimeMillis()/1000);
		payload.getPiiUserInfo().setUserID(userID);
		payload.getPiiUserInfo().setRegistrationDate(now);
		try{
			userDao.createUser(payload);
		}
		catch(SQLException se) {
			se.printStackTrace();
			throw new KnownException(400, "SQL exception when creating user..");
		}
		return new InfoCreationResponse(userID, true);
	}
	
	public void updateUser(UserInfo payload) {
		
		try{
			userDao.updateUser(payload);
		}
		catch(SQLException se) {
			se.printStackTrace();
			throw new KnownException(400, "SQL exception when creating user..");
		}
	}

	
	
}
