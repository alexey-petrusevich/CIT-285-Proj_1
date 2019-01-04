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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class SchedulePane {
    
    public static Pane getSchedulePane(){
        // clear panes contents
        StaticData.clearPanes();
        StaticData.matriculatedCheckBox.setSelected(false);
        StaticData.scheduleTV.setMaxWidth(370); // width of schedule tableview
        // set fname, mi and lname textfields editable to false (use ssn for searching for student)
        StaticData.firstNameTF.setEditable(false);
        StaticData.miTF.setEditable(false);
        StaticData.lastNameTF.setEditable(false);
        // observable list of student courses
        ObservableList<Course> allCoursesList = FXCollections.observableArrayList();
        // place table columns onto the schedule tableview
        
        // vbox for all elements
        StaticData.vBox = new VBox(10);
        // gridpane for labels, checkbox and textfields
        StaticData.gridPane = new GridPane();
        StaticData.gridPane.setVgap(10);
        StaticData.gridPane.setHgap(30);
        StaticData.gridPane.setTranslateX(40);
        // add elements
        StaticData.gridPane.add(StaticData.SSN_LABEL, 0, 0);
        StaticData.gridPane.add(StaticData.ssnTF, 1, 0);
        StaticData.gridPane.add(StaticData.SELECT_BUTTON, 2, 0);
        StaticData.gridPane.add(StaticData.FIRST_NAME_LABEL, 0, 1);
        StaticData.gridPane.add(StaticData.firstNameTF, 1, 1);
        // hbox for buttons
        StaticData.hBox = new HBox(10);
        StaticData.hBox.getChildren().addAll(StaticData.OK_BUTTON);
        StaticData.gridPane.add(StaticData.hBox, 2, 1);
        StaticData.gridPane.add(StaticData.MI_LABEL, 0, 2);
        StaticData.gridPane.add(StaticData.miTF, 1, 2);
        StaticData.gridPane.add(StaticData.LAST_NAME_LABEL, 0, 3);
        StaticData.gridPane.add(StaticData.lastNameTF, 1, 3);
        StaticData.gridPane.add(StaticData.MATRICULATED_LABEL, 0, 4);
        StaticData.gridPane.add(StaticData.matriculatedCheckBox, 1, 4);
        // header label
        StaticData.headerLabel.setText("Class Schedule");
        StaticData.headerLabel.setTranslateX(40);
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
                        // if student is matriculated, check matriculated box
                        if (resultSet.getInt("isMatriculated") != 0){
                            StaticData.matriculatedCheckBox.setSelected(true);
                        } 
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
                    // add results into observable list
                    while(resultSet.next()){
                        allCoursesList.add(new Course(
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
                    }
                    // add observablie list to tableview
                    StaticData.scheduleTV.setItems(allCoursesList);
                    // set value properties on each table column 
                    // courseID, coursename, day, time, place
                    StaticData.courseIDTC1.setCellValueFactory(new PropertyValueFactory<>("courseID"));
                    StaticData.courseNameTC1.setCellValueFactory(new PropertyValueFactory<>("name"));
                    StaticData.dayTC1.setCellValueFactory(new PropertyValueFactory<>("day"));
                    StaticData.timeTC1.setCellValueFactory(new PropertyValueFactory<>("time"));
                    StaticData.placeTC1.setCellValueFactory(new PropertyValueFactory<>("place"));
                    
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
        // add all elements onto vbox
        StaticData.scheduleTV.setTranslateX(40);
        StaticData.vBox.getChildren().addAll(StaticData.headerLabel, StaticData.gridPane, StaticData.scheduleTV);
        StaticData.OK_BUTTON.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load welcome scene
            loadScene(ov, Scenes.getWelcomeScene());
        });
        // return vbox
        return StaticData.vBox;
    }
}
