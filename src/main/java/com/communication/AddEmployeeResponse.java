package com.communication;

public class AddEmployeeResponse extends Response {

	private final String fullName;
	
	public AddEmployeeResponse(boolean success, String fullName) {
		super(success);
		this.fullName = fullName;
	}
	
	public AddEmployeeResponse(CustomError error) {
		super(error);
		this.fullName = null;
	}

	public String getFullName() {
		return fullName;
	}
}
