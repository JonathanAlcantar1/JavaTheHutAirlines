/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Controller Class for the serachFlights_view.fxml
 * @author Jonathan Alcantar, October 24, 2023
 * @version 1.0
 */

package com.example.javathehutair;

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

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import javafx.stage.Stage;

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
    private Flight flight = null ;
    private ObservableList<Flight> flightList = FXCollections.observableArrayList();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String connStr = "jdbc:mysql://airlinedatabase.ceof6ckatc9m.us-east-2.rds.amazonaws.com:3306/airlineDatabase";
    private int index;
    private int mouseClickCounter = 0;
    private int flightID;
    private int paxNum = 0;
    private int firstClassCabinSeats;
    private int bussClassCabinSeats;
    private int econClassCabinSeats;
    private String title = "Error";
    private String contentText = "Please fill out all fields and try search again";

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
     * Method sets an Alert
     * @param title
     * @param contentText
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
     * Method used to refresh the data in the TableView. Calls method searchAllFlights from FlightSearcher
     * @throws SQLException
     */
    @FXML
    private void refreshTable() throws SQLException, NumberFormatException
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
               setErrorAlert(title, contentText);
            }
            else if (arrival.isBlank()) // checks to see if arrival text field is blank
            {
                // if blank program alerts user to try search again
                setErrorAlert(title, contentText);
            }

            else
            {
                //if no date is specified do a general search
                if (departureDate == null && arrivalDate == null)
                {
                    // creates a ResultSet using the flightSearcher object
                    resultSet = flightSearcher.searchAllFlights(departure, arrival, paxNum);
                    setResultSet(resultSet);
                }
                else
                {
                    resultSet = flightSearcher.searchAllFlights(departure, arrival, paxNum, departureDate, arrivalDate);
                    setResultSet(resultSet);
                }

                // iterates through the resultSet
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

                // Checks to see if any flights were found given parameters
                if (flightList.isEmpty())
                {
                    // if not flights program alerts user to try search again
                    setErrorAlert(title, "No flights found with given parameters. Please try search again");
                }
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
            setErrorAlert(title, contentText);
        }


    }

    /**
     * Method used to load the TableView.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void loadFlightTable() throws SQLException
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

        // keeps track of how many times flight object in TableView is clicked
        mouseClickCounter++;

    }


    /**
     * This method opens the CustomerInfo Scene
     * @param event
     * @throws IOException
     */

    @FXML
    void clickSelectFlightButton(ActionEvent event) throws IOException {
        if(mouseClickCounter > 0)
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
            paxInfoController.setPaxNum(paxNum);
            paxInfoController.setFlightID(flightID);
            paxInfoController.setFirstSeats(firstClassCabinSeats);
            paxInfoController.setBussSeats(bussClassCabinSeats);
            paxInfoController.setEconSeats(econClassCabinSeats);

            // Checks to see if there are available seats for ech cabin type
            // if not it hides button for respective cabin
            if (firstClassCabinSeats == 0)
                paxInfoController.getFirstClassCabinButton().setVisible(false);
            if (bussClassCabinSeats == 0)
                paxInfoController.getBussClassCabinButton().setVisible(false);
            if (econClassCabinSeats == 0)
                paxInfoController.getEconClassCabinButton().setVisible(false);

            // Opens the PassengerInfo scene
            Stage stage = new Stage();
            stage.setTitle("Passenger Information");
            stage.setScene(scene);
            stage.show();
        }

    }
    @FXML
    public void clickCancelFlights(ActionEvent event) throws IOException {
        //closing the current stage
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
        //loading search flights fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("cancelFlights_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        //opening search flights stage
        Stage stage = new Stage();
        stage.setTitle("Cancel Flights");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void clickManagerLogin(ActionEvent event) throws IOException {
        //closing the current stage
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
        //loading search flights fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("managerLogin_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        //opening search flights stage
        Stage stage = new Stage();
        stage.setTitle("Manager Login");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void clickManagersInfo(ActionEvent event) throws IOException {

    }
    //Opens AboutUs page when clicked on sidebar
    @FXML
    public void clickAboutUs(ActionEvent event) throws IOException{

    }

    public void setResultSet(ResultSet resultSet){
        this.resultSet = resultSet;
    }
}
