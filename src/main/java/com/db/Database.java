package com.db;

import java.sql.*;
import java.util.ArrayList;

import com.Employee;
import com.communication.VolException;

public class Database {
	
	private static Database dbInstance = null;
	Connection dbConnection = null;
	Statement statement = null;
	
	private Database() throws Exception {
			String driver = DBInfo.getInstance().getDriver();
			String dbHost = DBInfo.getInstance().getHost();
			String dbPort = DBInfo.getInstance().getPort();
			String dbSchema = DBInfo.getInstance().getSchema();
			String dbUser = DBInfo.getInstance().getUser();
			String dbPass = DBInfo.getInstance().getPass();
		 	Class.forName(driver);
		 	dbConnection = 
		 			DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+'/'
		 					+ dbSchema, dbUser, dbPass);	
	 	}

	public static Database getInstance() throws Exception {
		if (dbInstance == null)
			dbInstance = new Database();
		return dbInstance;
	}

	public ResultSet fetchEmployeeRecords(int page, int itemsPerPage) {

		int offset = (itemsPerPage * page) - itemsPerPage; // to fetch 'itemsPerPage' items of page 'page'

		String query;

		if (page == 0 || itemsPerPage == 0)
			query = "select * from employees"; // fetch all records
		else
			query = "select * from employees limit " + offset + ", " + itemsPerPage;

		ResultSet result = null;

		try {
			statement = dbConnection.createStatement();
			result = statement.executeQuery(query);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;

	}

	// overload for searching by surname
	public ResultSet fetchEmployeeRecords(String surname, int items) {

		String query;
		
		if (items == 0) {
			query = "select * from employees where surname LIKE '" + surname + "%' order by surname";
		} else {
			query = "select * from employees where surname LIKE '" + surname + "%' order by surname limit 0, " + items;
		}
		
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
			result = statement.executeQuery("select count(id) as employeeCount from employees");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;
	}
	
	public ResultSet fetchEmployeeCount(String surname) {
		ResultSet result = null;

		try {
			statement = dbConnection.createStatement();
			result = statement.executeQuery("select count(id) as employeeCount from employees where surname LIKE '" + surname + "%'");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;
	}

	public String addEmployee(Employee employee) throws Exception {
		String result = null;

		String query = "INSERT INTO employees (`id`,`name`,`surname`,`joinDate`,`gender`,`birthDate` ) VALUES('"
				+ employee.getId() + "', '" + employee.getName() + "','" + employee.getSurname() + "','"
				+ employee.getJoinDate().toString() + "','" + employee.getGender() + "','"
				+ employee.getBirthDate().toString() + "');";

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
				query = "DELETE FROM `employees` WHERE (`id` = '" + id + "');";
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

}