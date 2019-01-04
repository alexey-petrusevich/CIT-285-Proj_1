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
import static panes.StaticData.loadScene;
import project1.Alerts;
import project1.Course;
import project1.Scenes;

/**
 *
 * @author Aliaksei
 */
public class ReceivablesPane {
    
    public static Pane getReceivablesPane(){
        // clear memory
        StaticData.clearPanes();
        // set fname, mi and lname textfields editable to false (use ssn for searching for student)
        StaticData.firstNameTF.setEditable(false);
        StaticData.miTF.setEditable(false);
        StaticData.lastNameTF.setEditable(false);
        // observable lists for credit and non-credit courses
        ObservableList<Course> creditList = FXCollections.observableArrayList();
        ObservableList<Course> nonCreditList = FXCollections.observableArrayList();
        // vbox to hold all elements
        StaticData.vBox = new VBox(10);
        // gridpane to hold labels and textfields
        StaticData.gridPane = new GridPane();
        StaticData.gridPane.setTranslateX(40);
        StaticData.gridPane.setHgap(10);
        StaticData.gridPane.setVgap(10);
        // add control elements to the gridpane
        StaticData.gridPane.add(StaticData.SSN_LABEL, 0, 0);
        StaticData.gridPane.add(StaticData.ssnTF, 1, 0);
        StaticData.gridPane.add(StaticData.SELECT_BUTTON, 2, 0);
        StaticData.gridPane.add(StaticData.FIRST_NAME_LABEL, 0, 1);
        StaticData.gridPane.add(StaticData.firstNameTF, 1, 1);
        StaticData.hBox2 = new HBox(10);
        StaticData.hBox2.getChildren().addAll(StaticData.OK_BUTTON);
        StaticData.gridPane.add(StaticData.hBox2, 2, 1);
        StaticData.gridPane.add(StaticData.MI_LABEL, 0, 2);
        StaticData.gridPane.add(StaticData.miTF, 1, 2);
        StaticData.gridPane.add(StaticData.LAST_NAME_LABEL, 0, 3);
        StaticData.gridPane.add(StaticData.lastNameTF, 1, 3);
        StaticData.gridPane.add(StaticData.NUMBER_OF_COURSES_LABEL, 0, 4);
        StaticData.gridPane.add(StaticData.numberOfCoursesTF, 1, 4);
        // hbox for tableviews
        StaticData.hBox = new HBox(10);
        StaticData.hBox.setTranslateX(40);
        // two vboxes for label + tableview
        StaticData.vBox1 = new VBox(10);
        StaticData.vBox2 = new VBox(10);
        StaticData.creditCoursesTV.setMaxHeight(300);
        StaticData.nonCreditCoursesTV.setMaxHeight(300);
        StaticData.vBox1.setAlignment(Pos.CENTER);
        StaticData.vBox1.getChildren().addAll(StaticData.CREDIT_COURSES_LABEL, StaticData.creditCoursesTV);
        StaticData.vBox2.setAlignment(Pos.CENTER);
        StaticData.vBox2.getChildren().addAll(StaticData.NON_CREDIT_COURSES_LABEL, StaticData.nonCreditCoursesTV);
        // add two vboxes to hbox
        StaticData.hBox.setTranslateX(40);
        StaticData.hBox.getChildren().addAll(StaticData.vBox1, StaticData.vBox2);
        // set the header label to receivables
        StaticData.headerLabel.setText("Receivables");
        StaticData.headerLabel.setTranslateX(40);
        // gridpane for grand total and two buttons
        StaticData.gridPane1 = new GridPane();
        StaticData.gridPane1.setTranslateX(40);
        StaticData.gridPane1.setHgap(20);
        StaticData.gridPane1.setVgap(10);
        StaticData.gridPane1.add(StaticData.GRAND_TOTAL_LABEL, 0, 0);
        StaticData.gridPane1.add(StaticData.grandTotalTF, 1, 0);
        // set a listener on select button
        StaticData.SELECT_BUTTON.setOnAction(ov->{
            int courseCount = 0;
            // submit a query with ssn provided by user in ssnTF
            try{
                // 1) connect driver
                Class.forName(StaticData.DRIVER);
                // 2) connect DB
                try(Connection connection = DriverManager.getConnection(StaticData.DATABASE,
                        StaticData.LOGIN, StaticData.PASSWORD)){
                    // 3) create statement for query submitiom
                    PreparedStatement prepStatement = connection.prepareStatement(""
                            + "SELECT * FROM students "
                            + "WHERE ssn = ?;");
                    prepStatement.setString(1, StaticData.ssnTF.getText());
                    prepStatement.execute();
                    // get resultset from the statement
                    ResultSet resultSet = prepStatement.getResultSet();
                    // check if resultSet returned any info
                    if (!resultSet.next()) {
                        throw new EntryNotFoundException("Student with SSN " + StaticData.ssnTF.getText() + 
                                " not found!");
                    } else {
                        Alerts.successAlert("Student with SSN " + StaticData.ssnTF.getText() + 
                                " found!");
                    }
                    // move cursor in result set to the begining of resultset
                    resultSet.beforeFirst();
                    // populate fname, mi, and lname with info from query
                    while(resultSet.next()){
                        StaticData.firstNameTF.setText(resultSet.getString("fname"));
                        StaticData.miTF.setText(resultSet.getString("mi"));
                        StaticData.lastNameTF.setText(resultSet.getString("lname"));
                        StaticData.grandTotalTF.setText(Integer.toString(resultSet.getInt("grandTotal") + resultSet.getInt("nonGrandTotal")));
                    }
                    // submit second query to get the courses selected by selected student
                    prepStatement = connection.prepareStatement("SELECT * FROM classes, combined, students "
                            + "WHERE students.ssn = ? "
                                    + "AND combined.ssn = students.ssn "
                                    + "AND combined.courseid = classes.courseid;");
                    prepStatement.setString(1, StaticData.ssnTF.getText());
                    prepStatement.execute();
                    // get the result set of all (credit and non-credit) courses
                    resultSet = prepStatement.getResultSet();
                    // sort result set depending on numCredits of the result
                    while(resultSet.next()){
                        // add to credit courses observable list
                        if (resultSet.getInt("numCredits") != 0){
                            creditList.add(new Course(
                                resultSet.getString("courseID"),
                                resultSet.getString("name"),
                                resultSet.getString("day"),
                                resultSet.getString("time"),
                                resultSet.getString("place"),
                                resultSet.getInt("ftprice"),
                                resultSet.getInt("ftdprice"),
                                resultSet.getInt("ptPrice"),
                                resultSet.getInt("numCredits")
                            ));
                            courseCount++; // increment courses count
                        } else {
                            // add to noncredit observable list
                            nonCreditList.add(new Course(
                                resultSet.getString("courseID"),
                                resultSet.getString("name"),
                                resultSet.getString("day"),
                                resultSet.getString("time"),
                                resultSet.getString("place"),
                                resultSet.getInt("ftprice"),
                                resultSet.getInt("ftdprice"),
                                resultSet.getInt("ptPrice"),
                                resultSet.getInt("numCredits")
                            ));
                            courseCount++; // increment courses count
                        }
                    }
                    // add observablie lists to tableviews
                    StaticData.creditCoursesTV.setItems(creditList);
                    StaticData.nonCreditCoursesTV.setItems(nonCreditList);
                    // set value properties on each table column 
                    // courseID, coursename, price
                    StaticData.courseIDTC1.setCellValueFactory(new PropertyValueFactory<>("courseID"));
                    StaticData.courseIDTC2.setCellValueFactory(new PropertyValueFactory<>("courseID"));
                    StaticData.courseNameTC1.setCellValueFactory(new PropertyValueFactory<>("name"));
                    StaticData.courseNameTC2.setCellValueFactory(new PropertyValueFactory<>("name"));
                    
                    // set number of courses with courses counter
                    StaticData.numberOfCoursesTF.setText(Integer.toString(courseCount));
                }
            } catch (ClassNotFoundException ex) {
                Alerts.alertError("JDBC driver not found!");
                System.exit(1);
            } catch (SQLException ex){
                Alerts.alertError("Unsuccessfull SQL query!");
                ex.printStackTrace();
            } catch (EntryNotFoundException ex){
                ex.getErrorMessage();
            }
            
        });
        StaticData.OK_BUTTON.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load welcome scene
            loadScene(ov, Scenes.getWelcomeScene());
        });
        // add all elements onto vbox
        StaticData.vBox.getChildren().addAll(StaticData.headerLabel, StaticData.gridPane, StaticData.hBox, StaticData.gridPane1);
        // return vbox
        return StaticData.vBox;
    }
}
