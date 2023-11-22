package com.example.javathehutair.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

/**
 * The purpose of this class is as a controller for the about us page
 * @author Teo Dominguez
 * @version 1.0
 * November 21, 2023
 */
public class AboutUsController {
    /**
     * Local class variables
     */
    @FXML
    private Button searchFlightsButton;
    @FXML
    private Button cancelFLightsButton;
    @FXML
    private Button managersButton;
    private SceneController sceneController = new SceneController();

    /**
     * Navigates to the search flights page when search flights is clicked
     * @param event
     * @throws IOException
     */
    public void clickSearchFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");
    }

    /**
     * Navigates to the cancel flights page when cancel flights is clicked
     * @param event
     * @throws IOException
     */
    public void clickCancelFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "cancelFlights_view.fxml", "Cancel Flights");
    }

    /**
     * Navigates to the manager login page when managers is clicked
     * @param event
     * @throws IOException
     */
    public void clickManagersButton(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "managerLogin_view.fxml", "Manager Login");
    }
}
