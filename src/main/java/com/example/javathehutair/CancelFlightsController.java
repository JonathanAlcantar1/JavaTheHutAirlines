package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CancelFlightsController {

    @FXML
    private Button aboutUsButton;

    @FXML
    private Button cancelFlightsButton;

    @FXML
    private Button managersButton;

    @FXML
    private Button searchFlightsButton;

    @FXML
    private TextField reservationTxt;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private Button submitButton;

    /* local variables */
    private String reservationID;
    private String lastName;
    private SceneController sceneController = new SceneController();

    //Method to cancel a flight reservation given a reservation ID and LastName
    @FXML
    public void clickSubmit(ActionEvent event) throws SQLException {
        try {
            //gets reservationID and lastName from the textbox
            setReservationID(reservationTxt.getText());
            setLastName(lastNameTxt.getText());
            Reservation reservation = new Reservation();

            //cancels reservation if ID and name combo are valid
            reservation.cancelReservation(getReservationID(), getLastName());

            //success message
            Alerts.setSuccessAlert("Success!",
                    "Reservation Successfully Cancelled");

        }
        //If data is not in the database an error window will appear
        catch (SQLException e){
            Alerts.setErrorAlert("Invalid Input",
                    "Reservation ID and Last Name not recognized");
        }
    }
    //Opens SearchFlight page when clicked on sidebar
    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");
    }
    @FXML
    public void clickManagersInfo(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "managerLogin_view.fxml", "Manager Login");
    }
    //Opens AboutUs page when clicked on sidebar
    @FXML
    public void clickAboutUs(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "aboutUs_view.fxml", "About Us");
    }
                                   //setters and getters
    public String getReservationID(){
        return reservationID;
    }
    public void setReservationID(String reservationID){
        this.reservationID = reservationID;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }


}
