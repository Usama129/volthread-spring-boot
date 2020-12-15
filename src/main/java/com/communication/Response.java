package com.communication;

public class Response {
	
	private final boolean success;
	private final CustomError error;
	
	public Response(boolean success ) {
		super();
		this.success = success;
		this.error = null;
	}
	
	public Response(CustomError error ) {
		super();
		this.success = false;
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public CustomError getError() {
		return error;
	}
	
	
}
