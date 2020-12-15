package com.communication;


import java.util.ArrayList;

import com.EmployeeBean;

public class EmployeesResponse extends Response {
	
	private final int count;
	private final ArrayList<EmployeeBean> list;
	
	public EmployeesResponse(int count, ArrayList<EmployeeBean> list, boolean success) {
		super(success);
		this.count = count;
		this.list = list;
	}
	
	public EmployeesResponse(CustomError error) {
		super(error);
		this.list = null;
		this.count = -1;
	}

	public int getCount() {
		return count;
	}

	public ArrayList<EmployeeBean> getList() {
		return list;
	}
	
	
}