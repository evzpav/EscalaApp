package br.com.evandro.model;

import java.util.List;

public class Company {

    private int companyId;
    private String companyName;
    private List<Store> listOfStores;

    public Company() {
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Store> getListOfStores() {
        return listOfStores;
    }

    public void setListOfStores(List<Store> listOfStores) {
        this.listOfStores = listOfStores;
    }
}



