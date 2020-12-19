package com.db;

import java.sql.*;
import java.util.ArrayList;

import com.Employee;
import com.communication.VolException;

public class Database {
	
	private static Database dbInstance = null;
	Connection dbConnection = null;
	Statement statement = null;
	

	private Database() throws SQLException, ClassNotFoundException {
	    Class.forName("com.mysql.jdbc.Driver");
		dbConnection = DriverManager.getConnection("jdbc:mysql://35.197.247.91:3306/employee_schema", "root", "asdf1234");
	}
	
	public static Database getInstance() throws SQLException, ClassNotFoundException {
		if (dbInstance == null)
			dbInstance = new Database();
		return dbInstance;
	}
	
	public ResultSet fetchEmployeeRecords(int page, int itemsPerPage) {
		
		int offset = (itemsPerPage * page) - itemsPerPage; // to fetch 'itemsPerPage' items of page 'page'
		
		String query;
		
		if (page == 0 || itemsPerPage == 0)
			query = "select * from employee_data"; // fetch all records
		else
			query = "select * from employee_data limit " + offset + ", " + itemsPerPage;
		
		ResultSet result = null;
		
		try {
			statement = dbConnection.createStatement();
			result = statement.executeQuery(query);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		return result;
		
	}
	
	public ResultSet fetchEmployeeCount() {
		ResultSet result = null;
		
		try {
			statement = dbConnection.createStatement();
			result = statement.executeQuery("select count(id) as employeeCount from employee_data");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		return result;
	}
	
	public String addEmployee(Employee employee) throws Exception {
		String result = null;
		
		String query = "INSERT INTO employee_data (`id`,`name`,`surname`,`joinDate`,`gender`,`birthDate` ) VALUES('"+employee.getId()+"', '"+employee.getName()+"','"+employee.getSurname()+"','"+employee.getJoinDate().toString()+"','"+employee.getGender()+"','"+employee.getBirthDate().toString()+"');";
		
		try {
			statement = dbConnection.createStatement();
			result = statement.executeUpdate(query) + "";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (e.getMessage().contains("Duplicate"))
				result = "duplicate";
			else 
				throw e;
		}
		
		return result;
	}
	
	public int deleteEmployees(ArrayList<String> ids) throws Exception {
		String result = null;
		
		int rowsChanged = 0;
		
		String query = "";
		
		try {
			statement = dbConnection.createStatement();
			for (String id : ids) {
				if (!isInteger(id))
					throw new VolException("Expected numeric IDs, bad data: " + id, rowsChanged);
				query = "DELETE FROM `employee_data` WHERE (`id` = '"+id+"');";
				result = statement.executeUpdate(query) + "";
				if (isInteger(result))
					rowsChanged += Integer.parseInt(result);
				else
					throw new VolException(result, rowsChanged);
			}
		} catch (Exception e) {
			System.out.println("Encountered error after deleting " + rowsChanged + " rows"); 
			throw new VolException(e, rowsChanged);
		}
		
		return rowsChanged;
	}
	
	public void closeStatement() {
		try {
			if (!statement.isClosed())
				statement.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

}