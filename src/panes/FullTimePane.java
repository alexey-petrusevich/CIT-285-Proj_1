/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Aliaksei
 */
public class FullTimePane extends BaseRegPane{
    
    
    public static Pane getFullTimePane(){
        // vbox for all elements
        VBox vBox = new VBox(10);
        // set the name of the registration label
        StaticData.headerLabel.setText("Full-time registration");
        // hbox for credits label and textfield
        HBox creditsBox = new HBox(30);
        creditsBox.setTranslateX(40);
        creditsBox.getChildren().addAll(StaticData.NUMBER_OF_CREDITS_LABEL, StaticData.numberOfCreditsTF);
        // add all elements onto vbox
        vBox.getChildren().addAll(BaseRegPane.getRegistrationTop(), creditsBox, BaseRegPane.getRegistrationBottom());
        // return vbox
        return vBox;
    }
}
