/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import exceptions.AddressException;
import exceptions.CityException;
import exceptions.DuplicateEntryException;
import exceptions.FNameException;
import exceptions.LNameException;
import exceptions.MIException;
import exceptions.SSNException;
import exceptions.StateException;
import exceptions.ZipException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project1.Alerts;
import project1.Scenes;

/**
 *
 * @author Aliaksei
 */
public abstract class NonMatriculatedPane extends BaseStudentPane{
    
    public static Pane getNonMatriculatedPane(){
        // set text of header label to matriculated
        StaticData.headerLabel.setText("Non-Matriculated student admission");
        // vbox for all elements
        VBox vBox = new VBox(20);
        vBox.getChildren().add(StaticData.headerLabel);
        StaticData.headerLabel.setTranslateX(40);
        // two hboxes to hold label and checkbox, and two buttons
        HBox labelHBox = new HBox();
        labelHBox.getChildren().addAll(StaticData.IMMUNIZED_LABEL, StaticData.immunizedCheckBox, StaticData.HINT_LABEL);
        labelHBox.setSpacing(70); 
        labelHBox.setTranslateX(40);
        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(StaticData.OK_BUTTON, StaticData.CANCEL_BUTTON);
        StaticData.OK_BUTTON.setMinWidth(75);
        StaticData.CANCEL_BUTTON.setMinWidth(75);
        buttonHBox.setSpacing(40);
        buttonHBox.setTranslateX(70);
        // add hboxes to vbox
        vBox.getChildren().addAll(BaseStudentPane.getBasePane(), labelHBox, buttonHBox);
        StaticData.HINT_LABEL.setTranslateX(40);
        // add listener to OK button
        // set a listener on matriculated/nonmatriculated OK button
            StaticData.OK_BUTTON.setOnAction(ov->{
                try{
                    // submit a query to check if student with that ssn already exists
                    // 1) connect driver
                Class.forName(StaticData.DRIVER);
                // 2) connect DB
                try(Connection connection = DriverManager.getConnection(StaticData.DATABASE, StaticData.LOGIN, StaticData.PASSWORD)){
                    // 3) create statement
                    PreparedStatement prepStatement = connection.prepareStatement(""
                            + "SELECT * FROM students "
                            + "WHERE ssn = ?;");
                    prepStatement.setString(1, StaticData.ssnTF.getText());
                    prepStatement.execute();
                    ResultSet resultSet = prepStatement.getResultSet();
                    if(resultSet.next() && StaticData.editModeSelected == false) {
                        throw new DuplicateEntryException("Student with SSN " + StaticData.ssnTF.getText() + ""
                                + " already exists!");
                    }
                }   
                    // check length of ssn
                    if (StaticData.ssnTF.getText().length() != 9) {
                        throw new SSNException("The SSN must be " + StaticData.SSN_LENGTH + " digits long!");
                    }
                    // check if user entered at least one character
                    for (int i = 0; i < StaticData.ssnTF.getText().length(); i++){
                        if (!Character.isDigit(StaticData.ssnTF.getText().charAt(i))) {
                            throw new SSNException("SSN must contain digits only!");
                        }
                    }
                    // check first name text field
                    // check fname length (for DB synchronization)
                    if (StaticData.firstNameTF.getText().length() == 0 ||
                            StaticData.firstNameTF.getText().length() > StaticData.FNAME_LENGTH){
                        throw new FNameException("The length of first name must be between 0 and " + StaticData.FNAME_LENGTH + "!");
                    }
                    // check if any character in fname is not an alphabetic character
                    for (int i = 0; i < StaticData.firstNameTF.getText().length(); i++){
                        if (!Character.isAlphabetic(StaticData.firstNameTF.getText().charAt(i))){
                            throw new FNameException("First name must contain only characters!");
                        }
                    }
                    // check mi text field
                    if (StaticData.miTF.getText().length() != 1){
                        throw new MIException("Middle initials field must have " + StaticData.MI_LENGTH + " character!");
                    }
                    else if (!Character.isAlphabetic(StaticData.miTF.getText().charAt(0))){ // if value entered is not an alphabetic character
                        throw new MIException("Middle initials must contain characters only!");
                    }
                    // check last name field
                    // check lname length (for DB synchronization)
                    if (StaticData.lastNameTF.getText().length() == 0 ||
                            StaticData.lastNameTF.getText().length() > StaticData.LNAME_LENGTH){
                        throw new LNameException("The length of last name must be between 0 and  " + StaticData.LNAME_LENGTH + "!");
                    }
                    // check if any character in lname is not an alphabetic character
                    for (int i = 0; i < StaticData.lastNameTF.getText().length(); i++){
                        if (!Character.isAlphabetic(StaticData.lastNameTF.getText().charAt(i))){
                            throw new LNameException("Last name must contain only characters!");
                        }
                    }
                    // check address field
                    if (StaticData.streetAddressTF.getText().length() == 0 ||
                            StaticData.streetAddressTF.getText().length() > StaticData.ADDRESS_LENGTH){
                        throw new AddressException("The length of the address must be between 0 and " + StaticData.ADDRESS_LENGTH + "!");
                    }
                    // check city field
                    // check length
                    if (StaticData.cityTF.getText().length() == 0 ||
                            StaticData.cityTF.getText().length() > StaticData.CITY_LENGTH){
                        throw new CityException("The length of the city must be between 0 and " + StaticData.CITY_LENGTH + "!");
                    }
                    // check if any nonarithmetic characters present
                    for (int i = 0; i < StaticData.cityTF.getText().length(); i++){
                        if (!Character.isAlphabetic(StaticData.cityTF.getText().charAt(i))){
                            // if character is a space/ skip it
                            if (Character.isSpaceChar(StaticData.cityTF.getText().charAt(i))){
                                continue;
                            }
                            throw new CityException("The name of the city must contain "
                                    + "alphabetic characters only!");
                        }
                    }
                    // check state selection
                    if (StaticData.STATES_COMBO_BOX.getSelectionModel().getSelectedItem() == null){
                        throw new StateException("You must select a state of your residence!");
                    }
                    // check zip field
                    // check the length of zip textfield
                    if (StaticData.zipTF.getText().length() == 0 ||
                            StaticData.zipTF.getText().length() > StaticData.ZIP_LENGTH){
                        throw new ZipException("The length of the zip must be between 0 and " + StaticData.ZIP_LENGTH + "!");
                    }
                    // check if every symbol in zip text box is a digit value
                    for (int i = 0; i < StaticData.zipTF.getText().length(); i++){
                        if (!Character.isDigit(StaticData.zipTF.getText().charAt(i))){
                            throw new ZipException("ZIP must contain digits only!");
                        }
                    }

                    // if no exceptions thrown, add student to the database.
                    // if student exists, stdent info will be updated instead
                    StaticData.addStudent(StaticData.ssnTF.getText(), StaticData.firstNameTF.getText(),
                            StaticData.miTF.getText(), StaticData.lastNameTF.getText(), 
                            StaticData.streetAddressTF.getText(), StaticData.cityTF.getText(),
                            StaticData.STATES_COMBO_BOX.getSelectionModel().getSelectedItem(), 
                            StaticData.zipTF.getText(), StaticData.dateTF.getText(), 
                            "", "", 0, StaticData.immunizedCheckBox.isSelected() == true ? 1 : 0);
                    // display success alert
                    Alerts.successAlert("Admitted!");
                    // unlock ssntf
                    StaticData.ssnTF.setEditable(true);
                    // load main window
                    StaticData.loadScene(ov, Scenes.getWelcomeScene());
                    // catch blocks
                } catch(SSNException e){
                    Alerts.alertError(e.getErrorMessage());
                } catch(FNameException e){
                    Alerts.alertError(e.getErrorMessage());
                } catch(MIException e){
                    Alerts.alertError(e.getErrorMessage());
                } catch(LNameException e){
                    Alerts.alertError(e.getErrorMessage());
                } catch(AddressException e){
                    Alerts.alertError(e.getErrorMessage());
                } catch(CityException e){
                    Alerts.alertError(e.getErrorMessage());
                } catch(StateException e){
                    Alerts.alertError(e.getErrorMessage());
                } catch(ZipException e){
                    Alerts.alertError(e.getErrorMessage());
                } catch (ClassNotFoundException ex) { 
                    Alerts.alertError("JDBC driver not found!");
                    System.exit(1);
                } catch (SQLException ex) { 
                        Alerts.alertError("Unsuccessfull SQL query!");
                        ex.printStackTrace();
                } catch (DuplicateEntryException ex) {
                        Alerts.alertError(ex.getErrorMessage());
                }
            });
        // return vBox
        return vBox;
    }
}
