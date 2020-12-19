package com.communication;

public class Response {
	
	private final boolean success;
	private final String error;
	private final int rowsChanged;
	
	public Response(boolean success, int rowsChanged) {
		super();
		this.success = success;
		this.error = null;
		this.rowsChanged = rowsChanged;
	}
	
	public Response(VolException error ) {
		super();
		this.success = false;
		this.error = error.getMessage();
		this.rowsChanged = error.getRowsChanged();
	}

	public boolean isSuccess() {
		return success;
	}

	public String getError() {
		return error;
	}

	public int getRowsChanged() {
		return rowsChanged;
	}
	
	
}
