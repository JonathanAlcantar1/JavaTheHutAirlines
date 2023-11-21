/**
 * PassengerFlightCheckerController
 * November 15, 2023
 * @author Ricardo Ramos
 *
 * The most important algorithm within this class would be within refreshTable method, where PassengerFlightChecker's
 *      method 'getPassengersInFlight' is called to obtain all passengers within an instance of a flight. Here we
 *      assign the output of getPassengersInFlight into a local resultSet where it is iteratively put into an ObservableList
 *      that only takes in the object type Reservations (the information is stored within reservations --> Names, Dob, Email etc.)
 *
 *
 * @version 1.0
 */
package com.example.javathehutair;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PassengerFlightCheckerController {
    /**
     * Local class variables
     */
    private SceneController sceneController = new SceneController();
    private ObservableList<Reservation> passengerList = FXCollections.observableArrayList();
    private PassangerFlightChecker passengerHelper = new PassangerFlightChecker();
    private String title = "Error";
    private String contentText = "Please fill out all fields and try search again";
    private ResultSet resultSet = null;
    @FXML
    private TextField arrTxtField;
    @FXML
    private TableView<Reservation> flightsTable;

    @FXML
    private TableColumn<Flight, Integer> flightIDCol;

    @FXML
    private TableColumn<Flight, Integer> classIDCol;

    @FXML
    private TableColumn<Flight, String> lastNameCol;

    @FXML
    private TableColumn<Flight, Date> firstNameCol;

    @FXML
    private TableColumn<Flight, Date> emailCol;

    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to previous scene (manager menu).
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickBackButton(ActionEvent event) throws IOException {
        sceneController.switchScene(event,"manager_View.fxml","Manager View");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Search Flight.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Manager Login.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickManagerLogin(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "manager_View.fxml", "Manager View");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Cancel Flights.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickCancelFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "cancelFlights_view.fxml", "Cancel Flights");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to About Us.
     * @param event
     * @throws IOException
     */
    public void clickAboutUs(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "aboutUs_view.fxml", "About Us");
    }
    /**
     * Method sets error alert to be used if an error is encountered within an input parameter.
     * @param title
     * @param contentText
     */
    public void setErrorAlert(String title, String contentText){
        // if user doesn't select a cabin type program alerts user to try search again
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();

    }

    /**
     * Method used to refresh the data in the TableView. Calls method getPassengersInFlight from PassengerFlightChecker
     * @throws NumberFormatException
     */
    @FXML
    private void refreshTable() throws NumberFormatException
    {

        try {
            passengerList.clear(); // removes all elements from flightList
            Reservation reservation = new Reservation(); // creates instance of FlightSearcher
            // was String Departure
            int arrival = Integer.parseInt(arrTxtField.getText()); // gets text from arrival txt field
            // was LocalDate departure and arrivalDate
            // was paxNum


            if (String.valueOf(arrival).isBlank()) // checks to see if arrival text field is blank
            {
                // if blank program alerts user to try search again
                setErrorAlert(title, contentText);
            }

            else
            {
                resultSet = passengerHelper.getPassengersInFlight(arrival);
                setResultSet(resultSet);


                // iterates through the resultSet
                while (resultSet.next())
                {
                    // adds Flight objects to list
                    passengerList.add(new Reservation(
                            resultSet.getInt("flightID"),
                            resultSet.getInt("classID"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            null,
                            resultSet.getString("cellNum"),
                            resultSet.getString("address"),
                            resultSet.getString("email")));
                    flightsTable.setItems(passengerList);

                }

                // Checks to see if any flights were found given parameters
                if (passengerList.isEmpty())
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
        classIDCol.setCellValueFactory(new PropertyValueFactory<>("classID"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        flightsTable.setItems(passengerList);

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
     * Method sets a current ResultSet
     * @param resultSet
     */
    public void setResultSet(ResultSet resultSet){
        this.resultSet = resultSet;
    }
}

