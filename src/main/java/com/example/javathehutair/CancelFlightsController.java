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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setContentText("Reservation Successfully Cancelled");
        }
        //If data is not in the database an error window will appear
        catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Reservation ID and Last Name not recognized");
            alert.showAndWait();
        }
    }
    //Opens SearchFlight page when clicked on sidebar
    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException {
        //closing the current stage
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
        //loading search flights fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("searchFlights_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        //opening search flights stage
        Stage stage = new Stage();
        stage.setTitle("Search Flights");
        stage.setScene(scene);
        stage.show();
    }
    //Opens ManagersInfo page when clicked on sidebar
    @FXML
    public void clickManagersInfo(ActionEvent event) throws IOException {

    }
    //Opens AboutUs page when clicked on sidebar
    @FXML
    public void clickAboutUs(ActionEvent event) throws IOException{

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
