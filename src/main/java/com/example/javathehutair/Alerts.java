package com.example.javathehutair;

import javafx.scene.control.Alert;

public class Alerts
{
    /**
     * Method sets an Error Alert
     * @param title
     * @param contentText
     */
    public static void setErrorAlert(String title, String contentText)
    {
        // if user doesn't select a cabin type program alerts user to try search again
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();

    }

    /**
     * Method sets a Success Alert
     * @param title
     * @param contentText
     */
    public static void setSuccessAlert(String title, String contentText)
    {
        // if user doesn't select a cabin type program alerts user to try search again
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();

    }
}
