package com.db;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.Employee;
import com.EmployeeBean;
import com.IDListBean;
import com.communication.*;

public class DBOperations {

	public static EmployeesResponse getEmployees(int page, int items) throws Exception {
		if (accessDatabase() == null)
			return new EmployeesResponse(new VolException("Could not connect to database"));
		ArrayList<Employee> list = new ArrayList<Employee>();
		ResultSet result = accessDatabase().fetchEmployeeRecords(page, items);

		while (result.next()) {
			list.add(parseEmployee(result));
		}

		accessDatabase().closeStatement();

		return new EmployeesResponse(list.size(), list, true);
	}

	public static EmployeesResponse getEmployees(String term, int items) throws Exception {
		if (accessDatabase() == null)
			return new EmployeesResponse(new VolException("Could not connect to database"));
		ArrayList<Employee> list = new ArrayList<Employee>();
		ResultSet result = accessDatabase().fetchEmployeeRecords(term, items);
		while (result.next()) {
			list.add(parseEmployee(result));
		}

		accessDatabase().closeStatement();

		return new EmployeesResponse(list.size(), list, true);
	}
	
	public static CountResponse getCount() throws Exception {
		if (accessDatabase() == null)
			return new CountResponse(new VolException("Could not connect to database"));
		ResultSet result = accessDatabase().fetchEmployeeCount();

		int count = -1;

		if (result.next()) {
			count = result.getInt("employeeCount");
		}

		return new CountResponse(count, true);
	}

	public static AddEmployeeResponse addEmployee(EmployeeBean bean) throws Exception {
		if (accessDatabase() == null)
			return new AddEmployeeResponse(new VolException("Could not connect to database"));
		DateTimeFormatter receivedFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Employee employee = new Employee(Integer.parseInt(bean.getId()), bean.getName(),
				bean.getSurname(), bean.getGender(), LocalDate.parse(bean.getBirthDate(), receivedFormat),
				LocalDate.parse(bean.getJoinDate(), receivedFormat));
		
		String result = accessDatabase().addEmployee(employee);

		if (result == "duplicate") {
			System.out.println("Employee " + employee.getId() + " already exists in database");
			return new AddEmployeeResponse(new VolException("Employee already exists in database"));
		}
		return new AddEmployeeResponse(true, employee.getName() + " " + employee.getSurname());
	}
	
	public static DeleteEmployeesResponse deleteEmployees(IDListBean bean) throws Exception {
		if (accessDatabase() == null)
			return new DeleteEmployeesResponse(new VolException("Could not connect to database"));
		
		int deletedRows = accessDatabase().deleteEmployees(bean.getIdList());
		
		return new DeleteEmployeesResponse(true, deletedRows);
	}

	private static Employee parseEmployee(ResultSet result) throws SQLException {

		int id = result.getInt("id");
		String name = result.getString("name");
		String surname = result.getString("surname");
		String gender = result.getString("gender");
		Date birthDate = result.getDate("birthDate");
		Date joinDate = result.getDate("joinDate");
		

		return new Employee(id, name, surname, gender, birthDate.toLocalDate(), joinDate.toLocalDate());

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
