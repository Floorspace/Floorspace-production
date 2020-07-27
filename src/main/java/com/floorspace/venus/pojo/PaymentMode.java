package com.floorspace.venus.pojo;


public enum PaymentMode {
	PAYTM("Paytm"),
	PHONEPE("PhonePe"),
	GOOGLEPAY("Google pay"),
	SIMPL("Simpl");
	
	private String displayName;
	
	
	private PaymentMode(String displayName) {
		this.displayName = displayName;
	}
	
	public static PaymentMode getPaymentModeFromName(String name) {
		for(PaymentMode mode : PaymentMode.values()) {
			if(mode.name().equalsIgnoreCase(name)) {
				return mode;
			}
		}
		return null;
	}
	
}
