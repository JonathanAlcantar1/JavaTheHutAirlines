package com.example.javathehutair;

import java.sql.*;

public class Manager {
    private final String url = "jdbc:mysql://airlinedatabase.ceof6ckatc9m.us-east-2.rds.amazonaws.com:3306/airlineDatabase";
    private final String username = "admin";
    private final String password = "!Javathehut23";
    private Connection connection;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public boolean loginChecker(String username, String password) throws SQLException {
        String dbUsername, dbPassword;
        connection = DriverManager.getConnection(url, username, password);

        query = "SELECT * FROM airlineDatabase.managerCredentialTable WHERE username LIKE ? & password LIKE ?";

        preparedStatement = connection.prepareStatement(query);
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