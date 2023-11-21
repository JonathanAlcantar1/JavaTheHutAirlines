/**
 * ManagerViewController
 * November 13 / 14, 2023
 * @author Ricardo Ramos
 *
 * Most algorithms within this class uses SceneController method from the SceneController class to switch between
 *      scenes given the specified FXML scene. The purpose of the class itself is for the Manager View Scene to view
 *      the manager menu and its redirect buttons.
 *
 * @version 1.0
 */
package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ManagerViewController {
    private SceneController sceneController = new SceneController();
    /**
     * Method executes button event
     * @param event
     * @throws IOException
     */
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Cancel Flight.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickCancelFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "cancelFlights_view.fxml", "Cancel Flights");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Manager Login.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickManagerLogin(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "managerLogin_view.fxml", "Manager Login");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Search Flight.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");

    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Flight passenger checker.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickPassengerFlightChecker(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "passengerFlightsChecker_View.fxml", "Passenger Flight Checker");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Passenger history checker.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickSearchPsngrHistory(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "passengerFlightsHistoryChecker_View.fxml", "Passenger History Checker");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Passenger Reservation Checker.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickSearchPsngrRes(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "passengerReservationChecker_View.fxml", "Passenger Reservation Checker");

    }
}
