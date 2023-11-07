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
import java.util.ArrayList;
import java.util.List;

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
    private int cvc, creditNum;
    private Checkout checkout = new Checkout();
    private Reservation reservation;
    private String title = "Error";
    private String contentText = "Payment not accepted, please try again";
    private FlightCabin flightCabin = new FlightCabin();

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
        flightIDLabel.setText("Flight ID: " + reservation.getReservations().get(0).getFlightID());

        // TODO: Methods to get the departure city and arrival city
//        fromToLabel.setText("From: " + reservation.getDeparture() + " " + "To: " + reservation.getArrival());

        noOfPaxLabel.setText("Number of Passengers: " + reservation.getReservationSize());
        totalLabel.setText("Total Price: $" + flightCabin.totalReservationPrice(reservation.getReservations()) + ".00");
    }


    /**
     * Method checks to see if a string is blank
     * @param str
     * @return true if blank, otherwise return false
     */
    public Boolean checkIsStringBlank(String str)
    {
        if(str.isBlank())
            return true;
        return false;

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
            cvc = Integer.parseInt(cvcTxt.getText());
            creditNum = Integer.parseInt(creditNumTxt.getText());

            // Verifies payment by calling creditCheck method
            Boolean bool = checkout.creditCheck(fName, mName, lName, cvc, creditNum);

            if (bool) // if payment was verified
            {
                // Pushes reservations to the database
                reservation.pushReservations();

                // Clears the reservations list
                reservation.flushReservations();

                // Closes the current scene
                Node node = (Node) event.getSource();
                Stage primaryStage = (Stage) node.getScene().getWindow();
                primaryStage.hide();

                // Loads and opens the Checkout scene
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("confirmation_view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1243, 720);

                // Opens the Checkout scene
                Stage stage = new Stage();
                stage.setTitle("Confirmation");
                stage.setScene(scene);
                stage.show();

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
