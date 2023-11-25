package com.example.javathehutair.flight;

import com.example.javathehutair.dbConnectorUtility.dbConnector;
import java.sql.*;
import java.time.LocalDate;
import java.sql.Date;
/** FlightSearcher
 * The purpose of this class is to access the database flights table and provide the specific flights in a resultset
 * when provided different parameters
 * @author Teo Dominguez
 * @version 1.0
 * November 21, 2023
 */
public class FlightSearcher
{

    /**
     * Local Variables
     */
    private com.example.javathehutair.dbConnectorUtility.dbConnector dbConnector = new dbConnector();

    /**
     * searches for all flights that contain the given parameters
     * @param departure String
     * @param arrival String
     * @param numTickets int
     * @return resultSet
     */
    public ResultSet searchAllFlights(String departure, String arrival, int numTickets) {
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currTotalSeats >= ?)";
        return databaseQuery(sql, departure, arrival, numTickets);
    }

    /**
     * Searches for all flights that contain the given parameters
     * @param departure String
     * @param arrival String
     * @param numTickets int
     * @param departureDay LocalDate
     * @param arrivalDate LocalDate
     * @return resultSet
     */
    public ResultSet searchAllFlights(String departure, String arrival, int numTickets, LocalDate departureDay, LocalDate arrivalDate) {
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currTotalSeats >= ?) AND (departureDate = ?) AND (arrivalDate = ?)";
        return databaseQuery(sql, departure, arrival, numTickets, departureDay, arrivalDate);
    }

    /**
     * Searches for a specific flight given a flightID String
     * @param flightID String
     * @return
     */
    public ResultSet searchSpecificFlight(String flightID){
        String sql = "SELECT * FROM flightsTable WHERE (flightId = ?)";
        return databaseQuery(sql, flightID);
    }

    /**
     * Searches for First Class flights given departure location, arrival location, and number of tickets
     * @param departure String
     * @param arrival String
     * @param numTickets int
     * @return resultSet
     */
    public ResultSet searchFirstClassFlights(String departure, String arrival, int numTickets){
        //String sql = "SELECT * FROM flightsTable WHERE departureLocation=? AND arrivalLocation=? AND (currFirstSeats >= ?)";
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currFirstSeats>=?)";
        return databaseQuery(sql, departure, arrival, numTickets);
    }

    /**
     * Searches for Business Class flights given departure location, arrival location, and number of tickets
     * @param departure String
     * @param arrival String
     * @param numTickets int
     * @return resultSet
     */
    public ResultSet searchBusinessClassFlights(String departure, String arrival, int numTickets){
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currBusinessSeats >= ?)";
        return databaseQuery(sql, departure, arrival, numTickets);
    }

    /**
     * Searches for Economy Class flights given departure location, arrival location, and number of tickets
     * @param departure String
     * @param arrival String
     * @param numTickets int
     * @return resultSet
     */
    public ResultSet searchEconomyClassFlights(String departure, String arrival, int numTickets){
        String sql = "SELECT * FROM flightsTable WHERE (departureLocation LIKE ?) AND (arrivalLocation LIKE ?) AND (currEconomySeats >= ?)";
        return databaseQuery(sql, departure, arrival, numTickets);
    }

    /**
     * Searches for a specific flight given a flightID int
     * @param flightID int
     * @return resultSet
     */
    public ResultSet searchSpecificFlight(int flightID)
    {
        String sql = "SELECT * FROM flightsTable WHERE flightID = ?";
        return databaseQuery(sql, flightID);
    }

    /**
     * does the repeated work of accessing the database and passing the sql through a prepared statement. Returns a resultSet
     * @param sql String
     * @param departure String
     * @param arrival String
     * @param numTickets int
     * @return resultSet
     */
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

    /**
     * does the repeated work of accessing the database and passing the sql through a prepared statement. Returns a resultSet
     * @param sql String
     * @param departure String
     * @param arrival String
     * @param numTickets int
     * @param departureDate LocalDate
     * @param arrivalDate LocalDate
     * @return resultSet
     */
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

    /**
     * does the repeated work of accessing the database and passing the sql through a prepared statement. Returns a resultSet
     * @param sql String
     * @param flightID int
     * @return resultSet
     */
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

    /**
     * does the repeated work of accessing the database and passing the sql through a prepared statement. Returns a resultSet
     * @param sql String
     * @param flightID String
     * @return resultSet
     */
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
