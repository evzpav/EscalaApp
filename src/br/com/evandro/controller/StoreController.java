package br.com.evandro.controller;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import br.com.evandro.dao.StoreDAO;

import br.com.evandro.model.*;

public class StoreController {

    private StoreDAO storeDAO;

    public StoreController(DataSource dataSource) {
        storeDAO = new StoreDAO(dataSource);
    }

    public List<Store> listStores(int companyId) throws SQLException {
        return storeDAO.listStores(companyId);

    }

    public Store getStoreById(int storeId) {
        Store store = storeDAO.getStoreById(storeId);
        return store;
    }


}
