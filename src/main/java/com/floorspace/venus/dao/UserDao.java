package com.floorspace.venus.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.easypay.dao.user.pojo.Gender;
import com.floorspace.venus.enums.Caste;
import com.floorspace.venus.enums.Language;
import com.floorspace.venus.enums.Religion;
import com.floorspace.venus.enums.State;
import com.floorspace.venus.misc.SharedMemoryExpensiveConstants;
import com.floorspace.venus.pojo.CommonAdPoolDetails;
import com.floorspace.venus.pojo.PIIUserInfo;
import com.floorspace.venus.pojo.UserInfo;
import com.floorspace.venus.pojo.VisibleUserInfo;


public class UserDao {
 private JdbcTemplate jdbcTemplate;
 
 
// private final static String JOIN_BOTH_USER_TABLES = "select * from pii_user_details JOIN visible_user_details";
 
 
 private final static String GET_USER_INFO = "select * from pii_user_details INNER JOIN visible_user_details ON pii_user_details.user_id=visible_user_details.user_id where pii_user_details.user_id=?;";
 private final static String GET_VISIBLE_USER_INFO = "select * from visible_user_details where user_id=?;";
 
 private final static String INSERT_PII_USER_INFO = "insert into pii_user_details values(?,?,?,?,?,?,?);";
 private final static String INSERT_VISIBLE_USER_INFO = "insert into visible_user_details values(?,?,?,?,?,?,?);";
 
 private final static String UPDATE_VISIBLE_USER_INFO = "update visible_user_details "
	 		+ "  set caste = IfNull(?, caste),"
	 		+ "  religion = IfNull(?, religion),"
	 		+ "  gender = IfNull(?, gender),"
	 		+ "  age = IfNull(?, age),"
	 		+ "  known_languages = IfNull(?, known_languages),"
	 		+ "  native_state = IfNull(?, native_state)"
	 		+ " where user_id = ?";
 
 private final static String UPDATE_PII_USER_INFO = "update pii_user_details "
	 		+ "  set name = IfNull(?, name),"
	 		+ "  mobile_number = IfNull(?, mobile_number),"
	 		+ "  email_id = IfNull(?, email_id),"
	 		+ "  aadhar_number = IfNull(?, aadhar_number),"
	 		+ "  pan_number = IfNull(?, pan_number)"
	 		+ " where user_id = ?";
 
 public UserDao(DataSource dataSource){
	 jdbcTemplate = new JdbcTemplate(dataSource);
 }

 public UserInfo getUser(UUID userID, boolean hidePII) {
	 
	 if(hidePII) {
		 List<Object> args = new ArrayList<Object>();
		 args.add(userID.toString());
		 List<VisibleUserInfo> userInfoList = jdbcTemplate.query(GET_VISIBLE_USER_INFO,args.toArray(),new VisibleUserInfoMapper());
		 if(userInfoList.size()>1) {
			 System.err.println("Multiple entries present for the same user" + userID);
		 }
		 if(userInfoList.isEmpty()) {
			 return null;
		 } 
		  return new UserInfo(new PIIUserInfo(userID, null, null, null, null, null, null), userInfoList.get(0));
	 }
	 List<Object> args = new ArrayList<Object>();
	 args.add(userID.toString());
	 List<UserInfo> userInfoList = jdbcTemplate.query(GET_USER_INFO,args.toArray(),new UserInfoMapper());
	 if(userInfoList.size()>1) {
		 System.err.println("Multiple entries present for the same user" + userID);
	 }
	 if(userInfoList.isEmpty()) {
		 return null;
	 } 
	 return userInfoList.get(0);
	 
	 
 }
 
 
 public void createUser(UserInfo userInfo) throws SQLException {
	 
	     DataSource dataSource = jdbcTemplate.getDataSource();
		 Connection con  = dataSource.getConnection();
		 PreparedStatement commonInsertStatement = null;
		 PreparedStatement houseInsertStatement = null;
		 
		 con.setAutoCommit(false);
		 
		 String commonInsert = INSERT_PII_USER_INFO;
		 commonInsertStatement = con.prepareStatement(commonInsert);		 
		 setPreparestatementParamsForPII(commonInsertStatement, userInfo.getPiiUserInfo().getUserID(),  userInfo.getPiiUserInfo());
		 commonInsertStatement.executeUpdate();
		 
		 String houseInsert = null;
		 houseInsert = INSERT_VISIBLE_USER_INFO;
		 houseInsertStatement = con.prepareStatement(houseInsert);
		 setPreparestatementParamsForVisibleDetails(houseInsertStatement,userInfo.getPiiUserInfo().getUserID(), userInfo.getVisibleUserInfo());
		 houseInsertStatement.executeUpdate();
		 
      con.commit();
 }
 
 private void setPreparestatementParamsForPII(PreparedStatement statement, UUID userID, PIIUserInfo piiUserInfo) throws SQLException {
	 statement.setString(1, userID.toString());
	 statement.setString(2, piiUserInfo.getFullName());
	 statement.setInt(3, piiUserInfo.getRegistrationDate());
	 statement.setString(4, piiUserInfo.getMobileNumber());
	 statement.setString(5, piiUserInfo.getEmailID());
	 statement.setString(6, piiUserInfo.getAadharNumber());
	 statement.setString(7, piiUserInfo.getPanNumber());
 }
 
 
 private void setPreparestatementParamsForVisibleDetails(PreparedStatement statement, UUID userID, VisibleUserInfo visibleUserInfo) throws SQLException {
	 statement.setString(1, userID.toString());
	 statement.setString(2, visibleUserInfo.getCaste().name());
	 statement.setString(3, visibleUserInfo.getReligion().name());
	 statement.setString(4, visibleUserInfo.getGender().name());
	 statement.setInt(5, visibleUserInfo.getAge());
	 
	 List<Language> languages = visibleUserInfo.getKnownLanguages();
	 String languagesJson = null;
	try {
		languagesJson = SharedMemoryExpensiveConstants.objectMapper.writeValueAsString(languages);
	} catch (Exception e) {
		e.printStackTrace();
	}
	 statement.setString(6, languagesJson);
	 
	 statement.setString(7, visibleUserInfo.getNativeState().name());
 }
 
