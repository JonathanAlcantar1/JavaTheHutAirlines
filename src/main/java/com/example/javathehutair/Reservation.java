package com.example.javathehutair;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation
{
    private int flightID, classID;
    private int currIndex = 0; // variable used to reference current index that will be initially treated as 0, and reset to 0 after flushReservations()
    private String firstName, lastName, cellNum, address, email;
    private LocalDate dob;
    private String query;
    private List<Reservation> reservations = new ArrayList<>();
    private FlightCabin currSeat = new FlightCabin();
    private String url = "jdbc:mysql://airlinedatabase.ceof6ckatc9m.us-east-2.rds.amazonaws.com:3306/airlineDatabase";
    private String username = "admin";
    private String password = "!Javathehut23";
    // Added url,username,password to be passed into getConnection() --> just to make it look less crowded
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet result;


    public void setFlightID(int flightID){
        this.flightID = flightID;
    }
    public int getFlightID(){return flightID;}
    public void setClassID(int classID){
        this.classID = classID;
    }
    public int getClassID(){
        return classID;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setDob(LocalDate dob){
        this.dob = dob;
    }
    public Date getDob(){
        return Date.valueOf(dob);
    }
    public void setCellNum(String cellNum){
        this.cellNum = cellNum;
    }
    public String getCellNum(){
        return cellNum;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return address;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public Reservation(){
    }
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

    public void addReservation(int flightID, int classID, String firstName, String lastName, LocalDate dob, String cellNum, String address, String email){
        Reservation newReservation = new Reservation(flightID, classID, firstName, lastName, dob, cellNum, address, email);
        reservations.add(newReservation);
    }
    // Getter for getting the current list of reservations that will be added using the ^^, addReservation
    public List<Reservation> getReservationList(){
        return reservations;
    }

    public int getReservationSize(){
        return reservations.size();
    }

    public int currReservationIndex(){
        return currIndex;
    }
    /*
    flushReservations() will be used after a user clicks (Complete Reservation) or any equivalent button that will complete their 'transaction'
     this will essentially clear all the current list of reservations and will reset counters back to 0 ready for the next set of reservations
    */
    public void flushReservations(){
        reservations.clear();
        currIndex = 0;
    }
    /*
    this pushes all the reservations that have been added via addReservation() iteratively checking, once again
    if the seats are greater than 0, so we dont overbook, but we should have previous checkers before hand, this is just a final checker just in case

     */
    public void pushReservations() throws SQLException {
        Reservation currRes;
        connection = DriverManager.getConnection(url, username, password);

        query = "INSERT INTO airlineDatabase.reservationTable (flightID, classID, firstName, lastName, dob, cellNum, address, email) " +
                "VALUES (?, ?, ?, ? ,? ,? ,? ,?)";
        preparedStatement = connection.prepareStatement(query);
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

    /*
Given the specific flightID, firstName, lastName and email, we will match this information
that is in the SQL reservationTable and check if there is a row that exists with these information.
If it does we will return a result set of the entire row to be used within the PDF output or(text output)

 the getReservationRow() is intended to be used when the actual reservationID isnt known yet, so will be used
 after a specific reservation is added onto the database
 */
    public ResultSet getReservationRow(int flightID, String firstName, String lastName, String email) throws SQLException{
        connection = DriverManager.getConnection(url, username, password);

        query = "SELECT * FROM airlineDatabase.reservationTable WHERE flightID LIKE ? AND firstName LIKE ? AND lastName LIKE ? AND email LIKE ?";
        preparedStatement = connection.prepareStatement(query);
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
    /*
    getReservation() is used when the actual reservationID is known.
     */
    public ResultSet getReservation(String reservationID, String lastName) throws SQLException{
        connection = DriverManager.getConnection(url, username, password);
        query = "SELECT * FROM airlineDatabase.reservationTable WHERE reservationID LIKE ? AND lastName LIKE ?";
        preparedStatement = connection.prepareStatement(query);
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
    /*
    Seat incrementor. how do we get the flightID and cabinID to increment the seat?
     */
    public void cancelReservation(String reservationID, String lastName) throws SQLException{
        connection = DriverManager.getConnection(url, username, password);
        result = getReservation(reservationID, lastName);
        result.next();
        int currFlightID = result.getInt("flightID");
        int currClassID = result.getInt("classID");
        query = "DELETE FROM airlineDatabase.reservationTable WHERE reservationID LIKE ? AND lastName LIKE ?";
        preparedStatement = connection.prepareStatement(query);
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
