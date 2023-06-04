package com.example.newsfeed.customexeception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
	
	private final HttpStatus statusCode;
	private final String message;
	
	public CustomException(HttpStatus statusCode, String msg) {
		super(msg);
		this.message = msg;
		this.statusCode = statusCode;
	}
	
	public HttpStatus getStatusCode() {
		return this.statusCode;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
	
	
	
}
