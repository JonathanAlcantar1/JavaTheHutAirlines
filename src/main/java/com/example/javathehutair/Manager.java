/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Model Class to support Manager
 * @author Ricardo Ramos, October 14, 2023
 * @version 1.0
 */
package com.example.javathehutair;

import java.sql.*;

public class Manager {
    /**
     * Local class variables
     */
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private dbConnector dbConnector = new dbConnector();


    /**
     * Method is used to verify login credentials
     * @param username
     * @param password
     * @return boolean
     * @throws SQLException
     */
    public boolean loginChecker(String username, String password) throws SQLException {
        String dbUsername, dbPassword;
        query = "SELECT * FROM airlineDatabase.managerCredentialTable WHERE (username LIKE ?) AND (password LIKE ?)";

        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            dbUsername = resultSet.getString("username");
            dbPassword = resultSet.getString("password");
            if(!dbUsername.equals(username) && !dbPassword.equals(password)){
                return false;
            }
        }
        else{
            return false;
        }
        return true;
    }



}