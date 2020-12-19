package com;

public class EmployeeBean {
	
	private String id, name, surname, gender, birthDate, joinDate;

	/* if an argument constructor is included, a default constructor MUST ALSO be included for this
	* class to be JSON serializable
	*/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname.trim();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	
	
	
}

