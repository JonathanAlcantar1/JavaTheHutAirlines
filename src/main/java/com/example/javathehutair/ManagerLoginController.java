package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ManagerLoginController {

    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private Button loginButton;
    private String username, password;

    @FXML
    void clickLogin(ActionEvent event) throws SQLException {
        Manager managerHelper = new Manager();
        username = usernameTxt.getText();
        password = passwordTxt.getText();

        if(managerHelper.loginChecker(username, password)){
            // open next manager scene to display what the manager can see/do
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(("Success"));
            alert.setContentText("Username and password were found");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(("Invalid Login Credentials"));
            alert.setContentText("Username or Password is incorrect");
            alert.showAndWait();
        }
    }


}