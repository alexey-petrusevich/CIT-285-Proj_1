/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import exceptions.EntryNotFoundException;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project1.Alerts;
import project1.Course;
import project1.Scenes;

/**
 *
 * @author Aliaksei
 */
public class NonCreditPane  extends BaseRegPane{
    public static Pane getNonCreditPane(){
        // observable lists to hold contents of 
        // all non-credit courses
        ObservableList<Course> allNonCreditList = FXCollections.observableArrayList();
        // selected non-credit courses
        ObservableList<Course> selectedNonCreditList = FXCollections.observableArrayList();
        
        // vbox for all elements
        VBox vBox = new VBox(10);
        // set the name of the registration label
        StaticData.headerLabel.setText("Non-credit registration");
        // add all elements onto vbox
        vBox.getChildren().addAll(BaseRegPane.getRegistrationTop(), BaseRegPane.getRegistrationBottom());
        // set a listener onto select button (overrides? baseRegPane listener)
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
                        StaticData.grandTotal = 5; // non credit grand total contains registration fee
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
                        // exclude credit courses
                        if (resultSetSelected.getInt("numCredits") != 0){
                            continue;
                        }
                        selectedNonCreditList.add(new Course(
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
                        // increment grand total each time a course is placed into selected list
                        StaticData.grandTotal+=150; 
                    }
                    // refresh grand total textfield
                    StaticData.grandTotalTF.setText(Integer.toString(StaticData.grandTotal));
                    // populate observable list of all courses
                    while (resultSetAll.next()){
                        // exclude credit courses
                        if (resultSetAll.getInt("numCredits") != 0){
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
                        if (selectedNonCreditList.isEmpty()){
                            allNonCreditList.add(temp);
                        } else {
                            // exclude items from selected list
                            for (int i = 0; i < selectedNonCreditList.size(); i++){
                                if (selectedNonCreditList.get(i).getCourseID().
                                        equals(temp.getCourseID())){
                                    break;
                                } 
                                // if temp is not found after last iteration,
                                // right before exiting for loop, add temp to the all courses list
                                if (i == selectedNonCreditList.size() - 1){
                                    allNonCreditList.add(temp);
                                }
                            }
                        }
                        
                        
                        
                        
                    }
                    // add observable lists to table views
                    StaticData.allCoursesTV.setItems(allNonCreditList);
                    StaticData.selectedCoursesTV.setItems(selectedNonCreditList);
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
                    StaticData.priceTC1.setCellValueFactory(new PropertyValueFactory<>("ftPrice"));
                   
                    
                    
                    
                }
            } catch(EntryNotFoundException ex){
                Alerts.alertError(ex.getErrorMessage());
            } catch (ClassNotFoundException ex) {
                Alerts.alertError("JDBC Driver not found!");
                System.exit(1);
            } catch (SQLException ex){
                Alerts.alertError("Unsuccessfull query!");
                ex.printStackTrace();
            }
        });
        // add listeners onto LR buttons
        StaticData.LR_BUTTON.setOnAction(ov->{
            // if selected item in all courses tableview is not empty, then add it to selected observable list
            if (!StaticData.allCoursesTV.getSelectionModel().isEmpty()){
                selectedNonCreditList.add((Course)(StaticData.allCoursesTV.getSelectionModel().getSelectedItem()));
                // and remove it from all courses observable list
                allNonCreditList.remove((Course)(StaticData.allCoursesTV.getSelectionModel().getSelectedItem()));
                // increment grand total by 150
                StaticData.grandTotal+=150;   
                // set the grand total text field to grand total 
                StaticData.grandTotalTF.setText(Integer.toString(StaticData.grandTotal));
            }   
            
        });
        StaticData.RL_BUTTON.setOnAction(ov->{
            // if selected item from selectedcourses tableview is not empty, then add it to all courses observable list
            if (!StaticData.selectedCoursesTV.getSelectionModel().isEmpty()){
                allNonCreditList.add((Course)(StaticData.selectedCoursesTV.getSelectionModel().getSelectedItem()));
                // remove it from the selected list
                selectedNonCreditList.remove((Course)(StaticData.selectedCoursesTV.getSelectionModel().getSelectedItem()));
                // decrement grand total by 150
                StaticData.grandTotal-=150;
                // set the value of text field with grand total
                StaticData.grandTotalTF.setText(Integer.toString(StaticData.grandTotal));
            }
            
        });
        
                // add listener to ok button
        StaticData.OK_BUTTON.setOnAction(e->{
            // insert info from selected courselist in db
            try{
                // 1) connecto driver
                Class.forName(StaticData.DRIVER);
                // 2) connect DB
                try(Connection connection = DriverManager.getConnection(StaticData.DATABASE, StaticData.LOGIN, StaticData.PASSWORD)){
                    // 3) create a statement
                    Statement statement = connection.createStatement();
                    // get student's ssn
                    ResultSet resultSet = statement.executeQuery("SELECT ssn FROM students "
                            + "WHERE fname = '" + StaticData.firstNameTF.getText() + "' "
                                    + "AND mi = '" + StaticData.miTF.getText() + "' "
                                            + "AND lname = '" + StaticData.lastNameTF.getText() + "';");
                    while(resultSet.next()){
                        StaticData.ssnTF.setText(resultSet.getString("ssn"));
                    }


                    
                        // delete any non-credit entries from combined table with student's ssn
                        statement.executeUpdate("DELETE FROM combined "
                                + "USING combined, classes "
                                + "WHERE ssn = '" + StaticData.ssnTF.getText() + "'"
                                        + "AND combined.courseid = classes.courseid "
                                        + "AND classes.numCredits = 0;");
                        // if seleced list is not empty, traverce through selected observable
                        // list and enter every item into combined table
                        for (int i = 0; i < selectedNonCreditList.size(); i++){
                            statement = connection.createStatement();
                            statement.executeUpdate(""
                                    + "INSERT INTO combined "
                                    + "(ssn, courseid) "
                                    + "VALUES "
                                    + "('" + StaticData.ssnTF.getText() + "', '" + 
                                    selectedNonCreditList.get(i).getCourseID() + "');");

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
                        statement.executeUpdate("UPDATE students SET nonGrandTotal = " + 
                                StaticData.grandTotal + " WHERE fname = '" + 
                                StaticData.firstNameTF.getText() + "';");
                        // display a confirmation alert that registration complete
                        Alerts.successAlert("Registration complete!");
                        // load welcome scene
                        StaticData.loadScene(e, Scenes.getWelcomeScene());
                    
                }
                
                
                
            } catch (ClassNotFoundException ex) {
                Alerts.alertError("Driver not found!");
                System.exit(1);
            } catch (SQLException ex) {
                Alerts.alertError("SQL query unsuccessful!");
                ex.printStackTrace();
            }
            
        });
        // return vbox
        return vBox;
    }
}
