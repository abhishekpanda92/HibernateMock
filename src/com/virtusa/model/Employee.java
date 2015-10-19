package com.virtusa.model;

@CreateTable("EmployeeTable1")
public class Employee {
	
	@PrimaryKey
	private int employeeCode;
	private String employeeName;
	private String password;
	private double employeeSalary;
	private int departmentId;

	public Employee() {

	}

	/**
	 * 
	 * @param employeeCode
	 * @param password
	 * @param employeeName
	 * @param employeeSalary
	 * @param departmentId
	 */
	public Employee(int employeeCode, String password, String employeeName,
			double employeeSalary, int departmentId) {
		// super();
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.password = password;
		this.employeeSalary = employeeSalary;
		this.departmentId = departmentId;
	}

	/**
	 * 
	 * @return
	 */
	public int getDepartmentId() {
		return departmentId;
	}

	/**
	 * 
	 * @param departmentId
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * 
	 * @return
	 */
	public int getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * 
	 * @param employeeCode
	 */
	public void setEmployeeCode(int employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * 
	 * @param employeeName
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return
	 */
	public double getEmployeeSalary() {
		return employeeSalary;
	}

	/**
	 * 
	 * @param employeeSalary
	 */
	public void setEmployeeSalary(double employeeSalary) {
		this.employeeSalary = employeeSalary;
	}

	/**
	 * returns the employee object field values
	 */
	@Override
	public String toString() {
		return "Employee [employeeCode=" + employeeCode + ", employeeName="
				+ employeeName + ", password=" + password + ", employeeSalary="
				+ employeeSalary + ", departmendId=" + departmentId + "]";
	}
}
