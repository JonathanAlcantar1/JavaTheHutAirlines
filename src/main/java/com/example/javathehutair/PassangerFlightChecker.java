/**
 * PassengerFlightChecker
 * November 15, 2023
 * @author Ricardo Ramos
 *
 * Most algorithms within this class uses SQL JDBC API to obtain information from the MySQL database to pass
 *      into a ResultSet which is furthermore used within displaying into a scene for manager search.
 * @version 1.0
 */
package com.example.javathehutair;

import java.io.IOException;
import java.sql.*;

public class PassangerFlightChecker {

    /**
     * Local Variables
     */
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private dbConnector dbConnector = new dbConnector();

    /**
     * Method obtains and returns all columns from the database given a specific flightID, with data relating to
     *      passenger information within a specific instance of a flight.
     * @param flightID Action event input parameter
     * @throws SQLException SQLException
     * @throws IOException IOException
     */
    public ResultSet getPassengersInFlight(int flightID) throws SQLException{
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
