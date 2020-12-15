package com.communication;


public class CountResponse extends Response {

	private final int employeeCount;

	public CountResponse(int employeeCount, boolean success) {
		super(success);
		this.employeeCount = employeeCount;
	}
	
	public CountResponse(CustomError error) {
		super(error);
		this.employeeCount = -1;
	}

	public int getEmployeeCount() {
		return employeeCount;
	}
}
