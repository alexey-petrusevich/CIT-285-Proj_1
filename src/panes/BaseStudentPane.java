/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Aliaksei
 */
public abstract class BaseStudentPane {
    
    
    
    // return a pane with all elements
    protected static Pane getBasePane(){
        // pane to hold all elements
        StaticData.gridPane = new GridPane();
        StaticData.gridPane.setVgap(10);
        StaticData.gridPane.setHgap(70);
        StaticData.gridPane.setTranslateX(40);
        StaticData.gridPane.setTranslateY(10);
        // add all elements onto the 
        StaticData.gridPane.add(StaticData.SSN_LABEL, 0, 0);
        StaticData.gridPane.add(StaticData.ssnTF, 1, 0);
        StaticData.gridPane.add(StaticData.FIRST_NAME_LABEL, 0, 1);
        StaticData.gridPane.add(StaticData.firstNameTF, 1, 1);
        StaticData.gridPane.add(StaticData.MI_LABEL, 0, 2);
        StaticData.gridPane.add(StaticData.miTF, 1, 2);
        StaticData.gridPane.add(StaticData.LAST_NAME_LABEL, 0, 3);
        StaticData.gridPane.add(StaticData.lastNameTF, 1, 3);
        StaticData.gridPane.add(StaticData.STREET_ADDRESS_LABEL, 0, 4);
        StaticData.gridPane.add(StaticData.streetAddressTF, 1, 4);
        StaticData.gridPane.add(StaticData.CITY_LABEL, 0, 5);
        StaticData.gridPane.add(StaticData.cityTF, 1, 5);
        StaticData.gridPane.add(StaticData.STATE_LABEL, 0, 6);
        StaticData.gridPane.add(StaticData.STATES_COMBO_BOX, 1, 6);
        StaticData.gridPane.add(StaticData.ZIP_LABEL, 0, 7);
        StaticData.gridPane.add(StaticData.zipTF, 1, 7);
        // return gridpane
        return StaticData.gridPane;
    }
    // getters
    //---------------------------------------------
    public String getSSN(){
        return StaticData.ssnTF.getText();
    }
    public String getFirstName(){
        return StaticData.firstNameTF.getText();
    }
    public String getMI(){
        return StaticData.miTF.getText();
    }
    public String getLastName(){
        return StaticData.lastNameTF.getText();
    }
    public String getStreetAddress(){
        return StaticData.streetAddressTF.getText();
    }
    public String getCity(){
        return StaticData.cityTF.getText();
    }
    public String getState(){
        return StaticData.STATES_COMBO_BOX.getValue();
    }
    public String getZip(){
        return StaticData.zipTF.getText();
    }
    //---------------------------------------------
    //setters
    //---------------------------------------------
    public void setSSN(String ssn){
        StaticData.ssnTF.setText(ssn);
    }
    public void setFirstName(String firstName){
        StaticData.firstNameTF.setText(firstName);
    }
    public void setMI(String mi){
        StaticData.miTF.setText(mi);
    }
    public void setLastName(String lastName){
        StaticData.lastNameTF.setText(lastName);
    }
    public void setStreetAddress(String streetAddress){
        StaticData.streetAddressTF.setText(streetAddress);
    }
    public void setCity(String city){
        StaticData.cityTF.setText(city);
    }
    public void setState(String state){
        // if states list contains value of state, then select it
        if (StaticData.STATES_LIST.contains(state)){
            StaticData.STATES_COMBO_BOX.setValue(state);
        } else {
            // else set the first state in the list
            StaticData.STATES_COMBO_BOX.setValue(StaticData.STATES_LIST.get(0));
        }
    }
    public void setZip(String zip){
        StaticData.zipTF.setText(zip);
    }
    //---------------------------------------------
}
