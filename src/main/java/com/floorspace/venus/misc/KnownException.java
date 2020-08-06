package com.floorspace.venus.misc;

public class KnownException extends RuntimeException {

	
	private int statusCode;
	private String errorMessage;
	
	public KnownException(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	
	
}
