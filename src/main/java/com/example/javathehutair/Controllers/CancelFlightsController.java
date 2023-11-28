package com.example.javathehutair.Controllers;

import com.example.javathehutair.Reservation.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.SQLException;
/** CancelFlightsController
 * The purpose of this class is as a controller for the cancel flights page.
 * The important method is clickSubmit which cancels the reservation when submit is clicked by use
 * of the Reservation class
 * @author Teo Dominguez
 * @version 1.0
 * November 21, 2023
 */
public class CancelFlightsController {
    /**
     * Local class variables
     */
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
    private String reservationID;
    private String lastName;
    private SceneController sceneController = new SceneController();

    /**
     * Cancels a reservation given a valid reservationID and lastName in the textbox
     * @param event ActionEvent
     * @throws SQLException
     */
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

    /**
     * Switches to search flights page when search flights is clicked on sidebar
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");
    }

    /**
     * Switches to manager login page when managers is clicked on the sidebar
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void clickManagersInfo(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "managerLogin_view.fxml", "Manager Login");
    }

    /**
     * Switches to about us page when about us is clicked on the sidebar
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void clickAboutUs(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "aboutUs_view.fxml", "About Us");
    }

    /**
     * Method to get Reservation ID
     * @return reservationID
     */
    public String getReservationID(){
        return reservationID;
    }

    /**
     * Method to set Reservation ID
     * @param reservationID String
     */
    public void setReservationID(String reservationID){
        this.reservationID = reservationID;
    }

    /**
     * Method to get Last Name
     * @return lastName
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Method to set Last Name
     * @param lastName String
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
}
