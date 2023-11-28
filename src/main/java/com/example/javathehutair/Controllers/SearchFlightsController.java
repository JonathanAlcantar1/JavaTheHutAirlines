/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Controller Class for the serachFlights_view.fxml
 * @author Jonathan Alcantar, October 24, 2023
 * @version 2.0
 */

package com.example.javathehutair.Controllers;

import com.example.javathehutair.MainApplication;
import com.example.javathehutair.flight.Flight;
import com.example.javathehutair.flight.FlightSearcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class SearchFlightsController {

    @FXML
    private Button aboutUsButton;

    @FXML
    private TextField arrTxtField;

    @FXML
    private Button cancelFlightsButton;

    @FXML
    private TextField depTxtField;

    @FXML
    private Button managersButton;

    @FXML
    private TextField noPaxTxtField;

    @FXML
    private Button searchButton;

    @FXML
    private Button searchFlightsButton;

    @FXML
    private Button selectFlightButton;

    @FXML
    private DatePicker arrivalButton;

    @FXML
    private DatePicker departureButton;

    @FXML
    private TableView<Flight> flightsTable;

    @FXML
    private TableColumn<Flight, Integer> flightIDCol;

    @FXML
    private TableColumn<Flight, String> depLocCol;

    @FXML
    private TableColumn<Flight, String> arrLocCol;

    @FXML
    private TableColumn<Flight, Date> depDateCol;

    @FXML
    private TableColumn<Flight, Date> arrDateCol;

    @FXML
    private TableColumn<Flight, Date> depTimeCol;

    @FXML
    private TableColumn<Flight, Date> arrTimeCol;

    @FXML
    private TableColumn<Flight, Integer> firstClassCol;

    @FXML
    private TableColumn<Flight, Integer> bussClassCol;

    @FXML
    private TableColumn<Flight, Integer> econClassCol;

    @FXML
    private ImageView logoImage;


    /**
     * Local Class Variables
     */
    private ObservableList<Flight> flightList = FXCollections.observableArrayList();
    private ResultSet resultSet = null;
    private int index;
    private boolean flightClicked = false;
    private int flightID;
    private int paxNum = 0;
    private int firstClassCabinSeats;
    private int bussClassCabinSeats;
    private int econClassCabinSeats;
    private SceneController sceneController = new SceneController();

    /**
     * Method checks to see if a string is blank
     * @param str
     * @return true if blank, otherwise return false
     */
    public boolean checkIsStringBlank(String str)
    {
        return str.isBlank();

    }


    /**
     * Method used to refresh the data in the TableView. Calls method searchAllFlights from FlightSearcher
     * @throws NumberFormatException
     */
    @FXML
    public void refreshTable() throws NumberFormatException
    {

        try {
            flightList.clear(); // removes all elements from flightList
            FlightSearcher flightSearcher = new FlightSearcher(); // creates instance of FlightSearcher
            String departure = depTxtField.getText(); // gets text from departure txt field
            String arrival = arrTxtField.getText(); // gets text from arrival txt field
            LocalDate departureDate = departureButton.getValue(); //gets date from departure datepicker
            LocalDate arrivalDate = arrivalButton.getValue(); //gets date from arrival date picker
            paxNum = Integer.parseInt(noPaxTxtField.getText()); // gest the number of pax/tickets


            if (checkIsStringBlank(departure)) // checks if departure text field is blank
            {
               Alerts.setErrorAlert("Error", "Please fill out all fields and try search again");
            }
            else if (checkIsStringBlank(arrival)) // checks to see if arrival text field is blank
            {
                // if blank program alerts user to try search again
                Alerts.setErrorAlert("Error", "Please fill out all fields and try search again");
            }
            else
            {
                //
                setFlightResultSet(departureDate, arrivalDate, flightSearcher, departure, arrival);

                // iterates through the resultSet
                populateFlightList(resultSet, flightList, flightsTable);

                // checks if the flight list is empty
                isFlightListEmpty();
            }

        }
        catch (SQLException e)
        {
            System.out.println("Refreshing Table Failed! " + e);
            e.getStackTrace();
        }
        catch (NumberFormatException e)
        {
            System.out.println("Forgot to add pax number! " + e);

            // Sets an alert if user leaves pax number empty
            Alerts.setErrorAlert("Error",
                    "Please fill out all fields and try search again");
        }


    }

    /**
     * Method checks if the flightsList is empty, if so it sets an error alert
     */
    public void isFlightListEmpty() {
        // Checks to see if any flights were found given parameters
        if (flightList.isEmpty())
        {
            // if not flights program alerts user to try search again
            Alerts.setErrorAlert("Error",
                    "No flights found with given parameters. Please try search again");
        }
    }

    /**
     * Method
     * @param resultSet
     * @param flightList
     * @param flightsTable
     * @throws SQLException
     */
    public void populateFlightList(ResultSet resultSet, ObservableList<Flight> flightList,
                                   TableView<Flight> flightsTable) throws SQLException
    {
        while (resultSet.next())
        {
            // adds Flight objects to list
            flightList.add(new Flight(
                    resultSet.getInt("flightID"),
                    resultSet.getString("departureLocation"),
                    resultSet.getString("arrivalLocation"),
                    resultSet.getString("departureDate"),
                    resultSet.getString("arrivalDate"),
                    resultSet.getString("departureTime"),
                    resultSet.getString("arrivalTime"),
                    resultSet.getInt("currFirstSeats"),
                    resultSet.getInt("currBusinessSeats"),
                    resultSet.getInt("currEconomySeats")));
            flightsTable.setItems(flightList);

        }
    }

    public void setFlightResultSet(LocalDate departureDate, LocalDate arrivalDate,
                                   FlightSearcher flightSearcher, String departure, String arrival)
    {
        //if no date is specified do a general search
        if (departureDate == null && arrivalDate == null)
        {
            // sets the ResultSet using the flightSearcher object
            this.resultSet = flightSearcher.searchAllFlights(departure, arrival, paxNum);
        }
        else
        {
            // sets the ResultSet using the flightSearcher object
            this.resultSet = flightSearcher.searchAllFlights(departure, arrival, paxNum, departureDate, arrivalDate);
        }
    }

    /**
     * Method used to load the TableView.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void loadFlightTable() throws SQLException
    {
        // Refreshes the TableView
        refreshTable();

        // loads up the cells in the TableView
        flightIDCol.setCellValueFactory(new PropertyValueFactory<>("flightID"));
        depLocCol.setCellValueFactory(new PropertyValueFactory<>("departureLocation"));
        arrLocCol.setCellValueFactory(new PropertyValueFactory<>("arrivalLocation"));
        depDateCol.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        arrDateCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        depTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        firstClassCol.setCellValueFactory(new PropertyValueFactory<>("currFirstSeats"));
        bussClassCol.setCellValueFactory(new PropertyValueFactory<>("currBusinessSeats"));
        econClassCol.setCellValueFactory(new PropertyValueFactory<>("currEconomySeats"));
        flightsTable.setItems(flightList);

    }


    /**
     * This method loads the TableView with flights when the search button is clicked
     * @param event
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void clickSearchButton(ActionEvent event) throws SQLException, ClassNotFoundException
    {
        // calls the loadFlightTable() method
        loadFlightTable();
    }

    /**
     * This method gets the FlightID of a flight from the TableView when clicked
     * @param mouseEvent
     */
    public void getFlightID(MouseEvent mouseEvent) {
        // gets the index of where mouse was clicked on the TableView
        index = flightsTable.getSelectionModel().getSelectedIndex();

        // If index is less than or equal to -1 does nothing
        if (index <= -1)
        {
            return;
        }

//        System.out.println(flightIDCol.getCellData(index));

        // saves the flightID and the available cabin seats for each respective cabin type
        flightID = flightIDCol.getCellData(index);
        firstClassCabinSeats = firstClassCol.getCellData(index);
        bussClassCabinSeats = bussClassCol.getCellData(index);
        econClassCabinSeats = econClassCol.getCellData(index);

        // keeps track if a flight object in TableView is clicked
        flightClicked = true;

    }


    /**
     * This method opens the CustomerInfo Scene
     * @param event
     * @throws IOException
     */

    @FXML
    void clickSelectFlightButton(ActionEvent event) throws IOException {
        if(flightClicked)
        {

            // Closes the current scene
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.hide();

            // Loads and opens the PassengerInfo scene
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("passengerInfo_view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1243, 720);

            // Creates an instance of the PassengerInfoController
            PassengerInfoController paxInfoController = fxmlLoader.getController();

            // Passes the selected number of pax, flightID, and no. of cabin seats to the PassengerInfoController
            passInfoToNextScene(paxInfoController);

            // Checks to see if there are available seats for each cabin type
            // if not it hides button for respective cabin in the passengerInfo_view.fxml
            buttonHider(paxInfoController);

            // Opens the PassengerInfo scene
            Stage stage = new Stage();
            stage.setTitle("Passenger Information");
            stage.setScene(scene);
            stage.show();

        }

    }

    public void passInfoToNextScene(PassengerInfoController paxInfoController) {
        paxInfoController.setPaxNum(paxNum);
        paxInfoController.setFlightID(flightID);
        paxInfoController.setFirstSeats(firstClassCabinSeats);
        paxInfoController.setBussSeats(bussClassCabinSeats);
        paxInfoController.setEconSeats(econClassCabinSeats);
    }

    public void buttonHider(PassengerInfoController paxInfoController) {
        if (firstClassCabinSeats == 0)
            paxInfoController.getFirstClassCabinButton().setVisible(false);
        if (bussClassCabinSeats == 0)
            paxInfoController.getBussClassCabinButton().setVisible(false);
        if (econClassCabinSeats == 0)
            paxInfoController.getEconClassCabinButton().setVisible(false);
    }

    @FXML
    public void clickCancelFlights(ActionEvent event) throws IOException {
       sceneController.switchScene(event, "cancelFlights_view.fxml", "Cancel Flights");
    }
    @FXML
    public void clickManagerLogin(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "managerLogin_view.fxml", "Manager Login");
    }

    //Opens AboutUs page when clicked on sidebar
    @FXML
    public void clickAboutUs(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "aboutUs_view.fxml", "About Us");
    }

}
