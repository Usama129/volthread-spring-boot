package com.communication;

public class DeleteEmployeesResponse extends Response {
	
	public DeleteEmployeesResponse(boolean success, int rowsDeleted) {
		super(success, rowsDeleted);
	}
	
	public DeleteEmployeesResponse(VolException error) {
		super(error);
	}

}
