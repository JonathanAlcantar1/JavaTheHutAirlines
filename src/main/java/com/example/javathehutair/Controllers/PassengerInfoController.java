/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Controller Class for the passengerInfo_view.fxml
 * @author Jonathan Alcantar, October 24, 2023
 * @version 2.0
 */

package com.example.javathehutair.Controllers;

import com.example.javathehutair.Alerts;
import com.example.javathehutair.MainApplication;
import com.example.javathehutair.Reservation.Reservation;
import com.example.javathehutair.flight.FlightCabin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class PassengerInfoController {

    @FXML
    private TextField addressTxt;

    @FXML
    private DatePicker dobDatePicker;

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

    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView home;

    /**
     * Local Class Variables
     */
    private int flightID;
    private int paxNum;
    private int cabinID;
    private int firstSeats, bussSeats, econSeats;
    private String firstName, lastName, phoneNum, address, email;
    private LocalDate dateOfBirth;
    private int paxLabelCounter = 1;
    private Reservation reservation = new Reservation();
    private boolean cabinSelected = false;
    private SceneController sceneController = new SceneController();

    /**
     * Some setters for the cabin class seats
     */
    public void setFirstSeats(int num){firstSeats = num;}

    public void setBussSeats(int num){bussSeats = num;}

    public void setEconSeats(int num){econSeats = num;}

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
     * Method is used to get the first class cabin button
     * @return firstClassButton
     */
    public Button getFirstClassCabinButton(){return firstClassButton;}

    /**
     * Method is used to get the business cabin button
     * @return bussinessButton
     */
    public Button getBussClassCabinButton(){return businessButton;}

    /**
     * Method is used to get the economy cabin button
     * @return economyButton
     */
    public Button getEconClassCabinButton(){return economyButton;}


    /**
     * This method stores pax reservation info and adds the info into a list after the submit button is clicked
     * @param cabinID
     */

    public void setCurrCabinID(int cabinID){
        this.cabinID = cabinID;
    }

    /**
     * Method sets currCabinID when customer selects their designated cabin class
     * @return cabinID
     */

    public int getCurrCabinID(){
        return cabinID;
    }


    /**
     * This method will get and set the cabinID depending on what cabin button the user selects.
     * @param event
     */
    @FXML
    void clickCabinButton(ActionEvent event)
    {

        if (event.getSource() == firstClassButton)
        {
            setCurrCabinID(11);
            cabinSelected = true;
//            System.out.println("First Class Cabin Button Pressed");
        }
        else if(event.getSource() == businessButton)
        {
            // Needs method to extract cabinID from database
            setCurrCabinID(22);
            cabinSelected = true;
//            System.out.println("Business Class Cabin Button Pressed");
        }
        else if(event.getSource() == economyButton)
        {
            // Needs method to extract cabinID from database
            setCurrCabinID(33);
            cabinSelected = true;
//            System.out.println("Economy Class Cabin Button Pressed");
        }

    }

    /**
     * Method is used to decrement the seat amount for the cabin types
     * @param cabinID
     */
    public void cabinSeatDecrementer(int cabinID)
    {
        if (cabinID == 11 && firstSeats != 0)
            firstSeats--;
        else if (cabinID == 22 && bussSeats != 0)
            bussSeats--;
        else if (cabinID == 33 && econSeats != 0)
            econSeats--;
    }

    /**
     * Method is used to hide cabin buttons if no more seats available
     */
    public void cabinButtonHider()
    {
        if (firstSeats == 0)
            firstClassButton.setVisible(false);
        if (bussSeats == 0)
            businessButton.setVisible(false);
        if (econSeats == 0)
            economyButton.setVisible(false);

    }


    /**
     * Method checks to see if a string is blank
     * @param str
     * @return true if blank, otherwise return false
     */
    public Boolean checkIsStringBlank(String str)
    {
        return str.isBlank();

    }



    /**
     * This method stores pax reservation info and adds the info into a list after the submit button is clicked
     * @param event
     */
    @FXML
    void clickSubmit(ActionEvent event) throws IOException, NullPointerException, SQLException {

        // Set the scene label/title
        paxNumLabel.setText(String.valueOf(paxLabelCounter));

        try
        {
            // Checks if there is still more pax that need to enter info
            if (paxNum != 0)
            {
                if (cabinSelected) // checks if a cabin type was selected
                {
                    // Gets all the data from the text fields
                    getDataFromTextFields();

                    // checks to make sure no text field is left blank, if so it sets an alert for the user
                    // if not adds reservation object to the reservations list
                    addResIfNoBlanks();

                }
                else
                {
                    // Sets an alert if user has not selected a cabin type
                    Alerts.setErrorAlert("Error", "Please select a cabin type");
                }

            }

        }
        catch (NullPointerException e)
        {
            // Sets an Alert if Datepicker is left blank (null)
            Alerts.setErrorAlert("Error", "Please fill out all fields");
            e.getStackTrace();
        }

        // If no more pax to add, switches to the CheckoutScene
        nextScene(event);

    }

    /**
     * Method get the data entered in the text fields entered by the user
     * and stores it in variables
     */
    public void getDataFromTextFields()
    {
        firstName = fNameTxt.getText();
        lastName = lNameTxt.getText();
        dateOfBirth = dobDatePicker.getValue();
        phoneNum = pNumTxt.getText();
        address = addressTxt.getText();
        email = emailTxt.getText();
        cabinID = getCurrCabinID();
    }

    /**
     * Method checks if there are no text fields left blank, if so it displays an error
     * alert, otherwise it creates a reservation object and adds it to the reservations list
     */
    public void addResIfNoBlanks ()
    {
        String title = "Error";
        String contentText = "Please fill out all fields";

        if(checkIsStringBlank(firstName))
            Alerts.setErrorAlert(title, contentText);
        else if (checkIsStringBlank(lastName))
            Alerts.setErrorAlert(title, contentText);
        else if (checkIsStringBlank(phoneNum))
            Alerts.setErrorAlert(title, contentText);
        else if (checkIsStringBlank(email))
            Alerts.setErrorAlert(title, contentText);
        else
        {
            // Adds reservation data into a list
               reservation.addReservation(flightID, cabinID, firstName, lastName,
                       dateOfBirth, phoneNum, address, email);
               updateScene();

            // Print Test to see if pax reservation info is being added to list
//                        reservation.printResList(reservation.getReservations());
        }
    }

    /**
     * Method updates the components of the scene
     */
    public void updateScene() {
        // decrements amount of seats available for respective cabin type
        cabinSeatDecrementer(cabinID);

        // hides cabin button with no seats available
        cabinButtonHider();

        // Clears all the text fields
        fNameTxt.clear();
        lNameTxt.clear();
        dobDatePicker.setValue(null);
        pNumTxt.clear();
        addressTxt.clear();
        emailTxt.clear();

        // Update the scene label/title
        paxLabelCounter++;

        // Set the scene label/title
        paxNumLabel.setText(String.valueOf(paxLabelCounter));

        // decrease number of pax left to enter info
        paxNum--;

        // reset cabinSelection
        cabinSelected = false;
    }

    /**
     * Method switches to the next scene if there are no more passengers to enter
     * information for
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void nextScene(ActionEvent event) throws SQLException, IOException {
        if (paxNum == 0) // if no more pax
        {

            // Closes the current scene
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.hide();

            // Loads and opens the Checkout scene
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("checkout_view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1243, 720);

            // Creates an instance of the CheckoutController
            CheckoutController checkoutController = fxmlLoader.getController();

            // Passes the reservation object to the CheckoutController
            checkoutController.setReservation(reservation);
            checkoutController.setSummary();

            // Opens the Checkout scene
            Stage stage = new Stage();
            stage.setTitle("Checkout");
            stage.setScene(scene);
            stage.show();

        }
    }

    /**
     * Refactored methods for Testing
     */
    public int cabinSeatDecrementer(int cabinID, int firstClassSeats,
                                    int bussClassSeats, int econClassSeats)
    {
        int cabinSeats = 0;

        if (cabinID == 11 &&
                firstClassSeats != 0)
            return cabinSeats = firstClassSeats - 1;
        else if (cabinID == 22
                && bussClassSeats != 0)
            return cabinSeats = bussClassSeats - 1;
        else if (cabinID == 33
                && econClassSeats != 0)
            return cabinSeats = econClassSeats - 1;

        return -1;
    }

    public String cabinButtonHider(int firstClassSeats, int bussClassSeats, int econClassSeats)
    {
        String str = " ";
        if (firstClassSeats == 0)
            str = str + "No first Class seats available, ";
        if (bussClassSeats == 0)
            str = str + "No Business Class seats available, ";
        if(econClassSeats == 0)
            str = str + "No Economy Class Seats available, ";

        return str;
    }

    public boolean addResIfNoBlanks (String firstName, String lastName, String phoneNum, String email) {

        if (checkIsStringBlank(firstName))
            return false;
        else if (checkIsStringBlank(lastName))
            return false;
        else if (checkIsStringBlank(phoneNum))
            return false;
        else if (checkIsStringBlank(email))
            return false;
        else
        {
            // Returns true and adds reservation to the list;
            reservation.addReservation(flightID, cabinID, firstName, lastName,
                    dateOfBirth, phoneNum, address, email);
            return true;
        }

    }

    @FXML
    void goHome(MouseEvent event) throws IOException
    {
        // Closes the current scene
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.hide();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("searchFlights_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        Stage stage = new Stage();
        stage.setTitle("Search Flights");
        stage.setScene(scene);
        stage.show();

        // Clears the reservations list
        reservation.flushReservations();

    }
}