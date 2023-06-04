package com.example.newsfeed.customexeception;

public class ErrorResponse {
	private int httpStatusCode;
	private String message;
	
	public ErrorResponse(int httpStatusCode, String message) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.message = message;
	}
	
	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
