package com.db;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.EmployeeBean;
import com.communication.*;

public class DBOperations {

	public static EmployeesResponse getEmployees(int page, int items) throws Exception {
		ArrayList<EmployeeBean> list = new ArrayList<EmployeeBean>();

		if (accessDatabase() == null)
			return new EmployeesResponse(new CustomError("Could not connect to database"));

		ResultSet result = accessDatabase().fetchEmployeeRecords(page, items);

		while (result.next()) {
			list.add(parseEmployee(result));
		}

		accessDatabase().closeStatement();

		return new EmployeesResponse(list.size(), list, true);
	}

	public static CountResponse getCount() throws Exception {
		if (accessDatabase() == null)
			return new CountResponse(new CustomError("Could not connect to database"));
		ResultSet result = accessDatabase().fetchEmployeeCount();

		int count = -1;

		if (result.next()) {
			count = result.getInt("employeeCount");
		}

		return new CountResponse(count, true);
	}

	public static AddEmployeeResponse addEmployee(String id, String name, String surname, String gender,
			String birthDate, String joinDate) throws Exception {
		if (accessDatabase() == null)
			return new AddEmployeeResponse(new CustomError("Could not connect to database"));
		DateTimeFormatter receivedFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		EmployeeBean employee = new EmployeeBean(Integer.parseInt(id), name, surname, gender,
				LocalDate.parse(birthDate, receivedFormat), LocalDate.parse(joinDate, receivedFormat));
		String result = accessDatabase().addEmployee(employee);

		if (result == "duplicate") {
			System.out.println("Employee " + id + " already exists in database");
			return new AddEmployeeResponse(new CustomError("Employee already exists in database"));
		}
		return new AddEmployeeResponse(true, name + " " + surname);
	}

	private static EmployeeBean parseEmployee(ResultSet result) throws SQLException {

		int id = result.getInt("id");
		String name = result.getString("name");
		String surname = result.getString("surname");
		String gender = result.getString("gender");
		Date birthDate = result.getDate("birthDate");
		Date joinDate = result.getDate("joinDate");
		

		return new EmployeeBean(id, name, surname, gender, birthDate.toLocalDate(), joinDate.toLocalDate());

	}

	private static Database accessDatabase() {
		try {
			return Database.getInstance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
