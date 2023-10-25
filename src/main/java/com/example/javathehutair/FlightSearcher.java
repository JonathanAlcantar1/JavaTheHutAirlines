package com.example.javathehutair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FlightSearcher
{
    //Returning a ResultSet of any flight with open seats given a departure and arrival location and number of tickets
    public ResultSet searchAllFlights(String departure, String arrival, int numTickets) {
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currTotalSeats >= ?)";
        return databaseQuery(sql, departure, arrival, numTickets);
    }
    //searching for flights with the specified amount of first class seats given a departure and arrival location
    public ResultSet searchFirstClassFlights(String departure, String arrival, int numTickets){
        //String sql = "SELECT * FROM flightsTable WHERE departureLocation=? AND arrivalLocation=? AND (currFirstSeats >= ?)";
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currFirstSeats>=?)";
        return databaseQuery(sql, departure, arrival, numTickets);
    }
    //searching for flights with the specified amount of business class seats given a departure and arrival location
    public ResultSet searchBusinessClassFlights(String departure, String arrival, int numTickets){
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currBusinessSeats >= ?)";
        return databaseQuery(sql, departure, arrival, numTickets);
    }
    //searching for flights with the specified amount of economy class seats given a departure and arrival location
    public ResultSet searchEconomyClassFlights(String departure, String arrival, int numTickets){
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currEconomySeats >= ?)";
        return databaseQuery(sql, departure, arrival, numTickets);
    }
    //does the repeated work of accessing the database and passing the sql through a prepared statement. Returns a resultSet
    public ResultSet databaseQuery(String sql, String departure, String arrival, int numTickets){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //open a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://airlinedatabase.ceof6ckatc9m.us-east-2.rds.amazonaws.com:3306/airlineDatabase", "admin", "!Javathehut23");
            //sql statement to execute with prepared statement
            preparedStatement = connection.prepareStatement(sql);
            //passing parameters into the sql statement
            preparedStatement.setString(1, "%" + departure + "%");
            preparedStatement.setString(2, "%" + arrival + "%");
            preparedStatement.setInt(3, numTickets);
            //executing
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
