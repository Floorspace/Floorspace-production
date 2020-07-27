package com.floorspace.venus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.floorspace.venus.pojo.PaymentInfo;
import com.floorspace.venus.pojo.PaymentMode;




public class PaymentsDao {
	private JdbcTemplate jdbcTemplate;
	 
	 private final static String GET_ALL_PAYMENTS = "select * from payment_options";
	 
	 private final static String INSERT_NEW_PAYMENT= "insert into payment_options values(?,?,?,?,?,?) "
	 		+ "	ON DUPLICATE KEY UPDATE available_balance=? , is_wallet=? , description=? , cashback_amount=? , is_user_linked=?";
	 
	 private final static String UPDATE_USER_INFO = "update payment_options set ";
	 
	 public PaymentsDao(DataSource dataSource){
		 jdbcTemplate = new JdbcTemplate(dataSource);
	 }

	 public List<PaymentInfo> getPaymentsList() {
		return jdbcTemplate.query(GET_ALL_PAYMENTS,new PaymentInfoMapper());
	 }
	 
	 
	 public boolean addNewPayment(PaymentInfo info) {
		 List<Object> args = new ArrayList<Object>();
		 args.add(info.getPaymentMode().toString());
		 args.add(info.getAvailableBalance());
		 args.add(info.isWallet());
		 args.add(info.getDescription());
		 args.add(info.getCashBackAmount());
		 args.add(info.isUserLinked());
		 
		 args.add(info.getAvailableBalance());
		 args.add(info.isWallet());
		 args.add(info.getDescription());
		 args.add(info.getCashBackAmount());
		 args.add(info.isUserLinked());
		 
		 int rows = jdbcTemplate.update(INSERT_NEW_PAYMENT, args.toArray());
		 if(rows>0) {
			 return true;
		 }
		 return false;
	 }
	 
	// public static void main(String[] args) {
//		 DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost/easypay_prod?useSSL=false", "tarun", "tarun");
//		 UserInfoDao dao = new UserInfoDao(dataSource);
//		 UUID userId = UUID.randomUUID();
//		 dao.createUser(new UserInfo(userId, "Tarun kumar", "9897655165", 1558207829));
//		 UserInfo info = dao.getUserInfo(userId);
//		 System.out.println(info.getUserName());
	//}

	}

	class PaymentInfoMapper implements RowMapper<PaymentInfo>{

		public PaymentInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			PaymentInfo info = new PaymentInfo();
			info.setPaymentMode(PaymentMode.getPaymentModeFromName(rs.getString("payment_mode")));
			info.setDescription(rs.getString("description"));
			info.setCashBackAmount(rs.getInt("cashback_amount"));
			info.setUserLinked(rs.getBoolean("is_user_linked"));
			info.setWallet(rs.getBoolean("is_wallet"));
			info.setAvailableBalance(rs.getDouble("available_balance"));
			return info;
		}
}
