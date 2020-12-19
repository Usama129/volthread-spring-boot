package com.communication;


public class CountResponse extends Response {

	private final int employeeCount;

	public CountResponse(int employeeCount, boolean success) {
		super(success, 0); // count op always changes 0 rows
		this.employeeCount = employeeCount;
	}
	
	public CountResponse(VolException error) {
		super(error);
		this.employeeCount = -1;
	}

	public int getEmployeeCount() {
		return employeeCount;
	}
}
