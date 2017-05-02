package br.com.evandro.persistence.DTO;


import br.com.evandro.model.Company;

public class UserDTO{
    private String email;
    private String password;
    private Company company;
    private int accessId;

    public UserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getAccessId() {
        return accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }
}
