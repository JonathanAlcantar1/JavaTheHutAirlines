/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Model Class to support obtaining and modifying cabin information within flights
 * @author Ricardo Ramos, October 14, 2023
 * @version 1.0
 */
package com.example.javathehutair;

import java.sql.*;
import java.util.List;

public class FlightCabin
{
    /**
     * Local Class Variables
     */
    private int totalReservationPrice;
    private int currIndex =0;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet result = null;
    private dbConnector dbConnector = new dbConnector();

    // These three getters will mostly be used to return current available seats based on their cabins & can be found with a flightID
    /**
     * Method gets the Economy Class seats available within a specific designated flight
     * @param flightID
     * @return int
     * @throws SQLException
     */
    public int getCurrEconomySeat(int flightID) throws SQLException {
        query = "SELECT currEconomySeats FROM airlineDatabase.flightsTable WHERE flightID = ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        try {
            preparedStatement.setInt(1, flightID);
            result = preparedStatement.executeQuery();
            result.next();
        }
        catch(Exception e){
            e.getStackTrace();
        }

        return result.getInt(1);
    }
    /**
     * Method gets the Business Class seats available within a specific designated flight
     * @param flightID
     * @return int
     * @throws SQLException
     */

    public int getCurrBusinessSeat(int flightID) throws SQLException{
        query = "SELECT currBusinessSeats FROM airlineDatabase.flightsTable WHERE flightID = ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,   flightID);

        result = preparedStatement.executeQuery();
        result.next();

        return result.getInt(1);
    }
    /**
     * Method gets the First Class seats available within a specific designated flight
     * @param flightID
     * @return int
     * @throws SQLException
     */

    public int getCurrFirstSeat(int flightID) throws SQLException{

        query = "SELECT currFirstSeats FROM airlineDatabase.flightsTable WHERE flightID = ?";

        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,   flightID);

        result = preparedStatement.executeQuery();
        result.next();

        return result.getInt(1);
    }
    /*
    This will getter will mostly be used by the Manager, which is to be used the total current fliers within a specific flight
    Although we do currently have getCurrAvailableSeats that is based on specific cabin classes, this will mostly be used
    and determined by the static total flight seats(?) of 30 (10 first, 10 biz, 10 econ)
     */
    /**
     * Method calculates the total current active reservations within a designated flight
     * @param flightID
     * @return int
     * @throws SQLException
     */
    public int getTotalPassangers(int flightID) throws SQLException{

        query = "SELECT currTotalSeats FROM airlineDatabase.flightsTable WHERE flightID = ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, flightID);
        result = preparedStatement.executeQuery();
        result.next();
        int currAvailSeats = result.getInt(1);
        return 30 - currAvailSeats;
    }
    /*
    these cabin seat decrementers will be used along with seatDecrementor() to be used to decrease specific cabin seats in their specific flights
    minus 1. As long as the flights cabin seat is more than 0, it will be able to do so.
     */
    /**
     * Method decremends Economy Class seat from a Flight instance given a designated flightID
     * @param flightID
     * @throws SQLException
     */
    private void economySeatDecrementer(int flightID) throws SQLException{
        int currSeatNum = getCurrEconomySeat(flightID);

        if(currSeatNum > 0){
            currSeatNum = currSeatNum - 1;
            query = "UPDATE airlineDatabase.flightsTable SET currEconomySeats = ? WHERE flightID = ?";
            preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, currSeatNum);
            preparedStatement.setInt(2, flightID);
            preparedStatement.executeUpdate();
        }
        else {
            throw new IllegalArgumentException("Number of seats exceeds available seats!");
        }
    }

    /**
     * Method decremends Business Class seat from a Flight instance given a designated flightID
     * @param flightID
     * @throws SQLException
     */
    private void businessSeatDecrementer(int flightID) throws SQLException{
        int currSeatNum = getCurrBusinessSeat(flightID);

        if(currSeatNum > 0){
            currSeatNum = currSeatNum - 1;
            query = "UPDATE airlineDatabase.flightsTable SET currBusinessSeats = ? WHERE flightID = ?";
            preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, currSeatNum);
            preparedStatement.setInt(2, flightID);
            preparedStatement.executeUpdate();
        }

        else {
            throw new IllegalArgumentException("Number of seats exceeds available seats!");
        }
    }

    /**
     * Method decremends First Class seat from a Flight instance given a designated flightID
     * @param flightID
     * @throws SQLException
     */

    private void firstSeatDecrementer(int flightID) throws SQLException{
        int currSeatNum = getCurrFirstSeat(flightID);

        if(currSeatNum > 0){
            currSeatNum = currSeatNum - 1;
            query = "UPDATE airlineDatabase.flightsTable SET currFirstSeats = ? WHERE flightID = ?";
            preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, currSeatNum);
            preparedStatement.setInt(2, flightID);
            preparedStatement.executeUpdate();
        }
        else {
            throw new IllegalArgumentException("Number of seats exceeds available seats!");
        }
    }
    /*
    This seat decrementor will call a specific cabin decrementor depending on the specific classID that is passed through it
    this will be used after a specific flight is pushed into the reservation database.
     */

    /**
     * Method calls specific cabin seat decremtors to be used universally for any flight given FlightID and ClassID
     * @param flightID
     * @param classID
     * @throws SQLException
     */
    public void seatDecrementor(int flightID, int classID) throws SQLException{
        query = "SELECT classStatus FROM airlineDatabase.seatClasses WHERE classID = ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);

        preparedStatement.setInt(1, classID);
        result = preparedStatement.executeQuery();
        result.next();
        String currClass = result.getString(1);

        try {
            if (currClass.equals("First Class")) {
                firstSeatDecrementer(flightID);
            } else if (currClass.equals("Business Class")) {
                businessSeatDecrementer(flightID);
            } else if (currClass.equals("Economy Class")) {
                economySeatDecrementer(flightID);
            }
        }
        catch(Exception e){
            throw new IllegalArgumentException("Unexpected error has occured.");
        }
    }
    /**
     * Method increments Economy Class seat from a Flight instance given a designated flightID
     * @param flightID
     * @throws SQLException
     */
    private void economySeatIncrementor(int flightID) throws SQLException{
        int currSeatNum = getCurrEconomySeat(flightID);

        if(currSeatNum < 10){
            currSeatNum = currSeatNum + 1;

            query = "UPDATE airlineDatabase.flightsTable SET currEconomySeats = ? WHERE flightID = ?";
            preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, currSeatNum);
            preparedStatement.setInt(2, flightID);
            preparedStatement.executeUpdate();
        }
        else{
            throw new IllegalArgumentException("Error! Economy seats are full, possible overbooking occured.");
        }
    }
    /**
     * Method increments Business Class seat from a Flight instance given a designated flightID
     * @param flightID
     * @throws SQLException
     */
    private void businessSeatIncrementor(int flightID) throws SQLException{

        int currSeatNum = getCurrBusinessSeat(flightID);

        if(currSeatNum < 10){
            currSeatNum = currSeatNum + 1;

            query = "UPDATE airlineDatabase.flightsTable SET currBusinessSeats = ? WHERE flightID = ?";
            preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, currSeatNum);
            preparedStatement.setInt(2, flightID);
            preparedStatement.executeUpdate();

        }
        else{
            throw new IllegalArgumentException("Error! Economy seats are full, possible overbooking occured.");
        }
    }

    /**
     * Method increments First Class seat from a Flight instance given a designated flightID
     * @param flightID
     * @throws SQLException
     */
    private void firstSeatIncrementor(int flightID) throws SQLException{

        int currSeatNum = getCurrFirstSeat(flightID);

        if(currSeatNum < 10){
            currSeatNum = currSeatNum + 1;

            query = "UPDATE airlineDatabase.flightsTable SET currFirstSeats = ? WHERE flightID = ?";
            preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, currSeatNum);
            preparedStatement.setInt(2, flightID);
            preparedStatement.executeUpdate();
        }
        else{
            throw new IllegalArgumentException("Error! Economy seats are full, possible overbooking occured.");
        }
    }

    /**
     * Method calls specific seat Incrementors given a FlightID and ClassID
     * @param flightID
     * @throws SQLException
     */
    public void seatIncrementor(int flightID, int classID) throws SQLException{
        query = "SELECT classStatus FROM airlineDatabase.seatClasses WHERE classID = ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);

        preparedStatement.setInt(1, classID);
        result = preparedStatement.executeQuery();
        result.next();
        String currClass = result.getString(1);
        try {
            if (currClass.equals("First Class")) {
                firstSeatIncrementor(flightID);
            } else if (currClass.equals("Business Class")) {
                businessSeatIncrementor(flightID);
            } else if (currClass.equals("Economy Class")) {
                economySeatIncrementor(flightID);
            }
        }
        catch(Exception e){
            throw new IllegalArgumentException("Unexpected error has occured.");
        }
    }
    /*
    this is just used (for now) for obtaining the currAvailSeats for a specific cabin depending on their classID
    which will return the value as an int, to be used in pushReservation()
     */

    /**
     * Method obtains available seats from a specific class given a unique Flight instance
     * @param flightID
     * @param classID
     * @return int
     * @throws SQLException
     */
    public int getAvailSeats(int flightID, int classID)throws SQLException{
        query = "SELECT classStatus FROM airlineDatabase.seatClasses WHERE classID = ?";
        int output = 0;
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, classID);
        result = preparedStatement.executeQuery();
        result.next();
        String currClass = result.getString(1);
        try {
            if (currClass.equals("First Class")) {
                output = getCurrFirstSeat(flightID);
            } else if (currClass.equals("Business Class")) {
                output = getCurrBusinessSeat(flightID);
            } else if (currClass.equals("Economy Class")) {
                output = getCurrEconomySeat(flightID);
            }
        }
        catch(Exception e){
            throw new IllegalArgumentException("Unexpected error has occured.");
        }
        return output;
    }
    /*
    This is to be used for the Manager, as described in Sprint 2 user story,
    this will return the total reservation price given a specific list of reservations,
    we can pass through here getReservations() from the Reservation class to obtain the full price and
    can furthermore be displayed for checkout price
     */
    /**
     * Method calculates iteratively the total reservation price given an instance List.
     * @param reservations
     * @throws SQLException
     */
    public int totalReservationPrice(List<Reservation> reservations) throws SQLException{
        // The idea of getting the totalReservationPrice of the current list of reservations is
        // to essentially use this function to pass
        query = "SELECT seatPrice FROM airlineDatabase.seatClasses WHERE classID = ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        Reservation currRes;
        try{

            while(currIndex < reservations.size()){
                currRes = reservations.get(currIndex);
                preparedStatement.setInt(1, currRes.getClassID());
                result = preparedStatement.executeQuery();
                result.next();
                totalReservationPrice += result.getInt(1);
                currIndex++;
            }

        }
        catch(Exception e){
            throw new IllegalArgumentException("No reservations have been added!");
        }

        return totalReservationPrice;
    }
}
