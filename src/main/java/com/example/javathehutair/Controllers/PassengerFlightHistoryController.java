package com.example.javathehutair.Controllers;

import com.example.javathehutair.Reservation.Reservation;
import com.example.javathehutair.flight.Flight;
import com.example.javathehutair.flight.FlightSearcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class PassengerFlightHistoryController {
    private SceneController sceneController = new SceneController();

    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableView<Flight> flightsTable;

    @FXML
    private TableColumn<Flight, Integer> flightCol;

    @FXML
    private TableColumn<Flight, String> deptCol;

    @FXML
    private TableColumn<Flight, String> arrivCol;

    @FXML
    private TableColumn<Flight, Date> depDateCol;

    @FXML
    private TableColumn<Flight, Date> arrivDateCol;

    @FXML
    private TableColumn<Flight, Date> depTimeCol;

    @FXML
    private TableColumn<Flight, Date> arrivTimeCol;

    @FXML
    private TableColumn<Reservation,String> reservationCol;
    @FXML
    private TableColumn<Reservation,String> classCol;

    @FXML
    private Button searchButton;
    @FXML
    private TextField firstNameTxt;
    @FXML
    private TextField lastNameTxt;
    @FXML
    private TextField dobTxt;
    @FXML
    private TextField cellNumTxt;

    private ObservableList<Flight> flightList = FXCollections.observableArrayList();
    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

    private ArrayList<String> flightIdList = new ArrayList<String>();

    private String firstName, lastName, cellNum;
    private java.sql.Date dob;
    //TODO: Finish error when info left blank
    public void clickSearchButton(ActionEvent Event) throws SQLException {
        if(firstNameTxt.getText().isEmpty() || lastNameTxt.getText().isEmpty()
                || dobTxt.getText().isEmpty() || cellNumTxt.getText().isEmpty()){
            setErrorAlert("Error", "Please Enter All Fields");
        }
        else{
            setFirstName(firstNameTxt.getText());
            setLastName(lastNameTxt.getText());
            setDob(dobTxt.getText());
            setCellNum(cellNumTxt.getText());
            refreshReservationTable(getFirstName(),getLastName(),getDob(),getCellNum());
            if(reservationList.isEmpty()) {
                setErrorAlert("Error", "Customer Has No Reservation History");
            }
            else {
                loadReservationTable();
                refreshFlightTable();
                loadFlightTable();
            }
        }
    }
    public void refreshReservationTable(String firstName, String lastName, java.sql.Date dob, String cellNum) throws SQLException {
        reservationList.clear();
        flightIdList.clear();
        Reservation reservation = new Reservation();
        ResultSet resultset = reservation.getReservationId(firstName, lastName, dob, cellNum);
        while (resultset.next()){
            reservationList.add(new Reservation(resultset.getString("reservationID"), resultset.getInt("classID")));
            flightIdList.add(resultset.getString("flightID"));
            reservationTable.setItems(reservationList);
        }
    }
    public void loadReservationTable(){
        reservationCol.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        classCol.setCellValueFactory(new PropertyValueFactory<>("classID"));
        reservationTable.setItems(reservationList);
    }
    public void refreshFlightTable() throws SQLException {
        flightList.clear();
        FlightSearcher flightSearcher = new FlightSearcher();
        ResultSet resultSet = null;
        for(int i = 0; i < flightIdList.size(); i++){
            resultSet = flightSearcher.searchSpecificFlight(flightIdList.get(i));
            while (resultSet.next()){
                flightList.add(new Flight(
                        resultSet.getInt("flightID"),
                        resultSet.getString("departureLocation"),
                        resultSet.getString("arrivalLocation"),
                        resultSet.getString("departureDate"),
                        resultSet.getString("arrivalDate"),
                        resultSet.getString("departureTime"),
                        resultSet.getString("arrivalTime"),
                        0,
                        0,
                        0));
                flightsTable.setItems(flightList);
            }
        }
    }
    public void loadFlightTable(){
        flightCol.setCellValueFactory(new PropertyValueFactory<>("flightID"));
        deptCol.setCellValueFactory(new PropertyValueFactory<>("departureLocation"));
        arrivCol.setCellValueFactory(new PropertyValueFactory<>("arrivalLocation"));
        depDateCol.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        arrivDateCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        depTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        flightsTable.setItems(flightList);
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
    @FXML
    public void clickAboutUs(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "aboutUs_view.fxml", "About Us");
    }
    public void setDob(String dob){
        this.dob = java.sql.Date.valueOf(dob);
    }
    public java.sql.Date getDob(){
        return dob;
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
    public void setCellNum(String cellNum){
        this.cellNum = cellNum;
    }
    public String getCellNum(){
        return cellNum;
    }
}
