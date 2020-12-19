package com.communication;

@SuppressWarnings("serial")
public class VolException extends Exception {
	
	private final int rowsChanged;

	public VolException(Exception e, int rowsChanged) {
		super(e);
		this.rowsChanged = rowsChanged;
	}
	
	public VolException(String message, int rowsChanged) {
		super(message);
		this.rowsChanged = rowsChanged;
	}
	
	public VolException(String message) {
		super(message);
		this.rowsChanged = 0;
	}
	
	public int getRowsChanged() {
		return rowsChanged;
	}
	
	
}