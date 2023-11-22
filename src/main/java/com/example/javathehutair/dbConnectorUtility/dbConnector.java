package com.example.javathehutair.dbConnectorUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnector {
    /**
     * Local Class Variables
     */
    protected String url = "jdbc:mysql://airlinedatabase.ceof6ckatc9m.us-east-2.rds.amazonaws.com:3306/airlineDatabase";
    protected String user = "admin";
    protected String pass = "!Javathehut23";
    /**
     * Method connects to hosted database via URL, USER, PASSWORD
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
