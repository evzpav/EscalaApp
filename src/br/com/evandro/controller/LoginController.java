package br.com.evandro.controller;

import br.com.evandro.exceptions.NotFoundException;
import br.com.evandro.model.Store;
import br.com.evandro.model.User;
import br.com.evandro.persistence.dao.LoginDAO;


import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class LoginController {

    private LoginDAO loginDAO;
    private StoreController storeController;

    public LoginController(DataSource dataSource) {
        loginDAO = new LoginDAO(dataSource);
        storeController = new StoreController(dataSource);
    }


    public User doLogin(User newUser) throws NotFoundException, SQLException {
        User user = loginDAO.doLogin(newUser);
        List<Store> stores = storeController.listStoresByUser(user);
        user.setStores(stores);
        return user;

    }
}

