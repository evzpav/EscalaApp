package br.com.evandro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.evandro.model.Employee;
import br.com.evandro.model.Store;
import br.com.evandro.util.CloseConnection;

public class EmployeeDAO {
	private DataSource dataSource;

	public EmployeeDAO(DataSource theDataSource) {
		dataSource = theDataSource;
	}


	public List<Employee> listEmployees() throws SQLException {
		List<Employee> listOfEmployees = new ArrayList<>();
		Employee employee = null;
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();

			String sql = "select * from employee order by employee_name";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {

				int employeeId = myRs.getInt("employee_id");
				String employeeName = myRs.getString("employee_name");
				String workFunction = myRs.getString("work_function");
				boolean status = myRs.getBoolean("status");
				int storeId = myRs.getInt("store_id");

				Store store = new Store();
				store.setStoreId(storeId);

				employee = new Employee(employeeName, workFunction);
				employee.setEmployeeId(employeeId);
				employee.setStore(store);
				employee.setActive(status);
				listOfEmployees.add(employee);
			}

		} finally {
			CloseConnection.close(myConn, myStmt, myRs);
		}

		return listOfEmployees;
	}

	public List<Employee> listEmployeesActiveOnly() throws SQLException {
		List<Employee> listOfEmployees = new ArrayList<>();
		Employee employee = null;
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();

			String sql = "select * from employee where active = true order by employee_name";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {

				int employeeId = myRs.getInt("employee_id");
				String employeeName = myRs.getString("employee_name");
				String workFunction = myRs.getString("work_function");
				boolean status = myRs.getBoolean("active");
				int storeId = myRs.getInt("store_id");

				Store store = new Store();
				store.setStoreId(storeId);

				employee = new Employee(employeeName, workFunction);
				employee.setEmployeeId(employeeId);
				employee.setStore(store);
				employee.setActive(status);
				listOfEmployees.add(employee);
			}

		} finally {
			CloseConnection.close(myConn, myStmt, myRs);
		}

		return listOfEmployees;
	}

	public List<Employee> listActiveEmployeesByStore(int storeId) throws SQLException {
		List<Employee> listOfEmployees = new ArrayList<>();
		Employee employee = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();

			String sql = "select * from employee where active = true and store_id = ? order by employee_name";

			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, storeId);
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				int employeeId = myRs.getInt("employee_id");
				String employeeName = myRs.getString("employee_name");
				String workFunction = myRs.getString("work_function");
				boolean status = myRs.getBoolean("active");
				employee = new Employee(employeeName, workFunction);
				employee.setEmployeeId(employeeId);
				employee.setActive(status);
				listOfEmployees.add(employee);
			}

		} finally {
			CloseConnection.close(myConn, myStmt, myRs);
		}

		return listOfEmployees;
	}

	public int addEmployee(Employee employee) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		int employeeId;
		try {
			myConn = dataSource.getConnection();

			String sql = "insert into employee (employee_name, work_function, store_id) values (?,?,?)";

			myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			myStmt.setString(1, employee.getEmployeeName());
			myStmt.setString(2, employee.getWorkFunction());
			//myStmt.setInt(3, employee.getStore().getStoreId());
			myStmt.setInt(3, 1); // storeId chumbado
			myStmt.execute();

			try (ResultSet keys = myStmt.getGeneratedKeys()) {
				keys.next();
				employeeId = keys.getInt("employee_id");
				employee.setEmployeeId(employeeId);
			}
		} finally {
			CloseConnection.close(myConn, myStmt, null);
		}
		return employeeId;
	}

	public void deleteEmployeeById(int employeeId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {

			myConn = dataSource.getConnection();

			String sql = "delete from employee where employee_id=(?)";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, employeeId);

			myStmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			CloseConnection.close(myConn, myStmt, null);
		}

	}

	public Employee getEmployeeById(int employeeId) {
		Employee employee = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = dataSource.getConnection();

			String sql = "select * from employee where employee_id = ?";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, employeeId);

			myRs = myStmt.executeQuery();


			if (myRs.next()) {
				String employeeName = myRs.getString("employee_name");
				String workFunction = myRs.getString("work_function");
				int storeId = myRs.getInt("store_id");

				Store store = new Store();
				store.setStoreId(storeId);

				employee = new Employee(employeeName, workFunction);
				employee.setEmployeeId(employeeId);
				employee.setStore(store);


			} else {
				throw new Exception("Could not find employee id: " + employeeId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseConnection.close(myConn, myStmt, myRs);
		}
		return employee;

	}


	public void updateEmployee(Employee employee) {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = dataSource.getConnection();

			String sql = "update employee set employee_name = ?, work_function = ?, store_id = ? where employee_id = ?";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setString(1, employee.getEmployeeName());
			myStmt.setString(2, employee.getWorkFunction());
			//myStmt.setInt(3,employee.getStore().getStoreId());
			myStmt.setInt(3,1); // storeId chumbado
			myStmt.setInt(4, employee.getEmployeeId());
			myStmt.execute();


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseConnection.close(myConn, myStmt);
		}

	}

	public void changeActiveStatus(int employeeId, boolean isActive) {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = dataSource.getConnection();

			String sql = "update employee set active = ? where employee_id=?";

			myStmt = myConn.prepareStatement(sql);


			myStmt.setBoolean(1, isActive);
			myStmt.setInt(2, employeeId);
			myStmt.execute();


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseConnection.close(myConn, myStmt);
		}


	}
}
