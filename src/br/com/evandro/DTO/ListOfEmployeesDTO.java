package br.com.evandro.DTO;

import br.com.evandro.model.Employee;
import br.com.evandro.model.Store;

import java.util.List;

public class ListOfEmployeesDTO {

	private List<Employee> listOfEmployees;
	private Store store;

	public ListOfEmployeesDTO(List<Employee> listOfEmployees, Store store) {
		super();
		this.listOfEmployees = listOfEmployees;
		this.store = store;
	}

}
