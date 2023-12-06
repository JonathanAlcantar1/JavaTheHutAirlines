/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Controller Class for the checkout_view.fxml
 * @author Jonathan Alcantar, October 24, 2023
 * @version 2.0
 */

package com.example.javathehutair.Controllers;

import com.example.javathehutair.Alerts;
import com.example.javathehutair.checkout.Checkout;
import com.example.javathehutair.flight.FlightSearcher;
import com.example.javathehutair.MainApplication;
import com.example.javathehutair.Reservation.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class CheckoutController {
    
    @FXML
    private TextField creditNumTxt;

    @FXML
    private TextField cvcTxt;

    @FXML
    private TextField fnameTxt;

    @FXML
    private TextField lNameTxt;

    @FXML
    private TextField mNameTxt;

    @FXML
    private Button submitButton;

    @FXML
    private TextField expirationDate;

    @FXML
    private ImageView cardsImage;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label flightIDLabel;

    @FXML
    private Label fromToLabel;

    @FXML
    private Label noOfPaxLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private ImageView home;


    /**
     * Local Variables
     */
    private String fName, lName, mName;
    private long cvc, creditNum;
    private YearMonth expDate;
    private Checkout checkout = new Checkout();
    private Reservation reservation;
    private ResultSet resultSet = null;
    private SceneController sceneController = new SceneController();
    private Reservation reserve = new Reservation();

    /**
     * Method sets the reservation
     * @param res
     */
    public void setReservation (Reservation res)
    {
        reservation = res;
    }

    /**
     * Method sets the summary content
     * @throws SQLException
     */
    public void setSummary() throws SQLException
    {
        int flightID = reservation.getReservationList().get(0).getFlightID();
        FlightSearcher flightSearcher = new FlightSearcher(); // creates instance of FlightSearcher
        resultSet = flightSearcher.searchSpecificFlight(flightID);
        setResultSet(resultSet);

        while(resultSet.next())
        {
            fromToLabel.setText("From: " + resultSet.getString("departureLocation") +
                    " " + "To: " + resultSet.getString("arrivalLocation"));
        }
        flightIDLabel.setText("Flight ID: " + flightID);
        noOfPaxLabel.setText("Number of Passengers: " + reservation.getReservationSize());
        totalLabel.setText("Total Price: $" + reserve.totalReservationPrice(reservation.getReservationList()) + ".00");
    }


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
     * Method verifies the payment after user clicks the submit button
     * @param event
     */

    @FXML
    void clickSubmitButton(ActionEvent event)throws NumberFormatException
    {

        try
        {
//            System.out.println("Submit Button Pressed");

            // Gets data from the TextFields
            getTxtFieldData();

            // Verifies payment by calling creditCheck method
            boolean bool = checkout.creditCheck(fName, mName, lName, creditNum, cvc, expDate);

            if (bool) // if payment was verified
            {
                // Pushes reservations to the database
                reservation.pushReservations();

                // Closes the current scene
                Node node = (Node) event.getSource();
                Stage primaryStage = (Stage) node.getScene().getWindow();
                primaryStage.hide();

                // Loads and opens the Checkout scene
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("confirmation_view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1243, 720);

                // Creates an instance of the CheckoutController
                ConfirmationController confirmationController = fxmlLoader.getController();

                //Passes the reservation object to the ConfirmationControllerr
                confirmationController.setReservation(reservation);
                confirmationController.loadFlightTable();
                confirmationController.loadPaxTable();

                // Opens the Checkout scene
                Stage stage = new Stage();
                stage.setTitle("Confirmation");
                stage.setScene(scene);
                stage.show();

                // Clears the reservations list
                reservation.flushReservations();


            }
            else // Otherwise if payment cannot be verified
            {
                // An error alert is set
                Alerts.setErrorAlert("Error", "Payment not accepted, please try again");
            }


        }
        catch (NumberFormatException e)
        {
            Alerts.setErrorAlert("Error", "Payment not accepted, please try again");
            e.getStackTrace();
        } catch (IOException | SQLException e)
        {
            System.out.println("Error while connecting to db");
            e.getStackTrace();
        }


    }

    public void getTxtFieldData()
    {
        fName = fnameTxt.getText();
        mName = mNameTxt.getText();
        lName = lNameTxt.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        expDate = YearMonth.parse(expirationDate.getText(),formatter);
        cvc = Long.parseLong(cvcTxt.getText());
        creditNum = Long.parseLong(creditNumTxt.getText());
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

        // Closes the current scene
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.hide();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("searchFlights_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        Stage stage = new Stage();
        stage.setTitle("Search Flights");
        stage.setScene(scene);
        stage.show();

        // Clears the reservations list
        reservation.flushReservations();

    }
}
