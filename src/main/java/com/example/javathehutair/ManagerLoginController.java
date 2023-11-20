package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ManagerLoginController {
    /**
     * Local Variables
     */
    @FXML
    private Button searchFlightsButton;
    @FXML
    private Button cancelFlightsButton;
    @FXML
    private Button aboutUsButton;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private Button loginButton;
    private String username, password;

    private SceneController sceneController = new SceneController();

    /**
     * Method executes event after login click action
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void clickLogin(ActionEvent event) throws SQLException, IOException {
        Manager managerHelper = new Manager();
        username = usernameTxt.getText();
        password = passwordTxt.getText();

        if(managerHelper.loginChecker(username, password)){
            // open next manager scene to display what the manager can see/d
            sceneController.switchScene(event, "manager_View.fxml", "Manager View");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(("Invalid Login Credentials"));
            alert.setContentText("Username or Password is incorrect");
            alert.showAndWait();
        }
    }

    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");
    }
    @FXML
    public void clickCancelFlights(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "cancelFlights_view.fxml", "Cancel Flights");
    }
    @FXML
    public void clickAboutUs(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "aboutUs_view.fxml", "About Us");
    }

}