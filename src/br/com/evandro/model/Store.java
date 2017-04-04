package br.com.evandro.model;

public class Store {

    private Integer storeId;
    private String storeName;
    private String storeAddress;
    private Company company;

    public Store(String storeName, String storeAddress) {
        super();
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }


    public Store() {

    }


    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
