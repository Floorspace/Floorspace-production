package com.floorspace.venus.pojo;

public class PaymentInfo {

	private PaymentMode paymentMode;
	private Double availableBalance; 
	private boolean wallet; 
	private String description; 
	private Integer cashBackAmount; 
	private boolean userLinked;
	
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public boolean isWallet() {
		return wallet;
	}
	public void setWallet(boolean wallet) {
		this.wallet = wallet;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCashBackAmount() {
		return cashBackAmount;
	}
	public void setCashBackAmount(Integer cashBackAmount) {
		this.cashBackAmount = cashBackAmount;
	}
	public boolean isUserLinked() {
		return userLinked;
	}
	public void setUserLinked(boolean userLinked) {
		this.userLinked = userLinked;
	} 
	
}
