/**
 * CSUN FALL 23 Java The Hut Airlines
 * This Flight class has all attributes a flight should contain
 * @author Jonathan Alcantar, October 24, 2023
 * @version 1.0
 */

package com.example.javathehutair;

public class Flight
{

    /**
     * Local Class Variables
     */
    private int flightID;
    private String departureLocation;
    private String arrivalLocation;
    private String departureDate;
    private String arrivalDate;
    private String departureTime;
    private String arrivalTime;
    private int currFirstSeats;
    private int currBusinessSeats;
    private int currEconomySeats;

    /**
     * Constructor
     */
    public Flight(int flightID, String departureLocation, String arrivalLocation, String departureDate,
                  String arrivalDate, String departureTime, String arrivalTime, int currFirstSeats,
                  int currBusinessSeats, int currEconomySeats)
    {
        this.flightID = flightID;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.currFirstSeats = currFirstSeats;
        this.currBusinessSeats = currBusinessSeats;
        this.currEconomySeats = currEconomySeats;
    }

    public Flight(int flightID, String departureLocation, String arrivalLocation, String departureDate,
                  String arrivalDate, String departureTime, String arrivalTime)
    {
        this.flightID = flightID;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }


    /**
     * Getters and Setters
     */
    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getCurrFirstSeats() {
        return currFirstSeats;
    }

    public void setCurrFirstSeats(int currFirstSeats) {
        this.currFirstSeats = currFirstSeats;
    }

    public int getCurrBusinessSeats() {
        return currBusinessSeats;
    }

    public void setCurrBusinessSeats(int currBusinessSeats) {
        this.currBusinessSeats = currBusinessSeats;
    }

    public int getCurrEconomySeats() {
        return currEconomySeats;
    }

    public void setCurrEconomySeats(int currEconomySeats) {
        this.currEconomySeats = currEconomySeats;
    }
}
