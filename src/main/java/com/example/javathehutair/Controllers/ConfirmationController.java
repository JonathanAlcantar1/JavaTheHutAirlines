/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Controller Class for the confirmation_view.fxml
 * @author Jonathan Alcantar, November 13, 2023
 * @version 2.0
 */

package com.example.javathehutair.Controllers;

import com.example.javathehutair.MainApplication;
import com.example.javathehutair.flight.Flight;
import com.example.javathehutair.flight.FlightSearcher;
import com.example.javathehutair.passenger.Passenger;
import com.example.javathehutair.Reservation.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ConfirmationController
{
    @FXML
    private ImageView home;

    @FXML
    private TableView<Passenger> paxTable;

    @FXML
    private TableColumn<Passenger, String> paxFirstNameCol;

    @FXML
    private TableColumn<Passenger, String> paxLastNameCol;

    @FXML
    private TableColumn<Passenger, Integer> resIDCol;

    @FXML
    private TableColumn<Passenger, String> cabinTypeCol;


    @FXML
    private TableView<Flight> flightTable;

    @FXML
    private TableColumn<Flight, Integer> flightIDCol;

    @FXML
    private TableColumn<Flight, String> depLocCol;

    @FXML
    private TableColumn<Flight, String> arrLocCol;

    @FXML
    private TableColumn<Flight, Date> depTimeCol;

    @FXML
    private TableColumn<Flight, Date> arrTimeCol;

    @FXML
    private TableColumn<Flight, Date> depDateCol;

    @FXML
    private TableColumn<Flight, Date> arrDateCol;


    @FXML
    private ImageView logoImage;
    /**
     * Local variables
     */
    private ResultSet resultSet = null;
    private Reservation reservation = new Reservation();
    private ObservableList<Flight> flightList = FXCollections.observableArrayList();
    private ObservableList<Passenger> paxList = FXCollections.observableArrayList();

    /**
     * Method used to set the reservations list
     * @param res
     */
    public void setReservation (Reservation res)
    {
        reservation = res;
    }

    /**
     * Method used to load the pax table with data from a Reservations list
     * @throws SQLException
     */
    public void loadPaxTable() throws SQLException {

        // Iterates through the reservations list
        for (int i = 0; i < reservation.getReservationList().size(); i++)
        {
            // Gets necessary parameters from each reservation object
            int flightID = reservation.getReservationList().get(i).getFlightID();
            String fName = reservation.getReservationList().get(i).getFirstName();
            String lName = reservation.getReservationList().get(i).getLastName();
            String email = reservation.getReservationList().get(i).getEmail();

            // Gets the reservation row from database and stores it in a resultSet
            resultSet = reservation.getReservationRow(flightID, fName, lName, email);

            // sets the resultSet
            setResultSet(resultSet);

            populatePaxList(i);

        }

        // Populates the cells from the paxTable
        loadPaxTableCells();


    }

    private void loadPaxTableCells()
    {
        paxFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        paxLastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        resIDCol.setCellValueFactory(new PropertyValueFactory<>("ReservationID"));
        cabinTypeCol.setCellValueFactory(new PropertyValueFactory<>("CabinType"));
        paxTable.setItems(paxList);
    }

    private void populatePaxList(int index) throws SQLException
    {
        // Iterates through the resultSet
        while (resultSet.next())
        {
            // Adds new Passenger object into paxList
            paxList.add(new Passenger(reservation.getReservationList().get(index).getFirstName(),
                    reservation.getReservationList().get(index).getLastName(),
                    resultSet.getString("reservationID"),
                    returnCabinStr(reservation.getReservationList().get(index).getClassID())));
            paxTable.setItems(paxList);

        }
    }

    /**
     * Method is used to load the flightTable
     * @throws SQLException
     */
    public void loadFlightTable() throws SQLException
    {
        try
        {
            FlightSearcher flightSearcher = new FlightSearcher(); // creates instance of FlightSearcher
            resultSet = flightSearcher.searchSpecificFlight(reservation.getReservationList().get(0).getFlightID());

            // adds flight objects into the flightList
            populateFlightList(resultSet, flightList, flightTable);

            // loads up the cells in the TableView
            loadFlightTableCells();

        }
        catch(SQLException e)
        {
            System.out.println("Error while fetching from the database!");
        }
    }

    /**
     * Method is used to add Flight objects into flightList
     * @param resultSet
     * @param flightList
     * @param flightTable
     * @throws SQLException
     */
    public void populateFlightList(ResultSet resultSet, ObservableList<Flight> flightList,
                                              TableView<Flight> flightTable) throws SQLException
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
                    resultSet.getString("arrivalTime")));
            flightTable.setItems(flightList);

        }
    }

    /**
     * Method is used to populate the cells from the flightTable
     */
    public void loadFlightTableCells()
    {
        flightIDCol.setCellValueFactory(new PropertyValueFactory<>("flightID"));
        depLocCol.setCellValueFactory(new PropertyValueFactory<>("departureLocation"));
        arrLocCol.setCellValueFactory(new PropertyValueFactory<>("arrivalLocation"));
        depDateCol.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        arrDateCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        depTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        flightTable.setItems(flightList);
    }

    /**
     * Method takes in a cabinID int and returns a Cabin class String
     * @param cabinID
     * @return
     */
    public String returnCabinStr(int cabinID)
    {
        String cabinStr = null;

        if (cabinID == 11)
            cabinStr = "First Class";
        else if (cabinID == 22)
            cabinStr = "Business";
        else if (cabinID == 33)
            cabinStr = "Economy";

        return cabinStr;
    }

    /**
     * Method used to set the resultSet
     * @param resultSet
     */
    public void setResultSet(ResultSet resultSet){
        this.resultSet = resultSet;
    }

    @FXML
    void goHome(MouseEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("searchFlights_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        Stage stage = new Stage();
        stage.setTitle("Search Flights");
        stage.setScene(scene);
        stage.show();

    }

    }
