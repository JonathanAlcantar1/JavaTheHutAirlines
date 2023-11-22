/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Passenger class used to make Passenger object with necessary
 * attributes
 * @author Jonathan Alcantar, November 13, 2023
 * @version 1.0
 */

package com.example.javathehutair.passenger;

import javafx.beans.property.SimpleStringProperty;

public class Passenger
{
    /**
     * Local Variables
     */
    private SimpleStringProperty firstName, lastName, reservationID, cabinType;

    /**
     * Constructor
     * @param fName
     * @param lName
     * @param resID
     * @param cabin
     */
    Passenger(String fName, String lName, String resID, String cabin)
    {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.reservationID = new SimpleStringProperty(resID);
        this.cabinType = new SimpleStringProperty(cabin);

    }

    /**
     * Getters and Setters
     */
    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getCabinType() {
        return cabinType.get();
    }

    public SimpleStringProperty cabinTypeProperty() {
        return cabinType;
    }

    public void setCabinType(String cabinType) {
        this.cabinType.set(cabinType);
    }

    public String getReservationID() {
        return reservationID.get();
    }

    public SimpleStringProperty reservationIDProperty() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID.set(reservationID);
    }
}
