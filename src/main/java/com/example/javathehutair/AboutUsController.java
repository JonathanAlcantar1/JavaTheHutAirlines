package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;


public class AboutUsController {

    @FXML
    private Button searchFlightsButton;
    @FXML
    private Button cancelFLightsButton;
    @FXML
    private Button managersButton;
    private SceneController sceneController = new SceneController();

    public void clickSearchFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");
    }

    public void clickCancelFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "cancelFlights_view.fxml", "Cancel Flights");
    }

    public void clickManagersButton(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "managerLogin_view.fxml", "Manager Login");
    }
}
