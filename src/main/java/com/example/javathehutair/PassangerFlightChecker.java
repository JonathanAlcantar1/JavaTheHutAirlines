package com.example.javathehutair;

import java.sql.*;

public class PassangerFlightChecker {


    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private dbConnector dbConnector = new dbConnector();

    /*
    TODO: Display all passengers within a specific flight
    Parameters: (int flightID)
     */

    private ResultSet getPassengersInFlight(int flightID) throws SQLException{


        query = "SELECT * FROM airlineDatabase.reservationTable WHERE flightID = ?";

        preparedStatement = dbConnector.getConnection().prepareStatement(query);
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
