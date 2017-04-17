package br.com.evandro.persistence.dao;

import br.com.evandro.exceptions.NotFoundException;
import br.com.evandro.model.Company;
import br.com.evandro.model.User;
import br.com.evandro.persistence.CloseConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private DataSource dataSource;

    public LoginDAO(DataSource theDataSource) {
        dataSource = theDataSource;

    }

    public User doLogin(User user) throws NotFoundException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "select * from user_login " +
                    "join company on user_login.company_id = company.company_id " +
                    "where email = ? and password = ?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1, user.getEmail());
            myStmt.setString(2, user.getPassword());

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                int companyId = myRs.getInt("company_id");
                String companyName = myRs.getString("company_name");
                int accessId = myRs.getInt("access_id");
                Company company = new Company();
                company.setCompanyId(companyId);
                company.setCompanyName(companyName);
                user.setCompany(company);
                user.setAccessId(accessId);

            } else {

                throw new NotFoundException("Email ou senha inválidos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseConnection.close(myConn, myStmt, myRs);
        }
        return user;

    }
}
