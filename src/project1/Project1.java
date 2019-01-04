/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Aliaksei
 */
public class Project1 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("BOOLA BOOLA University");
        primaryStage.setScene(Scenes.getWelcomeScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /** Main method.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
