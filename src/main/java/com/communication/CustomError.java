package com.communication;

public class CustomError {
	// custom exception
	
	private final String message;
	
	public CustomError(String message){
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}