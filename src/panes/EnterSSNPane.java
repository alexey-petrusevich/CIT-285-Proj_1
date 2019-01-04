/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import exceptions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project1.Alerts;
import project1.Scenes;

/**
 *
 * @author Aliaksei
 */
public class EnterSSNPane {
    public static Pane getEnterSSNPane(){
        // vbox for all elements
        StaticData.vBox = new VBox(10);
        // label asking to enter SSN
        Label labelSSN = new Label("Enter SSN: ");
        // hbox for label and textfield
        StaticData.hBox = new HBox(10);
        StaticData.hBox.setAlignment(Pos.CENTER);
        StaticData.hBox1 = new HBox(10);
        StaticData.hBox1.setAlignment(Pos.CENTER);
        // add elements onto hboxes
        StaticData.hBox.getChildren().addAll(labelSSN, StaticData.ssnTF);
        StaticData.hBox1.getChildren().addAll(StaticData.SSN_OK_BUTTON, StaticData.CANCEL_BUTTON);
        // add hboxes to vbox
        StaticData.vBox.setAlignment(Pos.CENTER);
        StaticData.vBox.getChildren().addAll(StaticData.hBox, StaticData.hBox1);
        // add listener onto OK button
        StaticData.SSN_OK_BUTTON.setOnAction(ov->{
            try{
                // check SSNtf length
                if (StaticData.ssnTF.getLength() < StaticData.SSN_LENGTH){
                    throw new SSNException("The SSN must be " + StaticData.SSN_LENGTH + " digits long!");
                } else {
                    // check every character if it is not a digit
                    for (int i = 0; i < StaticData.ssnTF.getLength(); i++){
                        if (!Character.isDigit(StaticData.ssnTF.getText().charAt(i))){
                            throw new SSNException("SSN must contain digits only!");
                        }
                    }
                }
                // send a query to DB and check if entry exists, and throw exception if ResultSet returns null = entry not found
                // 1) conenct driver
                Class.forName(StaticData.DRIVER);
                // 2) connect to DB
                // try with parameters (autoclose connection)
                try(Connection connection = DriverManager.getConnection(StaticData.DATABASE, StaticData.LOGIN, StaticData.PASSWORD)){
                    // 3) call query (create statement)
                    PreparedStatement prepStatement = connection.prepareStatement("SELECT * "
                            + "FROM students "
                            + "WHERE SSN = ?;");
                    prepStatement.setString(1, StaticData.ssnTF.getText());
                    prepStatement.execute();
                    // submit query (search for a student with SSN provided by SSNtf)
                    ResultSet resultSet = prepStatement.getResultSet();
                    if (!resultSet.next()) {
                        throw new EntryNotFoundException("Student with SSN " + 
                                StaticData.ssnTF.getText() + " not found!");
                    } else {
                        // if student is found populate textfield with data from the student
                        // prevent user from updating selected students ssn
                        StaticData.ssnTF.setEditable(false);
                        // execute query again
                        resultSet.beforeFirst();
                        while (resultSet.next()){
                            StaticData.firstNameTF.setText(resultSet.getString("fname"));
                            StaticData.miTF.setText(resultSet.getString("mi"));
                            StaticData.lastNameTF.setText(resultSet.getString("lname"));
                            StaticData.streetAddressTF.setText(resultSet.getString("address"));
                            StaticData.cityTF.setText(resultSet.getString("city"));
                            StaticData.STATES_COMBO_BOX.getSelectionModel().select(resultSet.getString("state"));
                            StaticData.zipTF.setText(resultSet.getString("zip"));
                            if (resultSet.getInt("hasImmunization") == 1){
                                StaticData.immunizedCheckBox.setSelected(true);
                            } else {
                                StaticData.immunizedCheckBox.setSelected(false);
                            }
                            
                            // if matriculated student is selected, set additional tfs and checkboxes (if any)
                            // in case if user is switching from matriculated to non matriculated or vice versa
                            if (StaticData.matriculatedSelected == true) {
                                // mat year
                                StaticData.MATRICULATION_YEARS_COMBO_BOX.getSelectionModel().select(resultSet.getString("matYear"));
                                // degree
                                StaticData.DEGREES_COMBO_BOX.getSelectionModel().select(resultSet.getString("degree"));
                                // hs diploma
                                if (resultSet.getInt("hasDiploma") == 1){
                                    StaticData.hsDiplomaCheckBox.setSelected(true);
                                } else {
                                  // hs diploma is not selected, empty else case (could be removed)
                                  // included for clarity 
                                }
                                
                            }
                        }
                        
                        
                        StaticData.editModeSelected = true;
                        // load corresponding scene
                        StaticData.loadScene(ov, StaticData.matriculatedSelected == true ?
                                Scenes.getMatriculatedScene() : Scenes.getNonMatriculatedScene());
                        
                    }
                }
                
                
            // multicatch?? (suggested by netbeans)
            } catch(SSNException | EntryNotFoundException e){
                Alerts.alertError(e.getErrorMessage());
            } catch (ClassNotFoundException ex) {
                Alerts.alertError("JDBC driver not found!");
                System.exit(1);
            } catch (SQLException ex) {
                Alerts.alertError("Database not found!");
                System.exit(1);
            }
            
            
            
                
        });
        // return vbox 
        return StaticData.vBox;
    }
}
