package com.bigcart.bigcartreportservice.domain;

public class Employee {
	
	// Generate Getters and Setters...
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	private String name;
	private String organization;
	private String designation;
	private int salary;

	public Employee(int id, String name, String oraganization, String designation, int salary) {
		super();
		this.id = id;
		this.name = name;
		this.organization = oraganization;
		this.designation = designation;
		this.salary = salary;
	}
}
