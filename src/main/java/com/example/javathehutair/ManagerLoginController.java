/**
 * ManagerLoginController
 * November 7, 2023
 * @author Ricardo Ramos
 *
 * Most algorithms within this class uses SceneController method from the SceneController class to switch between
 *      scenes given the specified FXML scene. The purpose of the class itself is for the Manager Login Scene, where
 *      method clickLogin verifies the user-inputted information into the Manager.class method to verify input information
 *      is correct.
 *
 * @version 1.0
 */

package com.example.javathehutair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class ManagerLoginController {
    /**
     * Local Variables
     */
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    private String username, password;
    private SceneController sceneController = new SceneController();

    /**
     * Method executes event after login click action
     * @param event Action event input parameter
     * @throws SQLException SQLException
     * @throws IOException IOException
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

    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Search Flight.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickSearchFlights(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "searchFlights_view.fxml", "Search Flights");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to Cancel Flights.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickCancelFlights(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "cancelFlights_view.fxml", "Cancel Flights");
    }
    /**
     * Function executes scene change from ActionEvent via on-click button on FXML. Scenes Changes to About Us Info.
     * @param event
     * @throws IOException
     */
    @FXML
    public void clickAboutUs(ActionEvent event) throws IOException{
        sceneController.switchScene(event, "aboutUs_view.fxml", "About Us");
    }

}