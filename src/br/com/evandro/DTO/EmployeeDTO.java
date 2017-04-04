package br.com.evandro.DTO;

import br.com.evandro.model.Store;

public class EmployeeDTO {
    private Integer employeeId;
    private String employeeName;
    private String workFunction;
    private Store store;

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


}

