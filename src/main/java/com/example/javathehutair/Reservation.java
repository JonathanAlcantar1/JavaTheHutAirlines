/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Model Class to support Manager
 * @author Ricardo Ramos, October 14, 2023
 * @version 1.0
 */
package com.example.javathehutair;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation
{
    /**
     * Local class variables
     */
    private int flightID, classID;
    private int currIndex = 0; // variable used to reference current index that will be initially treated as 0, and reset to 0 after flushReservations()
    private String firstName, lastName, cellNum, address, email;
    private LocalDate dob;
    private String query;
    private List<Reservation> reservations = new ArrayList<>();
    private FlightCabin currSeat = new FlightCabin();

    private PreparedStatement preparedStatement;
    private ResultSet result;
    private dbConnector dbConnector = new dbConnector();

    /**
     * Method Set Flight ID
     * @param flightID
     */
    public void setFlightID(int flightID){
        this.flightID = flightID;
    }
    /**
     * Method Get Flight ID
     * @return int
     */
    public int getFlightID(){return flightID;}
    public void setClassID(int classID){
        this.classID = classID;
    }
    /**
     * Method Get Class ID
     * @return int
     */
    public int getClassID(){
        return classID;
    }
    /**
     * Method Set First Name
     * @param firstName
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    /**
     * Method Get First Name
     * @return String
     */
    public String getFirstName(){
        return firstName;
    }
    /**
     * Method Set Last Name
     * @param lastName
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    /**
     * Method Get Last Name
     * @return String
     */
    public String getLastName(){
        return lastName;
    }
    /**
     * Method Set Date-Of-Birth
     * @param dob
     */
    public void setDob(LocalDate dob){
        this.dob = dob;
    }
    /**
     * Method Get DOB
     * @return Date
     */
    public Date getDob(){
        return Date.valueOf(dob);
    }
    /**
     * Method Set Cell Number
     * @param cellNum
     */
    public void setCellNum(String cellNum){
        this.cellNum = cellNum;
    }
    /**
     * Method Get Cell Number
     * @return String
     */
    public String getCellNum(){
        return cellNum;
    }
    /**
     * Method Set Address
     * @param address
     */
    public void setAddress(String address){
        this.address = address;
    }
    /**
     * Method Get Adress
     * @return String
     */
    public String getAddress(){
        return address;
    }
    /**
     * Method Set Email
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
    }
    /**
     * Method Get Email
     * @return String
     */
    public String getEmail(){
        return email;
    }
    /**
     * Constructs Reservation
     */
    public Reservation(){
    }
    /**
     * Constructs Reservation
     * @param flightID
     * @param classID
     * @param firstName
     * @param lastName
     * @param dob
     * @param cellNum
     * @param address
     * @param email
     */
    // Constructors for adding Reservations. Will be used for ...
    public Reservation(int flightID, int classID, String firstName, String lastName, LocalDate dob, String cellNum, String address, String email){
        this.flightID = flightID;
        this.classID = classID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.cellNum = cellNum;
        this.address = address;
        this.email = email;
    }

    /**
     * Method adds reservation into constructor
     *  @param flightID
     * @param classID
     * @param firstName
     * @param lastName
     * @param dob
     * @param cellNum
     * @param address
     * @param email
     */
    public void addReservation(int flightID, int classID, String firstName, String lastName, LocalDate dob, String cellNum, String address, String email){
        Reservation newReservation = new Reservation(flightID, classID, firstName, lastName, dob, cellNum, address, email);
        reservations.add(newReservation);
    }
    /**
     * Method Gets Current Reservation List
     * @return List
     */
    // Getter for getting the current list of reservations that will be added using the ^^, addReservation
    public List<Reservation> getReservationList(){
        return reservations;
    }
    /**
     * Method Gets Reservation Size
     * @return int
     */
    public int getReservationSize(){
        return reservations.size();
    }
    /**
     * Method Gets Current Reservation Index
     * @return int
     */
    public int currReservationIndex(){
        return currIndex;
    }
    /**
     * Method Resets The Current List Of Reservations
     */
    /*
    flushReservations() will be used after a user clicks (Complete Reservation) or any equivalent button that will complete their 'transaction'
     this will essentially clear all the current list of reservations and will reset counters back to 0 ready for the next set of reservations
    */
    public void flushReservations(){
        reservations.clear();
        currIndex = 0;
    }
    /**
     * Method pushes reservation into database
     * @throws SQLException
     */
    /*
    this pushes all the reservations that have been added via addReservation() iteratively checking, once again
    if the seats are greater than 0, so we dont overbook, but we should have previous checkers before hand, this is just a final checker just in case

     */
    public void pushReservations() throws SQLException {
        Reservation currRes;

        query = "INSERT INTO airlineDatabase.reservationTable (flightID, classID, firstName, lastName, dob, cellNum, address, email) " +
                "VALUES (?, ?, ?, ? ,? ,? ,? ,?)";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        try {
            while (currIndex < reservations.size()) {
                currRes = reservations.get(currIndex);

                preparedStatement.setInt(1, currRes.getFlightID());
                preparedStatement.setInt(2, currRes.getClassID());
                preparedStatement.setString(3, currRes.getFirstName());
                preparedStatement.setString(4, currRes.getLastName());
                preparedStatement.setDate(5, currRes.getDob());
                preparedStatement.setString(6, currRes.getCellNum());
                preparedStatement.setString(7, currRes.getAddress());
                preparedStatement.setString(8, currRes.getEmail());
                preparedStatement.execute();
                currIndex++;
                if((currSeat.getAvailSeats(currRes.getFlightID(), currRes.getClassID())) > 0){
                    currSeat.seatDecrementor(currRes.getFlightID(), currRes.getClassID());
                }
                else{
                    System.out.println(-1); // Redefine error checking
                }
            }
        }
        catch(Exception e){
            throw new IllegalArgumentException(e);
        }

    }
    /**
     * Method Gets Reservation Row
     * @param flightID
     * @param firstName
     * @param lastName
     * @param email
     * @throws SQLException
     * @returns ResultSet
     */
    /*
Given the specific flightID, firstName, lastName and email, we will match this information
that is in the SQL reservationTable and check if there is a row that exists with these information.
If it does we will return a result set of the entire row to be used within the PDF output or(text output)

 the getReservationRow() is intended to be used when the actual reservationID isnt known yet, so will be used
 after a specific reservation is added onto the database
 */
    public ResultSet getReservationRow(int flightID, String firstName, String lastName, String email) throws SQLException{

        query = "SELECT * FROM airlineDatabase.reservationTable WHERE flightID LIKE ? AND firstName LIKE ? AND lastName LIKE ? AND email LIKE ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        try {
            preparedStatement.setInt(1, flightID);
            preparedStatement.setString(2, "%" + firstName + "%");
            preparedStatement.setString(3, "%" + lastName + "%");
            preparedStatement.setString(4, "%" + email + "%");

            result = preparedStatement.executeQuery();
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    /**
     * Method Gets Individual Reservation
     * @param reservationID
     * @param lastName
     * @throws SQLException
     * @return ResultSet
     */
    /*
    getReservation() is used when the actual reservationID is known.
     */
    public ResultSet getReservation(String reservationID, String lastName) throws SQLException{
        query = "SELECT * FROM airlineDatabase.reservationTable WHERE reservationID LIKE ? AND lastName LIKE ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        try{
            preparedStatement.setString(1, "%" + reservationID + "%");
            preparedStatement.setString(2, "%" + lastName + "%");

            result = preparedStatement.executeQuery();
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    /**
     * Method Cancels Reservation
     * @param reservationID
     * @param lastName
     * @throws SQLException
     */
    /*
    Seat incrementor. how do we get the flightID and cabinID to increment the seat?
     */
    public void cancelReservation(String reservationID, String lastName) throws SQLException{
        result = getReservation(reservationID, lastName);
        result.next();
        int currFlightID = result.getInt("flightID");
        int currClassID = result.getInt("classID");
        query = "DELETE FROM airlineDatabase.reservationTable WHERE reservationID LIKE ? AND lastName LIKE ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        outter:
        try{
            preparedStatement.setString(1, "%" + reservationID + "%");
            preparedStatement.setString(2, "%" + lastName + "%");
            preparedStatement.execute();

            try{
                result = getReservation(reservationID, lastName);
                if(!result.next()){
                    currSeat.seatIncrementor(currFlightID, currClassID);
                    break outter;
                }
            }
            catch(Exception e){
                throw new IllegalArgumentException("Reservation was not cancelled. Reservation still exists.");
            }
        }
        catch(Exception e){
            throw new IllegalArgumentException("Incorrect Reservation or ID was inputted.");
        }
    }
    /**
     * Method Gets Reservation From FlightID
     * @param flightID
     * @throws SQLException
     * @return ResultSet
     */
    public ResultSet getReservationsOnFlight(int flightID) throws SQLException{
        query = "SELECT * FROM airlineDatabase.reservationTable WHERE flightID = ?";
        preparedStatement = dbConnector.getConnection().prepareStatement(query);
        try{
            preparedStatement.setInt(1, flightID);
            result = preparedStatement.executeQuery();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    /**
     * Method Prints Reservation List
     * @param res
     */
    public void printResList(List<Reservation> res)
    {
        int size = res.size();
        for (int i = 0; i < size; i++)
        {
            System.out.println(res.get(i).getFlightID());
            System.out.println(res.get(i).getClassID());
            System.out.println(res.get(i).getFirstName() );
            System.out.println(res.get(i).getLastName());
            System.out.println(res.get(i).getDob());
            System.out.println(res.get(i).getCellNum());
            System.out.println(res.get(i).getAddress());
            System.out.println(res.get(i).getEmail());
        }
    }

}
