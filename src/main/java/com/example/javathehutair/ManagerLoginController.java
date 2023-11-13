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

    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private Button loginButton;
    private String username, password;

    @FXML
    void clickLogin(ActionEvent event) throws SQLException, IOException {
        Manager managerHelper = new Manager();
        username = usernameTxt.getText();
        password = passwordTxt.getText();

        if(managerHelper.loginChecker(username, password)){
            // open next manager scene to display what the manager can see/do

            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("manager_View.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
            Stage stage = new Stage();
            stage.setTitle("Manager View");
            stage.setScene(scene);
            stage.show();


        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(("Invalid Login Credentials"));
            alert.setContentText("Username or Password is incorrect");
            alert.showAndWait();
        }
    }

    @FXML
    public void clickCancelFlights(ActionEvent event) throws IOException {
        //closing the current stage
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
        //loading search flights fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("cancelFlights_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        //opening search flights stage
        Stage stage = new Stage();
        stage.setTitle("Cancel Flights");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void clickManagerLogin(ActionEvent event) throws IOException {
        //closing the current stage
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
        //loading search flights fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("managerLogin_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        //opening search flights stage
        Stage stage = new Stage();
        stage.setTitle("Manager Login");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException{
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
        //loading search flights fxml
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("searchFlights_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
        //opening search flights stage
        Stage stage = new Stage();
        stage.setTitle("Manager Login");
        stage.setScene(scene);
        stage.show();
    }


}