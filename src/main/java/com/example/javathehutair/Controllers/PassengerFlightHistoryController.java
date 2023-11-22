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
/**
 * The purpose of this class is to both gather and display all passenger flight history when given the
 * passenger information
 * @author Teo Dominguez
 * @version 1.0
 * November 21, 2023
 */
public class PassengerFlightHistoryController {
    /**
     * Local class variables
     */
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
    private SceneController sceneController = new SceneController();
    //used for flight table input
    private ObservableList<Flight> flightList = FXCollections.observableArrayList();
    //used for reservation table input
    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    //used to store flightIds for use in a search to populate flightlist
    private ArrayList<String> flightIdList = new ArrayList<String>();
    private String firstName, lastName, cellNum;
    private java.sql.Date dob;

    /** PassengerFlightHistoryController
     * Method that handles when submit is clicked. Checks if input is valid
     * then searches and dispalys all reservations and corresponding flights
     * @param Event ActionEvent
     * @throws SQLException
     */
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

    /**
     * method that clears reservationList,reservationTable, and flightIdList
     * then populates reservationList with reservations and flightIdList with flight IDs
     * @param firstName String
     * @param lastName String
     * @param dob Date
     * @param cellNum String
     * @throws SQLException
     */
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

    /**
     * Method that loads cells of the reservation table
     */
    public void loadReservationTable(){
        reservationCol.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        classCol.setCellValueFactory(new PropertyValueFactory<>("classID"));
        reservationTable.setItems(reservationList);
    }

    /**
     * Method that clears flightList/flight table and then populates flight list with flights
     * @throws SQLException
     */
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

    /**
     * Method to load cells of the flight table
     */
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

    /**
     *nMethod that creates an error message with the given title and content text
     * @param title String
     * @param contentText String
     */
    public void setErrorAlert(String title, String contentText)
    {
        // if user doesn't select a cabin type program alerts user to try search again
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();

    }

    /**
     * Method to switch to manager view page when back button is clicked
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void clickBackButton(ActionEvent event) throws IOException {
        sceneController.switchScene(event,"manager_View.fxml","Manager View");
    }

    /**
     * Method to switch to search flights page when search flights is clicked on sidebar
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");
    }

    /**
     * Method to switch to manager login page when managers is clicked on sidebar
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void clickManagerLogin(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "manager_View.fxml", "Manager View");
    }

    /**
     * Method to switch to cancel flights page when cancel flights is clicked on sidebar
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void clickCancelFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "cancelFlights_view.fxml", "Cancel Flights");
    }

    /**
     * Method to switch to about us page when about us is clicked on sidebar
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void clickAboutUs(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "aboutUs_view.fxml", "About Us");
    }

    /**
     * Method to set Date of Birth
     * @param dob String
     */
    public void setDob(String dob){
        try {
            this.dob = java.sql.Date.valueOf(dob);
        }
        catch (IllegalArgumentException e){
            this.dob = java.sql.Date.valueOf("1111-11-11");
        }
    }

    /**
     * Method to get Date of Birth
     * @return dob
     */
    public java.sql.Date getDob(){
        return dob;
    }

    /**
     * Method to set First Name
     * @param firstName String
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Method to get First Name
     * @return firstName
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * Method to set Last Name
     * @param lastName String
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Method to get Last Name
     * @return lastName
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Method to set Cellphone Number
     * @param cellNum String
     */
    public void setCellNum(String cellNum){
        this.cellNum = cellNum;
    }

    /**
     * Method to get Cellphone Number
     * @return cellNum
     */
    public String getCellNum(){
        return cellNum;
    }
}
