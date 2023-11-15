package com.example.javathehutair;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class PassengerFlightCheckerController {
    private ObservableList<Reservation> passengerList = FXCollections.observableArrayList();
    @FXML
    private TextField arrTxtField;
    private String title = "Error";
    private String contentText = "Please fill out all fields and try search again";
    private ResultSet resultSet = null;
    @FXML
    private TableView<Reservation> flightsTable;
    @FXML
    private TableColumn<Flight, Integer> flightIDCol;

    @FXML
    private TableColumn<Flight, Integer> classIDCol;

    @FXML
    private TableColumn<Flight, String> firstNameCol;

    @FXML
    private TableColumn<Flight, String> lastNameCol;

    @FXML
    private TableColumn<Flight, String> emailCol;
    private Reservation reservation = null;



    @FXML
    public void clickBackButton(ActionEvent event) throws IOException {
        //closing the current stage
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
        //loading search flights fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("manager_View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        //opening search flights stage
        Stage stage = new Stage();
        stage.setTitle("Manager Login");
        stage.setScene(scene);
        stage.show();
    }
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

    public void setResultSet(ResultSet resultSet){
        this.resultSet = resultSet;
    }
}
