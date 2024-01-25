package com.example.javathehutair;

import java.sql.*;

public class Manager {
    private final String url = "URL";
    private final String username = "USER";
    private final String password = "PASS";
    private Connection connection;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

        public boolean loginChecker(String username, String password) throws SQLException {
            String dbUsername, dbPassword;
            connection = DriverManager.getConnection(url, username, password);

            query = "SELECT * FROM airlineDatabase.adminTable WHERE username LIKE ? AND password LIKE ?";

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
