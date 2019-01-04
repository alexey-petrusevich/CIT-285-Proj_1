/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Aliaksei
 */
public abstract class WelcomePane {
    /**
     * Method returns an instance of pane used in the welcome (main) page.
     * @return 
     */
    public static Pane getWelcomePane(){
        // borderPane for menu bar and main pane
        StaticData.borderPane = new BorderPane();
        // hbox for centering menu bar
        StaticData.borderPane.setTop(MenuBarPane.getMenuBar());
        // label for welcome message
        Label welcomeLabel = new Label();
        welcomeLabel.setText("Welcome to BOOLA BOOLA University");
        welcomeLabel.setFont(Font.font("Times", FontWeight.BOLD, 20));
        StaticData.borderPane.setCenter(welcomeLabel);
        // return borderPane
        return StaticData.borderPane;
    }
}
