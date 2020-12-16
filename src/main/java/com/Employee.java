package com;

import java.time.LocalDate;

public class Employee {
	
	private final int id;
	private final String name, surname, gender;
	private final LocalDate birthDate, joinDate;
	
	public Employee(int id, String name, String surname, String gender, LocalDate birthDate, LocalDate joinDate) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.joinDate = joinDate;
	}


	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getGender() {
		return gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}
}
