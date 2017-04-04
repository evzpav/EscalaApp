package br.com.evandro.model;

import java.util.List;

public class Employee {

	private Integer employeeId;
	private String employeeName;
	private String workFunction;
	private Store store;
	private List<WeekTimePattern> listOfWeekTimePattern;
	private boolean active;


	public Employee(String employeeName, String workFunction) {
		super();
		this.employeeName = employeeName;
		this.workFunction = workFunction;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getWorkFunction() {
		return workFunction;
	}

	public void setWorkFunction(String workFunction) {
		this.workFunction = workFunction;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}


	public List<WeekTimePattern> getListOfWeekTimePattern() {
		return listOfWeekTimePattern;
	}

	public void setListOfWeekTimePattern(List<WeekTimePattern> listOfWeekTimePattern) {
		this.listOfWeekTimePattern = listOfWeekTimePattern;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
