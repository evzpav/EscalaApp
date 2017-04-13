package br.com.evandro.persistence.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

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

			String sql = "select * from store order by store_name where company_id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, companyId);
			myRs = myStmt.executeQuery();

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

		} finally {
			CloseConnection.close(myConn, myStmt, myRs);
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
