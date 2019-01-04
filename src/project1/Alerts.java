/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Aliaksei
 */

public class Alerts {
    /**
     * Method displays an alert with a message passed as a string parameter.
     * @param message message to be displayed.
     */
    public static void alertError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void successAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("Success!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void quitAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Quit program");
        alert.setContentText("Are you sure?");
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK){
            // if ok button pressed save progress
            System.exit(0);
            
        }
    }
    
}
