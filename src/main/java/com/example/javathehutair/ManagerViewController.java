package com.example.javathehutair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerViewController {
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