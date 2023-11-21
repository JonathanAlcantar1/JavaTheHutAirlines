package com.example.javathehutair;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class PassengerReservationCheckerController {
    @FXML
    private Button submitButton;
    @FXML
    private TextField reservationTextBox;
    @FXML
    private TextField firstNameTxt;
    @FXML
    private TextField lastNameTxt;
    @FXML
    private TextField cellNumberTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField dobTxt;
    @FXML
    private TextField flightTxt;
    @FXML
    private TextField departureTxt;
    @FXML
    private TextField arrivalTxt;
    @FXML
    private TextField depDateTxt;
    @FXML
    private TextField arrivDateTxt;
    @FXML
    private TextField classTxt;


    private SceneController sceneController = new SceneController();
    private Reservation reservation = new Reservation();
    private ObservableList<Reservation> passengerList = FXCollections.observableArrayList();
    private String flightID,classID;
    private FlightSearcher flightSearcher = new FlightSearcher();

    @FXML
    public void clickSubmitButton(ActionEvent event) throws SQLException{
        clearText();
        String reservationID = reservationTextBox.getText();
        ResultSet resultSet = null;
        //Error message if submit is pressed and nothing is in the textbox
        if(reservationID.isBlank() || reservationID.isEmpty()){
            setErrorAlert("Error", "Please Enter A Reservation ID");
        }
        else {
            resultSet = reservation.getReservation(reservationID);
            //if resultset is null no match was found present error
            if(!resultSet.isBeforeFirst()){
                setErrorAlert("Error","Reservation Not Found");
            }
            //else show the reservation info
            else {
                showReservationInfo(resultSet);
                resultSet = flightSearcher.searchSpecificFlight(getFlightID());
                showFlightInfo(resultSet);
            }
        }


    }
    public void showReservationInfo(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            setFlightID(resultSet.getString("flightID"));
            setClassID(resultSet.getString("classID"));
            if (Objects.equals(getClassID(), "11")){
                classTxt.setText("First");
            }
            else if (Objects.equals(getClassID(), "22")){
                classTxt.setText("Business");
            }
            else{
                classTxt.setText("Economy");
            }
            reservationTextBox.setText(resultSet.getString("reservationID"));
            flightTxt.setText(getFlightID());
            firstNameTxt.setText(resultSet.getString("firstName"));
            lastNameTxt.setText(resultSet.getString("lastName"));
            dobTxt.setText(resultSet.getString("dob"));
            cellNumberTxt.setText(resultSet.getString("cellNum"));
            addressTxt.setText(resultSet.getString("address"));
            emailTxt.setText(resultSet.getString("email"));
        }

    }
    public void showFlightInfo(ResultSet resultSet) throws SQLException{
        while (resultSet.next()){
            departureTxt.setText(resultSet.getString("departureLocation"));
            arrivalTxt.setText(resultSet.getString("arrivalLocation"));
            depDateTxt.setText(resultSet.getString("departureDate"));
            arrivDateTxt.setText(resultSet.getString("arrivalDate"));
        }
    }
    public void clearText(){
        firstNameTxt.clear();
        lastNameTxt.clear();
        dobTxt.clear();
        cellNumberTxt.clear();
        addressTxt.clear();
        emailTxt.clear();
        departureTxt.clear();
        arrivalTxt.clear();
        depDateTxt.clear();
        arrivDateTxt.clear();
        classTxt.clear();
        flightTxt.clear();
    }
    public void setErrorAlert(String title, String contentText)
    {
        // if user doesn't select a cabin type program alerts user to try search again
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();

    }
        @FXML
    public void clickBackButton(ActionEvent event) throws IOException {
        sceneController.switchScene(event,"manager_View.fxml","Manager View");
    }
    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");
    }
    @FXML
    public void clickManagerLogin(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "manager_View.fxml", "Manager View");
    }
    @FXML
    public void clickCancelFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "cancelFlights_view.fxml", "Cancel Flights");
    }
    public void clickAboutUs(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "aboutUs_view.fxml", "About Us");
    }

    public void setFlightID(String flightID){
        this.flightID = flightID;
    }
    public String getFlightID(){
        return this.flightID;
    }
    public void setClassID(String classID){
        this.classID = classID;
    }
    public String getClassID(){
        return this.classID;
    }
}
