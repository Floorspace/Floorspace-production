package com.floorspace.venus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.floorspace.venus.pojo.UserInfo;


public class UserInfoDao {
 private JdbcTemplate jdbcTemplate;
 
 private final static String GET_USER_INFO = "select * from basic_user_details where user_id=?;";
 
 private final static String INSERT_USER_INFO = "insert into basic_user_details values(?,?,?,?);";
 
 private final static String UPDATE_USER_INFO = "update basic_user_details set ";
 
 public UserInfoDao(DataSource dataSource){
	 jdbcTemplate = new JdbcTemplate(dataSource);
 }

 public UserInfo getUserInfo(UUID userID) {
	 List<Object> args = new ArrayList<Object>();
	 args.add(userID.toString());
	 List<UserInfo> userInfoList = jdbcTemplate.query(GET_USER_INFO,args.toArray(),new UserInfoMapper());
	 if(userInfoList.size()>1) {
		 System.err.println("Multiple entries present for the same user" + userID);
	 }
	 return userInfoList.get(0);
 }
 
 
 public boolean createUser(UserInfo userInfo) {
	 List<Object> args = new ArrayList<Object>();
	 args.add(userInfo.getUserID().toString());
	 args.add(userInfo.getUserName());
	 args.add(userInfo.getMobileNumber());
	 args.add(userInfo.getRegistrationDate());
	 int rows = jdbcTemplate.update(INSERT_USER_INFO, args.toArray());
	 if(rows==1) {
		 return true;
	 }
	 return false;
 }
 
// public static void main(String[] args) {
//	 DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost/easypay_prod?useSSL=false", "tarun", "tarun");
//	 UserInfoDao dao = new UserInfoDao(dataSource);
//	 UUID userId = UUID.randomUUID();
//	 dao.createUser(new UserInfo(userId, "Tarun kumar", "9897655165", 1558207829));
//	 UserInfo info = dao.getUserInfo(userId);
//	 System.out.println(info.getUserName());
//}

}

class UserInfoMapper implements RowMapper<UserInfo>{

	public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserID(UUID.fromString(rs.getString("user_id")));
		userInfo.setMobileNumber(rs.getString("mobile_number"));
		userInfo.setUserName(rs.getString("name"));
		userInfo.setRegistrationDate(rs.getInt("registered_date"));
		return userInfo;
	}
}
