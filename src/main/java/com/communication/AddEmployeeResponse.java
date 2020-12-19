package com.communication;

public class AddEmployeeResponse extends Response {

	private final String fullName;
	
	public AddEmployeeResponse(boolean success, String fullName) {
		super(success, 1); // add always adds 1 row
		this.fullName = fullName;
	}
	
	public AddEmployeeResponse(VolException error) {
		super(error);
		this.fullName = null;
	}

	public String getFullName() {
		return fullName;
	}
}
