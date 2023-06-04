package com.example.newsfeed.customexeception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException){
		ErrorResponse errorResponse = new ErrorResponse(customException.getStatusCode().value(),customException.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse,customException.getStatusCode());
	}
}
