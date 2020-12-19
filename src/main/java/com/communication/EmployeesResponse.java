package com.communication;


import java.util.ArrayList;

import com.Employee;

public class EmployeesResponse extends Response {
	
	private final int count;
	private final ArrayList<Employee> list;
	
	public EmployeesResponse(int count, ArrayList<Employee> list, boolean success) {
		super(success, 0); // fetch always changes 0 rows
		this.count = count;
		this.list = list;
	}
	
	public EmployeesResponse(VolException error) {
		super(error);
		this.list = null;
		this.count = -1;
	}

	public int getCount() {
		return count;
	}

	public ArrayList<Employee> getList() {
		return list;
	}
	
	
}