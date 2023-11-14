/**
 * CSUN FALL 23 Java The Hut Airlines
 * This is a Controller Class for the checkout_view.fxml
 * @author Jonathan Alcantar, October 24, 2023
 * @version 1.0
 */

package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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


    /**
     * Local Variables
     */
    private String fName, lName, mName;
    private long cvc, creditNum;
    private YearMonth expDate;
    private Checkout checkout = new Checkout();
    private Reservation reservation;
    private String title = "Error";
    private String contentText = "Payment not accepted, please try again";
    private FlightCabin flightCabin = new FlightCabin();
    private SceneController sceneController = new SceneController();

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
    public void setSummary() throws SQLException {
        flightIDLabel.setText("Flight ID: " + reservation.getReservationList().get(0).getFlightID());

        // TODO: Methods to get the departure city and arrival city
//        fromToLabel.setText("From: " + reservation.getDeparture() + " " + "To: " + reservation.getArrival());

        noOfPaxLabel.setText("Number of Passengers: " + reservation.getReservationSize());
        totalLabel.setText("Total Price: $" + flightCabin.totalReservationPrice(reservation.getReservationList()) + ".00");
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
     * Method sets an Error Alert
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
     * Method verifies the payment after user clicks the submit button
     * @param event
     */

    @FXML
    void clickSubmitButton(ActionEvent event)throws NumberFormatException
    {

        try
        {
            System.out.println("Submit Button Pressed");
            // Gets data from the TextFields
            fName = fnameTxt.getText();
            mName = mNameTxt.getText();
            lName = lNameTxt.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            expDate = YearMonth.parse(expirationDate.getText(),formatter);
            cvc = Long.parseLong(cvcTxt.getText());
            creditNum = Long.parseLong(creditNumTxt.getText());
            // Verifies payment by calling creditCheck method
            boolean bool = checkout.creditCheck(fName, mName, lName, creditNum, cvc, expDate);

            if (bool) // if payment was verified
            {
                // Pushes reservations to the database
                reservation.pushReservations();

                // Clears the reservations list
                reservation.flushReservations();

                // Closes the current scene
                sceneController.switchScene(event,"confirmation_view.fxml","Confirmation");

            }
            else // Otherwise if payment cannot be verified
            {
                // An error alert is set
                setErrorAlert(title, contentText);
            }


        }
        catch (NumberFormatException e)
        {
            setErrorAlert(title, contentText);
            e.getStackTrace();
        } catch (IOException | SQLException e)
        {
            System.out.println("Error while connecting to database");
            e.getStackTrace();
        }
    }
}
