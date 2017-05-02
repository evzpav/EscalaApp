package br.com.evandro.persistence.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.evandro.exceptions.NotFoundException;
import br.com.evandro.model.Store;
import br.com.evandro.persistence.CloseConnection;

public class StoreDAO {
    private DataSource dataSource;


    public StoreDAO(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Store> listStores(int companyId) throws SQLException {
        List<Store> listOfStores = new ArrayList<>();
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "select * from store " +
                    "where company_id = ?  " +
                    "order by store_name";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, companyId);
            myRs = myStmt.executeQuery();

            listOfStores = getStores(listOfStores, myRs);

        } finally {
            CloseConnection.close(myConn, myStmt, myRs);
        }

        return listOfStores;
    }

    public List<Store> listStoresByUser(int userId) throws NotFoundException {
        List<Store> listOfStores = new ArrayList<>();
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "select store.store_id, store_name, store_address from user_login " +
                    "join user_store on user_store.user_id = user_login.user_id " +
                    "join store on user_store.store_id = store.store_id " +
                    "where user_login.user_id = ?" +
                    "order by store_name";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, userId);
            myRs = myStmt.executeQuery();

            listOfStores = getStores(listOfStores, myRs);

        } catch (SQLException e) {
            throw new NotFoundException("Nenhuma loja encontrada para esse usuário.");
        } finally {
            CloseConnection.close(myConn, myStmt, myRs);
        }

        return listOfStores;
    }

    private List<Store> getStores(List<Store> listOfStores, ResultSet myRs) throws SQLException {
        while (myRs.next()) {

            int storeId = myRs.getInt("store_id");
            String storeName = myRs.getString("store_name");
            String address = myRs.getString("store_address");

            Store store = new Store();
            store.setStoreId(storeId);
            store.setStoreName(storeName);
            store.setStoreAddress(address);
            listOfStores.add(store);
        }
        return listOfStores;
    }


    public Store getStoreById(int storeId) {
        Store store = new Store();
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = dataSource.getConnection();

            String sql = "select * from store where store_id = ?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, storeId);

            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                String storeName = myRs.getString("store_name");
                String address = myRs.getString("store_address");
                store.setStoreName(storeName);
                store.setStoreAddress(address);
                store.setStoreId(storeId);

            } else {
                throw new Exception("Could not find employee id: " + storeId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection.close(myConn, myStmt, myRs);
        }
        return store;

    }


}
