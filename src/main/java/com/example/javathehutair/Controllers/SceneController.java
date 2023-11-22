package com.example.javathehutair.Controllers;

import com.example.javathehutair.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * The purpose of this class is to provide a method for easily switching scenes
 * @author Teo Dominguez
 * @version 1.0
 * November 21, 2023
 */
public class SceneController {
        /**
         * Method to switch to a new scene, set the title, and close the previous scene
         * @param event ActionEvent
         * @param fxml String
         * @param title String
         * @throws IOException
         */
    public void switchScene(ActionEvent event, String fxml, String title) throws IOException{
            //closing the current stage
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.close();
            //loading desired fxml
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 1243, 720);
            //opening stage and setting title
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
    }
}
