package com.example.javathehutair;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PassengerFlightCheckerController {
    private SceneController sceneController = new SceneController();
    private ObservableList<Reservation> passengerList = FXCollections.observableArrayList();
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


    /**
     * Method used to refresh the data in the TableView. Calls method searchAllFlights from FlightSearcher
     * @throws SQLException
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
                Alerts.setErrorAlert("Error",
                        "Please fill out all fields and try search again");
            }

            else
            {
                resultSet = reservation.getReservationsOnFlight(arrival);
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
                    Alerts.setErrorAlert("Error",
                            "No flights found with given parameters. Please try search again");
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
            Alerts.setErrorAlert("Error",
                    "Please fill out all fields and try search again");
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

    public void setResultSet(ResultSet resultSet){
        this.resultSet = resultSet;
    }
}