 public void updateUser(UserInfo userInfo) throws SQLException {
	 
     DataSource dataSource = jdbcTemplate.getDataSource();
	 Connection con  = dataSource.getConnection();
	 PreparedStatement commonInsertStatement = null;
	 PreparedStatement houseInsertStatement = null;
	 
	 con.setAutoCommit(false);
	 
	 String commonInsert = UPDATE_PII_USER_INFO;
	 commonInsertStatement = con.prepareStatement(commonInsert);		 
	 setPreparestatementParamsForPIIUpdate(commonInsertStatement, userInfo.getPiiUserInfo().getUserID(),  userInfo.getPiiUserInfo());
	 commonInsertStatement.executeUpdate();
	 
	 String houseInsert = null;
	 houseInsert = UPDATE_VISIBLE_USER_INFO;
	 houseInsertStatement = con.prepareStatement(houseInsert);
	 setPreparestatementParamsForVisibleDetailsUpdate(houseInsertStatement,userInfo.getPiiUserInfo().getUserID(), userInfo.getVisibleUserInfo());
	 houseInsertStatement.executeUpdate();
	 
  con.commit();
}
 
 
 private void setPreparestatementParamsForPIIUpdate(PreparedStatement statement, UUID userID, PIIUserInfo piiUserInfo) throws SQLException {
	 statement.setString(1, piiUserInfo.getFullName());
	 statement.setString(2, piiUserInfo.getMobileNumber());
	 statement.setString(3, piiUserInfo.getEmailID());
	 statement.setString(4, piiUserInfo.getAadharNumber());
	 statement.setString(5, piiUserInfo.getPanNumber());
	 statement.setString(6, userID.toString());
 }
 
 
 private void setPreparestatementParamsForVisibleDetailsUpdate(PreparedStatement statement, UUID userID, VisibleUserInfo visibleUserInfo) throws SQLException {
	 statement.setString(1, visibleUserInfo.getCaste().name());
	 statement.setString(2, visibleUserInfo.getReligion().name());
	 statement.setString(3, visibleUserInfo.getGender().name());
	 statement.setInt(4, visibleUserInfo.getAge());
	 
	 List<Language> languages = visibleUserInfo.getKnownLanguages();
	 String languagesJson = null;
	try {
		languagesJson = SharedMemoryExpensiveConstants.objectMapper.writeValueAsString(languages);
	} catch (Exception e) {
		e.printStackTrace();
	}
	 statement.setString(5, languagesJson);
	 statement.setString(6, visibleUserInfo.getNativeState().name());
	 statement.setString(7, userID.toString());
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

class VisibleUserInfoMapper implements RowMapper<VisibleUserInfo>{

	public VisibleUserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		String knownLanguagesJson = rs.getString("known_languages");
		TypeReference<List<Language>> type = new TypeReference<List<Language>>() {};
		List<Language> knownLanguages = new ArrayList<Language>();
		try {
			knownLanguages = SharedMemoryExpensiveConstants.objectMapper.readValue(knownLanguagesJson, type);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VisibleUserInfo visibleUserInfo = new VisibleUserInfo(Caste.valueOf(rs.getString("caste")),
				Religion.valueOf(rs.getString("religion")), Gender.valueOf(rs.getString("gender")), rs.getInt("age"), knownLanguages, State.valueOf(rs.getString("native_state")));
		
		return visibleUserInfo;
	}
}

class UserInfoMapper implements RowMapper<UserInfo>{

	public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String knownLanguagesJson = rs.getString("known_languages");
		TypeReference<List<Language>> type = new TypeReference<List<Language>>() {};
		List<Language> knownLanguages = new ArrayList<Language>();
		try {
			knownLanguages = SharedMemoryExpensiveConstants.objectMapper.readValue(knownLanguagesJson, type);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VisibleUserInfo visibleUserInfo = new VisibleUserInfo(Caste.valueOf(rs.getString("caste")),
				Religion.valueOf(rs.getString("religion")), Gender.valueOf(rs.getString("gender")), rs.getInt("age"), knownLanguages, State.valueOf(rs.getString("native_state")));
		
		PIIUserInfo piiUserInfo = new PIIUserInfo();
		piiUserInfo.setUserID(UUID.fromString(rs.getString("user_id")));
		piiUserInfo.setFullName(rs.getString("name"));
		piiUserInfo.setRegistrationDate(rs.getInt("registration_time"));
		piiUserInfo.setMobileNumber(rs.getString("mobile_number"));
		piiUserInfo.setEmailID(rs.getString("email_id"));
		piiUserInfo.setAadharNumber(rs.getString("aadhar_number"));
		piiUserInfo.setPanNumber(rs.getString("pan_number"));
		
		UserInfo userInfo = new UserInfo(piiUserInfo, visibleUserInfo);
		return userInfo;
	}
}

