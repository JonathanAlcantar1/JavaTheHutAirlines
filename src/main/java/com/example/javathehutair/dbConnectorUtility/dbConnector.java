package com.example.javathehutair.dbConnectorUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnector {
    /**
     * Local Class Variables
     */
    protected String url = "URL";
    protected String user = "USER";
    protected String pass = "PASS";
    /**
     * Method connects to hosted database via URL, USER, PASSWORD
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
