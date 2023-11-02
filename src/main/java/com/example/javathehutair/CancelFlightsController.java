package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

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
    private String url = "jdbc:mysql://airlinedatabase.ceof6ckatc9m.us-east-2.rds.amazonaws.com:3306/airlineDatabase";
    private String username = "admin";
    private String password = "!Javathehut23";

    @FXML
    public void clickSubmit(ActionEvent event) throws SQLException {
        try {
            setReservationID(reservationTxt.getText());
            setLastName(lastNameTxt.getText());
            Reservation reservation = new Reservation();
            reservation.cancelReservation(getReservationID(), getLastName());
        }
        catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(("Invalid Input"));
            alert.setContentText("Reservation ID and Last Name not recognized");
            alert.showAndWait();
        }
    }

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
