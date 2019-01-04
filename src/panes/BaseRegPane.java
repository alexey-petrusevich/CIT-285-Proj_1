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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project1.Alerts;
import project1.Course;
import project1.Scenes;

/**
 *
 * @author Aliaksei
 */
public abstract class BaseRegPane {
    
    /**
     * Returns an instance of the registration pane on the top, which contains
     * first name, middle initials, last name, and three button.
     * @return an instance of a pane on the top of registration window.
     */
    protected static Pane getRegistrationTop(){
        // clear values of panes if they aren't null
        StaticData.clearPanes();
        // vbox for all elements
        StaticData.vBox = new VBox(10);
        StaticData.vBox.setTranslateX(40);
        // gridpane labels, buttons, and textfields
        StaticData.gridPane = new GridPane();
        StaticData.gridPane.setHgap(50);
        StaticData.gridPane.setVgap(10);
        // add all elements onto gridpane
        StaticData.gridPane.add(StaticData.FIRST_NAME_LABEL, 0, 0);
        StaticData.gridPane.add(StaticData.firstNameTF, 1, 0);
        StaticData.gridPane.add(StaticData.studentFoundLabel, 2, 0);
        StaticData.gridPane.add(StaticData.MI_LABEL, 0, 1);
        StaticData.gridPane.add(StaticData.miTF, 1, 1);
        StaticData.gridPane.add(StaticData.SELECT_BUTTON, 2, 1);
        StaticData.gridPane.add(StaticData.LAST_NAME_LABEL, 0, 2);
        StaticData.gridPane.add(StaticData.lastNameTF, 1, 2);
        // hbox for ok and cancel buttons
        StaticData.hBox = new HBox(10);
        StaticData.hBox.getChildren().addAll(StaticData.OK_BUTTON, StaticData.CANCEL_BUTTON);
        StaticData.gridPane.add(StaticData.hBox, 2, 2);
        // set a listener on select button
        
        
        // set a listener on table views on click (to avoid double selection)
        StaticData.allCoursesTV.setOnMouseClicked(ov->{
            if (!StaticData.selectedCoursesTV.getSelectionModel().isEmpty()){
                StaticData.selectedCoursesTV.getSelectionModel().clearSelection();
            }
        });
        StaticData.selectedCoursesTV.setOnMouseClicked(ov->{
            if (!StaticData.allCoursesTV.getSelectionModel().isEmpty()){
                StaticData.allCoursesTV.getSelectionModel().clearSelection();
            }
        });
        // return gridpane
        StaticData.vBox.getChildren().addAll(StaticData.headerLabel, StaticData.gridPane);
        return StaticData.vBox;
    }
    /**
     * Returns an instance of the registration pane on the bottom, which contains
     * registration fee, grand total, two table views, and two button.
     * @return an instance of a pane on the bottom of registration menu.
     */
    protected static Pane getRegistrationBottom(){
        
        // observable array list of all courses
        ObservableList<Course> allCoursesList = FXCollections.observableArrayList();
        // observable array list of selected courses
        ObservableList<Course> selectedCoursesList = FXCollections.observableArrayList();
        
        // add listener on select button
        StaticData.SELECT_BUTTON.setOnAction(ov->{
            try{
                // 1) connect driver
                Class.forName(StaticData.DRIVER);
                // 2) connectio to DB
                try(Connection connection = DriverManager.getConnection(StaticData.DATABASE, StaticData.LOGIN, StaticData.PASSWORD)){
                    // search for the student from info provided by firstnametf, mitf, and lastnametf
                    PreparedStatement prepStatement = connection.prepareStatement("SELECT * "
                            + "FROM students "
                            + "WHERE fname = ? AND mi = ? AND lname = ?;");
                    prepStatement.setString(1, StaticData.firstNameTF.getText());
                    prepStatement.setString(2, StaticData.miTF.getText());
                    prepStatement.setString(3, StaticData.lastNameTF.getText());
                    prepStatement.execute();
                    // if prepstatement has not data, then throw student not found exception
                    if (!prepStatement.getResultSet().next()){
                        throw new EntryNotFoundException(StaticData.firstNameTF.getText() + " " + 
                        StaticData.miTF.getText() + " " + StaticData.lastNameTF.getText() + 
                        " not found!");
                    }
                    // else fill the correspnoding textfields with student info
                    else {
                        // display successfull search alert
                        Alerts.successAlert("Student " + StaticData.firstNameTF.getText() + 
                                " " + StaticData.miTF.getText() + " " + StaticData.lastNameTF.getText() + 
                                " found!");
                        StaticData.grandTotal = 5; // grand total contains registration fee
                        // get the result and reset it to the first position
                        ResultSet resultSet = prepStatement.getResultSet();
                        resultSet.beforeFirst();
                        StaticData.grandTotalTF.setText("5");
                        StaticData.registrationFeeTF.setText("5");
                        
                        
                    }
                    // load info to the table views
                    // 1) get info of all courses from classes table
                    prepStatement = connection.prepareStatement(""
                            + "SELECT * "
                            + "FROM classes;");
                    prepStatement.execute();
                    
                    ResultSet resultSetAll = prepStatement.getResultSet();
                    // 2) get courses selected by the student
                    prepStatement = connection.prepareStatement(""
                            + "SELECT * "
                            + "FROM classes, students, combined "
                            + "WHERE "
                            + "students.fname = ? "
                            + "AND combined.ssn = students.ssn "
                            + "AND combined.courseID = classes.courseid;");
                    prepStatement.setString(1, StaticData.firstNameTF.getText());
                    prepStatement.execute();
                    // resultset contains all classes student is registered for
                    ResultSet resultSetSelected = prepStatement.getResultSet();
                                       
                    // populate observable list of selected courses
                    while(resultSetSelected.next()){
                        // exclude non credit courses
                        if (resultSetSelected.getInt("numCredits") == 0){
                            continue;
                        }
                        selectedCoursesList.add(new Course(
                                resultSetSelected.getString("courseID"),
                                resultSetSelected.getString("name"),
                                resultSetSelected.getString("day"),
                                resultSetSelected.getString("time"),
                                resultSetSelected.getString("place"),
                                resultSetSelected.getInt("ftprice"),
                                resultSetSelected.getInt("ftdprice"),
                                resultSetSelected.getInt("ptprice"),
                                resultSetSelected.getInt("numCredits")
                        ));
                        // increment grand total 
                        if (StaticData.fullTimeSelected == true){
                            if (StaticData.numCredits < 9) {
                                // if number of credits is less than 9 and student is registering full time
                                StaticData.grandTotal+=285;
                            } else {
                                // if numCredits is 9 and more
                                StaticData.grandTotal+=265;
                            }
                        } else {
                            // if student is registering part time
                            StaticData.grandTotal+=300;
                        }
                        // increment number of credits
                        StaticData.numCredits+=3;
                    }
                    
                    // set values of number of credits and grand total to new values
                    StaticData.numberOfCreditsTF.setText(Integer.toString(StaticData.numCredits));
                    StaticData.grandTotalTF.setText(Integer.toString(StaticData.grandTotal));
                    // populate observable list of all courses
                    while (resultSetAll.next()){
                        // exclude non credit courses
                        if (resultSetAll.getInt("numCredits") == 0){
                            continue;
                        }
                        Course temp = new Course(
                                resultSetAll.getString("courseID"),
                                resultSetAll.getString("name"),
                                resultSetAll.getString("day"),
                                resultSetAll.getString("time"),
                                resultSetAll.getString("place"),
                                resultSetAll.getInt("ftprice"),
                                resultSetAll.getInt("ftdprice"),
                                resultSetAll.getInt("ptPrice"),
                                resultSetAll.getInt("numCredits")
                        );
                        // if no items were selected, simply add all courses
                        if (selectedCoursesList.isEmpty()){
                            allCoursesList.add(temp);
                        } else {
                            // exclude items from selected list
                            for (int i = 0; i < selectedCoursesList.size(); i++){
                                if (selectedCoursesList.get(i).getCourseID().
                                        equals(temp.getCourseID())){
                                    break;
                                } 
                                // if temp is not found after last iteration,
                                // right before exiting for loop, add temp to the all courses list
                                if (i == selectedCoursesList.size() - 1){
                                    allCoursesList.add(temp);
                                }
                            }
                        }
                        
                        
                        
                        
                    }
                    // add observable lists to table views
                    StaticData.allCoursesTV.setItems(allCoursesList);
                    StaticData.selectedCoursesTV.setItems(selectedCoursesList);
                    // set value factories of each column
                    StaticData.courseIDTC1.setCellValueFactory(new PropertyValueFactory<>("courseID"));
                    StaticData.courseIDTC2.setCellValueFactory(new PropertyValueFactory<>("courseID"));
                    StaticData.courseNameTC1.setCellValueFactory(new PropertyValueFactory<>("name"));
                    StaticData.courseNameTC2.setCellValueFactory(new PropertyValueFactory<>("name"));
                    StaticData.dayTC1.setCellValueFactory(new PropertyValueFactory<>("day"));
                    StaticData.dayTC2.setCellValueFactory(new PropertyValueFactory<>("day"));
                    StaticData.timeTC1.setCellValueFactory(new PropertyValueFactory<>("time"));
                    StaticData.timeTC2.setCellValueFactory(new PropertyValueFactory<>("time"));
                    StaticData.placeTC1.setCellValueFactory(new PropertyValueFactory<>("place"));
                    StaticData.placeTC2.setCellValueFactory(new PropertyValueFactory<>("place"));
                    if (StaticData.fullTimeSelected == true){
                        StaticData.priceTC1.setCellValueFactory(new PropertyValueFactory<>("ftPrice"));
                        StaticData.priceTC2.setCellValueFactory(new PropertyValueFactory<>("ftPrice"));
                    } else {
                        StaticData.priceTC1.setCellValueFactory(new PropertyValueFactory<>("ptPrice"));
                        StaticData.priceTC2.setCellValueFactory(new PropertyValueFactory<>("ptPrice"));
                    }
                    
                    
                    
                }
            } catch(EntryNotFoundException ex){
                Alerts.alertError(ex.getErrorMessage());
            } catch (ClassNotFoundException ex) {
                Alerts.alertError("JDBC Driver not found!");
                System.exit(1);
            } catch (SQLException ex){
                Alerts.alertError("Unsuccessfull query!");
                System.exit(1);
            }
        });
        
        // vbox for all elements
        StaticData.vBox1 = new VBox(10);
        StaticData.vBox1.setTranslateX(40);
        // gridpane for labels and textfields
        StaticData.gridPane1 = new GridPane();
        StaticData.gridPane1.setHgap(40);
        StaticData.gridPane1.setVgap(10);
        // add elements onto gridpane
        StaticData.gridPane1.add(StaticData.REGISTRATION_FEE_LABEL, 0, 0);
        StaticData.gridPane1.add(StaticData.registrationFeeTF, 1, 0);
        StaticData.gridPane1.add(StaticData.GRAND_TOTAL_LABEL, 0, 1);
        StaticData.gridPane1.add(StaticData.grandTotalTF, 1, 1);
        // hbox for tableviews and corresponding labels
        StaticData.hBox1 = new HBox(10);
        // vbox for all courses label + tableview
        StaticData.vBox2 = new VBox(10);
        StaticData.vBox2.setAlignment(Pos.CENTER);
        StaticData.vBox2.getChildren().addAll(StaticData.ALL_COURSES_LABEL, StaticData.allCoursesTV);
        
        // vbox for selected courses label + tableview
        VBox selectedBox = new VBox(10);
        selectedBox.setAlignment(Pos.CENTER);
        selectedBox.getChildren().addAll(StaticData.SELECTED_COURSES_LABEL, StaticData.selectedCoursesTV);
        // vbox for two button -> + <-
        VBox selectionButtonsBox = new VBox(30);
        selectionButtonsBox.setAlignment(Pos.CENTER);
        selectionButtonsBox.getChildren().addAll(StaticData.LR_BUTTON, StaticData.RL_BUTTON);
        // add listeners to lr and rl buttons
        StaticData.LR_BUTTON.setOnAction(ov->{
            // if selected item in all courses tableview is not empty, then add it to selected observable list
            if (!StaticData.allCoursesTV.getSelectionModel().isEmpty()){
                selectedCoursesList.add((Course)(StaticData.allCoursesTV.getSelectionModel().getSelectedItem()));
                // and remove it from all courses observable list
                allCoursesList.remove((Course)(StaticData.allCoursesTV.getSelectionModel().getSelectedItem()));
                
                if (StaticData.numCredits >= 9 && 
                        StaticData.fullTimeSelected == true){
                    
                     // increment grand total
                     StaticData.grandTotal+=265;
                    
                } else if (StaticData.numCredits < 9 &&
                        StaticData.fullTimeSelected == true){
                    // increment grand total
                StaticData.grandTotal+=285;
                } else { // if part time
                    StaticData.grandTotal+=300;
                }
                // increment number of credits by 3
                StaticData.numCredits+=3;
                if (StaticData.numCredits >= 9 && 
                        StaticData.fullTimeSelected == true){
                    // set the price in the tableview of all courses to discounted price
                     StaticData.priceTC1.setCellValueFactory(new PropertyValueFactory<>("ftdPrice"));
                }
                StaticData.numberOfCreditsTF.setText(Integer.toString(StaticData.numCredits));
                StaticData.grandTotalTF.setText(Integer.toString(StaticData.grandTotal));
            }
            
            
        });
        StaticData.RL_BUTTON.setOnAction(ov->{
            
                // if selected item from selectedcourses tableview is not empty, then add it to all courses observable list
                if (!StaticData.selectedCoursesTV.getSelectionModel().isEmpty()){

                    StaticData.numCredits-=3;
                if (StaticData.numCredits >= 9 && StaticData.fullTimeSelected == true){
                    // set the price in the tableview of all courses to discounted price
                         StaticData.priceTC1.setCellValueFactory(new PropertyValueFactory<>("ftdPrice"));
                        // decrement grand total
                        StaticData.grandTotal-=265; 
                } else if (StaticData.numCredits < 9 && StaticData.fullTimeSelected == true){
                    // set the price in the tableview of all courses to regular price
                         StaticData.priceTC1.setCellValueFactory(new PropertyValueFactory<>("ftPrice"));
                        // decrement grand total
                        StaticData.grandTotal-=285; 
                } else {
                    // part time
                    StaticData.grandTotal-=300;
                }
                
                
                allCoursesList.add((Course)(StaticData.selectedCoursesTV.getSelectionModel().getSelectedItem()));
                // remove it from the selected list
                selectedCoursesList.remove((Course)(StaticData.selectedCoursesTV.getSelectionModel().getSelectedItem()));
                // decrement number of credits by 3
                
                StaticData.numberOfCreditsTF.setText(Integer.toString(StaticData.numCredits));
                // refresh grant total text field
                StaticData.grandTotalTF.setText(Integer.toString(StaticData.grandTotal));
            }
            
        });
        
        // add listener to ok button
        StaticData.OK_BUTTON.setOnAction(e->{
            // insert info from selected courselist in db
            try{
                // mysql tinyint (boolean) to hold hasImmunization status of selected student
                int hasImmunization = 0; 
                // mysql tinyint (boolean) isMatriculated flag
                int isMatriculated = 0;
                // 1) connecto driver
                Class.forName(StaticData.DRIVER);
                // 2) connect DB
                try(Connection connection = DriverManager.getConnection(StaticData.DATABASE, StaticData.LOGIN, StaticData.PASSWORD)){
                    // 3) create a statement
                    Statement statement = connection.createStatement();
                    // get student's ssn
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM students "
                            + "WHERE fname = '" + StaticData.firstNameTF.getText() + "' "
                                    + "AND mi = '" + StaticData.miTF.getText() + "' "
                                            + "AND lname = '" + StaticData.lastNameTF.getText() + "';");
                    while(resultSet.next()){
                        StaticData.ssnTF.setText(resultSet.getString("ssn"));
                        // get hasImmunization status
                        hasImmunization = resultSet.getInt("hasImmunization");
                        // get isMatriculated status
                        isMatriculated = resultSet.getInt("isMatriculated");
                    }
                    if (isMatriculated == 0 && hasImmunization == 0 && StaticData.numCredits >= 9){
                        throw new MatriculationException("Immunization records not found!");
                    }
                    if (StaticData.fullTimeSelected == false && StaticData.numCredits > 6){
                        throw new PartTimeException("Part time students cannot have more than 6 credits!");
                    }

                    if (!selectedCoursesList.isEmpty()) {
                        // delete any credit entries from combined table with student's ssn
                        statement.executeUpdate("DELETE FROM combined "
                                + "USING combined, classes "
                                + "WHERE ssn = '" + StaticData.ssnTF.getText() + "'"
                                        + "AND combined.courseid = classes.courseid "
                                        + "AND classes.numCredits = 3;");
                        // if seleced list is not empty, traverce through selected observable
                        // list and enter every item into combined table
                        for (int i = 0; i < selectedCoursesList.size(); i++){
                            statement = connection.createStatement();
                            statement.executeUpdate(""
                                    + "INSERT INTO combined "
                                    + "(ssn, courseid) "
                                    + "VALUES "
                                    + "('" + StaticData.ssnTF.getText() + "', '" + 
                                    selectedCoursesList.get(i).getCourseID() + "');");

                        }
                        // check if registration fee was paid      
                        PreparedStatement prepStatement = connection.prepareStatement(""
                                + "SELECT paidRegFee FROM students "
                                + "WHERE fname = ? "
                                + "AND mi = ? "
                                + "AND lname = ?;");
                        prepStatement.setString(1, StaticData.firstNameTF.getText());
                        prepStatement.setString(2, StaticData.miTF.getText());
                        prepStatement.setString(3, StaticData.lastNameTF.getText());
                        prepStatement.execute();
                        resultSet = prepStatement.getResultSet();
                        while(resultSet.next()){
                            if (resultSet.getInt("paidRegFee") == 0){
                                // change the value of paidRegFee to 1 (true)
                                prepStatement = connection.prepareStatement(""
                                        + "UPDATE students SET paidRegFee = 1 "
                                        + "WHERE fname = ? "
                                        + "AND mi = ? "
                                        + "AND lname = ?;");
                                prepStatement.setString(1, StaticData.firstNameTF.getText());
                                prepStatement.setString(2, StaticData.miTF.getText());
                                prepStatement.setString(3, StaticData.lastNameTF.getText());
                                prepStatement.execute();
                            } else {
                                // else delivery fee has already been paid elsewhere
                                StaticData.grandTotal-=5;
                            }
                        }
                        
                        
                        // update student's grand total
                        statement = connection.createStatement();
                        statement.executeUpdate("UPDATE students SET grandTotal = " + 
                                StaticData.grandTotal + " WHERE ssn = '" + 
                                StaticData.ssnTF.getText() + "';");
                        
                        // display a confirmation alert that registration complete
                        Alerts.successAlert("Registration complete!");
                        // load welcome scene
                        StaticData.loadScene(e, Scenes.getWelcomeScene());
                    } else {
                        // display an error alert that no courses have been selected
                        // if selected list is empty
                        Alerts.alertError("No classes selected!");
                    }
                } 
                
                
                
            } catch (ClassNotFoundException ex) {
                Alerts.alertError("Driver not found!");
                System.exit(1);
            } catch (SQLException ex) {
                Alerts.alertError("SQL query unsuccessful!");
                ex.printStackTrace();
            } catch (MatriculationException ex) {
                Alerts.alertError(ex.getErrorMessage());
            } catch (PartTimeException ex){
                Alerts.alertError(ex.getErrorMessage());
            }
            
        });
        
        // add all vboxes onto hbox
        StaticData.hBox1.getChildren().addAll(StaticData.vBox2, selectionButtonsBox, selectedBox);
        // add gridpane and tableviewbox onto final vbox
        StaticData.vBox1.getChildren().addAll(StaticData.gridPane1, StaticData.hBox1);
        // return vbox
        return StaticData.vBox1;
    }
}
