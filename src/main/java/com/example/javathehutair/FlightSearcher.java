package com.example.javathehutair;

import java.sql.*;
import java.time.LocalDate;
import java.sql.Date;
public class FlightSearcher
{
    private dbConnector dbConnector = new dbConnector();
    //Returning a ResultSet of any flight with open seats given a departure and arrival location and number of tickets
    public ResultSet searchAllFlights(String departure, String arrival, int numTickets) {
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currTotalSeats >= ?)";
        return databaseQuery(sql, departure, arrival, numTickets);
    }
    //Searching for all flights with a specific arrival and departure date
    public ResultSet searchAllFlights(String departure, String arrival, int numTickets, LocalDate departureDay, LocalDate arrivalDate) {
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currTotalSeats >= ?) AND (departureDate = ?) AND (arrivalDate = ?)";
        return databaseQuery(sql, departure, arrival, numTickets, departureDay, arrivalDate);
    }
    //Searches for a specific flight info given a flightID
    public ResultSet searchSpecificFlight(String flightID){
        String sql = "SELECT * FROM flightsTable WHERE (flightId = ?)";
        return databaseQuery(sql, flightID);
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

    // Searches for a specific flight using the flightID
    public ResultSet searchSpecificFlight(int flightID)
    {
        String sql = "SELECT * FROM flightsTable WHERE flightID = ?";
        return databaseQuery(sql, flightID);
    }
    //does the repeated work of accessing the database and passing the sql through a prepared statement. Returns a resultSet
    public ResultSet databaseQuery(String sql, String departure, String arrival, int numTickets){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //open a database connection
            //sql statement to execute with prepared statement
            preparedStatement = dbConnector.getConnection().prepareStatement(sql);
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
    public ResultSet databaseQuery(String sql, String departure, String arrival, int numTickets, LocalDate departureDate, LocalDate arrivalDate){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //open a database connection
            //sql statement to execute with prepared statement
            preparedStatement = dbConnector.getConnection().prepareStatement(sql);
            //passing parameters into the sql statement
            preparedStatement.setString(1, "%" + departure + "%");
            preparedStatement.setString(2, "%" + arrival + "%");
            preparedStatement.setInt(3, numTickets);
            preparedStatement.setDate(4, Date.valueOf(departureDate));
            preparedStatement.setDate(5, Date.valueOf(arrivalDate));
            //executing
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet databaseQuery(String sql, int flightID){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //open a database connection
            //sql statement to execute with prepared statement
            preparedStatement = dbConnector.getConnection().prepareStatement(sql);
            //passing parameters into the sql statement
            preparedStatement.setInt(1, flightID);
            //executing
            resultSet = preparedStatement.executeQuery();
        }
        catch (Exception e)
        {
            System.out.println("Error is here");
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet databaseQuery(String sql, String flightID){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //open a database connection
            //sql statement to execute with prepared statement
            preparedStatement = dbConnector.getConnection().prepareStatement(sql);
            //passing parameters into the sql statement
            preparedStatement.setString(1, flightID);
            //executing
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
