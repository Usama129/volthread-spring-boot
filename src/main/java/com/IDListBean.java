package com;

import java.util.ArrayList;

public class IDListBean {

	private ArrayList<String> idList = new ArrayList<String>();
	
	/* if an argument constructor is included, a default constructor MUST ALSO be included for this
	* class to be JSON serializable
	*/

	public ArrayList<String> getIdList() {
		return idList;
	}

	public void setIdList(ArrayList<String> idList) {
		this.idList = idList;
	}
	
	
}
