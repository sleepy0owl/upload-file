package com.msqube.qii.sales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException{
	public MyFileNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
	
	public MyFileNotFoundException(String message, Throwable cause) {
		// TODO Auto-generated constructor stub
		super(message, cause);
	}
}
