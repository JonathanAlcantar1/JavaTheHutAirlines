package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PassengerFlightCheckerController {
    private SceneController sceneController = new SceneController();
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
}
