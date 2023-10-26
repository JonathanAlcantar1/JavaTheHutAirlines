/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Controller Class for the passengerInfo_view.fxml
 * @author Jonathan Alcantar, October 24, 2023
 * @version 1.0
 */

package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PassengerInfoController {

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField dobTxt;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField fNameTxt;

    @FXML
    private TextField lNameTxt;

    @FXML
    private TextField pNumTxt;

    @FXML
    private Label paxNumLabel;

    @FXML
    private Button submitButton;

    @FXML
    private Button businessButton;

    @FXML
    private Button firstClassButton;

    @FXML
    private Button economyButton;

    /**
     * Local Class Variables
     */
    int flightID;
    int paxNum;
    int cabinID = 1;
    String firstName, lastName, dateOfBirth, phoneNum, address, email;
    int paxLabelCounter = 1;
    Reservation reservation = new Reservation();

    /**
     * Method sets the flightID
     * @param id
     */
    public void setFlightID(int id)
    {
        flightID = id;
    }

    /**
     * Method sets the number of pax reserving a flight
     * @param num
     */
    public void setPaxNum(int num)
    {
        paxNum = num;
    }

    /**
     * This method stores pax reservation info and adds the info into a list after the submit button is clicked
     * @param event
     */

    private void setCurrCabinID(int cabinID){
        this.cabinID = cabinID;
    }

    /**
     * Method sets currCabinID when customer selects their designated cabin class
     * @return
     */

    public int getCurrCabinID(){
        return cabinID;
    }

    /*
    When the user clicks a button (either first class, business, economy etc), that specific actionEvent will assign cabinID to its specific cabinID number
    then we can just use getCurrCabinID during the addReservation
     */



    @FXML
    void clickSubmit(ActionEvent event)
    {

        // Checks if there is still more pax that need to enter info
        if (paxNum != 0)
        {
            // Gets all the data from the text fields
            firstName = fNameTxt.getText();
            lastName = lNameTxt.getText();
            dateOfBirth = dobTxt.getText();
            phoneNum = pNumTxt.getText();
            address = addressTxt.getText();
            email = emailTxt.getText();

            // Adds reservation data into a list
            reservation.addReservation(flightID, cabinID, firstName, lastName, dateOfBirth, phoneNum, address, email);  // We would replace cabinID here with getCurrcabinID()

            // Clears all the text fields
            fNameTxt.clear();
            lNameTxt.clear();
            dobTxt.clear();
            pNumTxt.clear();
            addressTxt.clear();
            emailTxt.clear();

            // decrease number of pax left to enter info
            paxNum--;
        }

        if (paxNum == 0)
        {
            // Closes the current scene
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.hide();

            // TODO: Should Open Cart scene
        }


        // Update the scene label/title
        paxLabelCounter++;
        paxNumLabel.setText(String.valueOf(paxLabelCounter));

        // Print Test to see if pax reservation info is being added to list
        reservation.printResList(reservation.getReservationList());


    }


}