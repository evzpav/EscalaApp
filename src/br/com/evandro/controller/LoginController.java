package br.com.evandro.controller;

import br.com.evandro.exceptions.NotFoundException;
import br.com.evandro.model.User;
import br.com.evandro.persistence.dao.LoginDAO;

import javax.sql.DataSource;

public class LoginController {

    private LoginDAO loginDAO;

    public LoginController(DataSource dataSource) {
        loginDAO = new LoginDAO(dataSource);
    }

    public User doLogin(User user) throws NotFoundException {
        return loginDAO.doLogin(user);

    }
}

