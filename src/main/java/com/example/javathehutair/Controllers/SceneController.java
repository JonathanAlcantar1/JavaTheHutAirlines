package com.example.javathehutair.Controllers;

import com.example.javathehutair.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
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
