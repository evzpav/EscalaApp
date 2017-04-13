package br.com.evandro.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class CloseConnection {

    public static void close(Connection myConn, Statement myStmt, ResultSet myRs) {

        try {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();

            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }
    public static void close(Connection myConn, Statement myStmt) {

        try {

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();

            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }
}
