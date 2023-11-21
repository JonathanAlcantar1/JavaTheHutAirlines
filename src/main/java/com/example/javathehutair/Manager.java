/**
 * Manager Class
 * November 7, 2023
 * @author Ricardo Ramos
 *
 * The essential sole method within this class is to verify login-credentials from those found within the ManagerCredentialTable
 *      within the SQL database. The core function of loginChecker being to check for similar passwords that are connected
 *      to usernames via input paramerters using parameratized queries to prevent SQL Injection and possible data breaches
 *      through text inputs.
 *
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
        preparedStatement.setString(1, "%" + username + "%");
        preparedStatement.setString(2, "%" + password + "%");
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