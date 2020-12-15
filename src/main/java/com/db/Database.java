package com.db;

import java.sql.*;

import com.EmployeeBean;

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
	
	public String addEmployee(EmployeeBean employee) {
		String result = null;
		
		String query = "INSERT INTO employee_data (`id`,`name`,`surname`,`joinDate`,`gender`,`birthDate` ) VALUES('"+employee.getId()+"', '"+employee.getName()+"','"+employee.getSurname()+"','"+employee.getJoinDate().toString()+"','"+employee.getGender()+"','"+employee.getBirthDate().toString()+"');";
		
		try {
			statement = dbConnection.createStatement();
			result = statement.executeUpdate(query) + "";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (e.getMessage().contains("Duplicate"))
				result = "duplicate";
		}
		
		return result;
	}
	
	public void closeStatement() {
		try {
			if (!statement.isClosed())
				statement.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}