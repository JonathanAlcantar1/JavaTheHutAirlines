package com.example.javathehutair;

import java.sql.*;

public class PassangerFlightChecker {
    private final String url = "jdbc:mysql://airlinedatabase.ceof6ckatc9m.us-east-2.rds.amazonaws.com:3306/airlineDatabase";
    private final String user = "admin";
    private final String pass = "!Javathehut23";
    private Connection connection;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    /*
    TODO: Display all passengers within a specific flight
    Parameters: (int flightID)
     */

    private ResultSet getPassengersInFlight(int flightID) throws SQLException{
        connection = DriverManager.getConnection(url, user, pass);

        query = "SELECT * FROM airlineDatabase.reservationTable WHERE flightID = ?";

        preparedStatement = connection.prepareStatement(query);
        try{
            preparedStatement.setInt(1, flightID);
            resultSet = preparedStatement.executeQuery();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return resultSet;
    }
}
