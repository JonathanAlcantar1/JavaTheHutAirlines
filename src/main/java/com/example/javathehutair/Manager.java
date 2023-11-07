package com.example.javathehutair;

import java.sql.*;

public class Manager {
    private final String url = "jdbc:mysql://airlinedatabase.ceof6ckatc9m.us-east-2.rds.amazonaws.com:3306/airlineDatabase";
    private final String user = "admin";
    private final String pass = "!Javathehut23";
    private Connection connection;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public boolean loginChecker(String username, String password) throws SQLException {
        String dbUsername, dbPassword;
        connection = DriverManager.getConnection(url, user, pass);

        query = "SELECT * FROM airlineDatabase.managerCredentialTable WHERE (username LIKE ?) AND (password LIKE ?)";

        preparedStatement = connection.prepareStatement(query);
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