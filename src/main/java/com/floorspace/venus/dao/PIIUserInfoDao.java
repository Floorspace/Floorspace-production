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

import com.floorspace.venus.pojo.PIIUserInfo;


public class PIIUserInfoDao {
 private JdbcTemplate jdbcTemplate;
 
 private final static String GET_PII_USER_INFO = "select * from pii_user_details where user_id=?;";
 
 private final static String INSERT_PII_USER_INFO = "insert into pii_user_details values(?,?,?,?,?,?,?);";
 
 private final static String UPDATE_PII_USER_INFO = "update pii_user_details "
 		+ "  set name = IfNull(?, name),"
 		+ "  mobile_number = IfNull(?, mobile_number),"
 		+ "  email_id = IfNull(?, email_id),"
 		+ "  aadhar_number = IfNull(?, aadhar_number),"
 		+ "  pan_number = IfNull(?, pan_number)"
 		+ " where user_id = ?";
 
 
 public PIIUserInfoDao(DataSource dataSource){
	 jdbcTemplate = new JdbcTemplate(dataSource);
 }

 public PIIUserInfo getUserInfo(UUID userID) {
	 List<Object> args = new ArrayList<Object>();
	 args.add(userID.toString());
	 List<PIIUserInfo> userInfoList = jdbcTemplate.query(GET_PII_USER_INFO,args.toArray(),new PIIUserInfoMapper());
	 if(userInfoList.size()>1) {
		 System.err.println("Multiple entries present for the same user" + userID);
	 }
	 if(userInfoList.isEmpty()) {
		 return null;
	 }
	 return userInfoList.get(0);
 }
 
 
 public boolean createUser(PIIUserInfo piiUserInfo) {
	 List<Object> args = new ArrayList<Object>();
	 args.add(piiUserInfo.getUserID().toString());
	 args.add(piiUserInfo.getFullName());
	 args.add(piiUserInfo.getRegistrationDate());
	 args.add(piiUserInfo.getMobileNumber());
	 args.add(piiUserInfo.getEmailID());
	 args.add(piiUserInfo.getAadharNumber());
	 args.add(piiUserInfo.getPanNumber());
	 int rows = jdbcTemplate.update(INSERT_PII_USER_INFO, args.toArray());
	 if(rows==1) {
		 return true;
	 }
	 return false;
 }
 
 public boolean updateUser(PIIUserInfo piiUserInfo) {
	 List<Object> args = new ArrayList<Object>();
	 args.add(piiUserInfo.getFullName());
	 args.add(piiUserInfo.getMobileNumber());
	 args.add(piiUserInfo.getEmailID());
	 args.add(piiUserInfo.getAadharNumber());
	 args.add(piiUserInfo.getPanNumber());
	 args.add(piiUserInfo.getUserID().toString());
	 int rows = jdbcTemplate.update(UPDATE_PII_USER_INFO, args.toArray());
	 if(rows==1) {
		 return true;
	 }
	 return false;
 }
 
// public static void main(String[] args) {
//	 DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost/easypay_prod?useSSL=false", "tarun", "tarun");
//	 UserInfoDao dao = new UserInfoDao(dataSource);
//	 UUID userId = UUID.randomUUID();
//	 dao.createUser(new PIIUserInfo(userId, "Tarun kumar", "9897655165", 1558207829));
//	 PIIUserInfo info = dao.getUserInfo(userId);
//	 System.out.println(info.getUserName());
//}

}

class PIIUserInfoMapper implements RowMapper<PIIUserInfo>{

	public PIIUserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		PIIUserInfo piiUserInfo = new PIIUserInfo();
		piiUserInfo.setUserID(UUID.fromString(rs.getString("user_id")));
		piiUserInfo.setFullName(rs.getString("name"));
		piiUserInfo.setRegistrationDate(rs.getInt("registration_time"));
		piiUserInfo.setMobileNumber(rs.getString("mobile_number"));
		piiUserInfo.setEmailID(rs.getString("email_id"));
		piiUserInfo.setAadharNumber(rs.getString("aadhar_number"));
		piiUserInfo.setPanNumber(rs.getString("pan_number"));
		return piiUserInfo;
	}
}
