/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Class contains method getNewUpdate pane that returns an instance of a pane 
 * with three buttons: New entry, Update entry, and Cancel.
 * @author Aliaksei
 */
public abstract class NewUpdatePane {
    
    /**
     * Method returns a pane with three control button for entry option selection.
     * @return 
     */
    public static Pane getNewUpdatePane(){
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        StaticData.NEW_ENTRY_BUTTON.setMinWidth(100);
        StaticData.UPDATE_ENTRY_BUTTON.setMinWidth(100);
        StaticData.CANCEL_BUTTON.setMinWidth(100);
        hBox.getChildren().addAll(StaticData.NEW_ENTRY_BUTTON, StaticData.UPDATE_ENTRY_BUTTON);
        hBox.setSpacing(20);
        vBox.getChildren().addAll(hBox, StaticData.CANCEL_BUTTON);
        vBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        // add listeners on buttons
        // return pane
        return vBox;
    }
}
