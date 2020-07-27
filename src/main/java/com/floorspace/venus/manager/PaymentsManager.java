package com.floorspace.venus.manager;

import java.util.List;

import com.floorspace.venus.dao.PaymentsDao;
import com.floorspace.venus.pojo.PaymentInfo;


public class PaymentsManager {

	private PaymentsDao paymentsDao;
	
	public PaymentsManager(PaymentsDao paymentsDao) {
		this.paymentsDao = paymentsDao;
	}
	
	public List<PaymentInfo> getAllPayments() {
		List<PaymentInfo> payments=  paymentsDao.getPaymentsList();
		updateLatestBalances(payments);
		return payments;
	}
	
	public void addPayments(List<PaymentInfo> paymentInfoList) {
		for(PaymentInfo info: paymentInfoList) {
			paymentsDao.addNewPayment(info);
		}
	}

	private void updateLatestBalances(List<PaymentInfo> payments) {
		
	}
	
}
