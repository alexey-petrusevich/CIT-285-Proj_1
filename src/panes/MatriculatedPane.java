/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import exceptions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project1.Alerts;
import project1.Scenes;

/**
 *
 * @author Aliaksei
 */
public abstract class MatriculatedPane extends BaseStudentPane{
    
    // add all elements and return as one pane
    public static Pane getMatriculatedPane(){
        // set text of header label to matriculated
        StaticData.headerLabel.setText("Matriculated student admission");
        // pane to hold all elements
        VBox vBox = new VBox(10);
        StaticData.headerLabel.setTranslateX(40);
        vBox.getChildren().add(StaticData.headerLabel);
        // pane to hold two buttons
        HBox hBox = new HBox(10);
        // pane to hold control elements of matriculated student
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(10);
        gridPane.setTranslateX(40);
        gridPane.setTranslateY(10);
        // add all control elements onto gridPane
        gridPane.add(StaticData.DATE_LABEL, 0, 0);
        StaticData.dateTF.setEditable(false);
        // get current date
        LocalDateTime currentDate = LocalDateTime.now();
        StaticData.dateTF.setText(DateTimeFormatter.ofPattern("MM/dd/yyyy").format(currentDate));
        gridPane.add(StaticData.dateTF, 1, 0);
        StaticData.dateTF.setMaxWidth(100);
        gridPane.add(StaticData.MATRICULATION_YEAR_LABEL, 0, 1);
        gridPane.add(StaticData.MATRICULATION_YEARS_COMBO_BOX, 1, 1);
        gridPane.add(StaticData.DEGREE_LABEL, 0, 2);
        gridPane.add(StaticData.DEGREES_COMBO_BOX, 1, 2);
        StaticData.DEGREES_COMBO_BOX.setMaxWidth(150);
        gridPane.add(StaticData.HS_DIPLOMA_LABEL, 0, 3);
        gridPane.add(StaticData.hsDiplomaCheckBox, 1, 3);
        gridPane.add(StaticData.IMMUNIZED_LABEL, 0, 4);
        gridPane.add(StaticData.immunizedCheckBox, 1, 4);
        // add two buttons on hbox
        hBox.getChildren().addAll(StaticData.OK_BUTTON, StaticData.CANCEL_BUTTON);
        
        // set a listener on matriculated/nonmatriculated OK button
        StaticData.OK_BUTTON.setOnAction(ov->{
            try{
                // submit a query checking if student with such ssn already exists
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
                    throw new ZipException("The length of the city must be between 0 and " + StaticData.ZIP_LENGTH + "!");
                }
                // check if every symbol in zip text box is a digit value
                for (int i = 0; i < StaticData.zipTF.getText().length(); i++){
                    if (!Character.isDigit(StaticData.zipTF.getText().charAt(i))){
                        throw new ZipException("ZIP must contain digits only!");
                    }
                }
                // check matriculation year selection
                if (StaticData.MATRICULATION_YEARS_COMBO_BOX.getSelectionModel().getSelectedItem() == null){
                    throw new MatriculationException("You must select a matriculation year!");
                }
                // check degree selection
                if (StaticData.DEGREES_COMBO_BOX.getSelectionModel().getSelectedItem() == null){
                    throw new DegreeException("You must select a degree!");
                }
                // check highschool diploma selection
                if (!StaticData.hsDiplomaCheckBox.isSelected()){
                    throw new HSDiplomaException("You must present your highschool diploma!");
                }
                // check immunization selection
                if (!StaticData.immunizedCheckBox.isSelected()) {
                    throw new ImmunizationException("You must present your immunization records!");
                }
                // if no exceptions thrown, add student to the database.
                // if student exists, stdent info will be updated instead
                StaticData.addStudent(StaticData.ssnTF.getText(), StaticData.firstNameTF.getText(),
                        StaticData.miTF.getText(), StaticData.lastNameTF.getText(), 
                        StaticData.streetAddressTF.getText(), StaticData.cityTF.getText(),
                        StaticData.STATES_COMBO_BOX.getSelectionModel().getSelectedItem(), 
                        StaticData.zipTF.getText(), StaticData.dateTF.getText(), 
                        StaticData.MATRICULATION_YEARS_COMBO_BOX.getSelectionModel().getSelectedItem(),
                        StaticData.DEGREES_COMBO_BOX.getSelectionModel().getSelectedItem(), 1, 1);
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
            } catch(MatriculationException e){
                Alerts.alertError(e.getErrorMessage());
            } catch(DegreeException e){
                Alerts.alertError(e.getErrorMessage());
            } catch(HSDiplomaException e){
                Alerts.alertError(e.getErrorMessage());
            } catch(ImmunizationException e){
                Alerts.alertError(e.getErrorMessage());
            } catch (ClassNotFoundException ex) {
                Alerts.alertError("JDBC driver not found!");
                System.exit(1);
            } catch (SQLException ex){
                Alerts.alertError("Unsuccessulf SQL query!");
                ex.printStackTrace();
            } catch (DuplicateEntryException ex) {
                Alerts.alertError(ex.getErrorMessage());
            }
        });
        
        hBox.setTranslateX(120);
        hBox.setTranslateY(10);
        // add all elements onto vbox
        vBox.getChildren().addAll(BaseStudentPane.getBasePane(), gridPane, hBox);
        // return vbox
        return vBox;
    }
}
