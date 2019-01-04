/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import com.sun.rowset.JdbcRowSetImpl;
import exceptions.EntryNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.*;
import javafx.event.Event;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javax.sql.RowSet;
import project1.Alerts;
import project1.Scenes;

/**
 *
 * @author Aliaksei
 */
public class StaticData {
    // length of textfields and size of databaze input
    static final int SSN_LENGTH = 9;
    static final int FNAME_LENGTH = 30;
    static final int MI_LENGTH = 1;
    static final int LNAME_LENGTH = 30;
    static final int ADDRESS_LENGTH = 50;
    static final int CITY_LENGTH = 30;
    static final int STATE_LENGTH = 15;
    static final int ZIP_LENGTH = 5;
    // labels
    static final Label DATE_LABEL = new Label("Today's date: ");
    static final Label MATRICULATION_YEAR_LABEL = new Label("Matriculation year: ");
    static final Label DEGREE_LABEL = new Label("Degree: ");
    static final Label HS_DIPLOMA_LABEL = new Label("Highschool diploma: ");
    static final Label IMMUNIZED_LABEL = new Label("Immunization: ");
    static final Label SSN_LABEL = new Label("SSN: ");
    static final Label FIRST_NAME_LABEL = new Label("First name: ");
    static final Label MI_LABEL = new Label("Middle initials: ");
    static final Label LAST_NAME_LABEL = new Label("Last name: ");
    static final Label STREET_ADDRESS_LABEL = new Label("Street address: ");
    static final Label CITY_LABEL = new Label("City: ");
    static final Label STATE_LABEL = new Label("State");
    static final Label ZIP_LABEL = new Label("ZIP: ");
    static final Label NUMBER_OF_CREDITS_LABEL = new Label("Number of credits: ");
    static Label headerLabel = new Label();
    static Label studentFoundLabel = new Label();
    static final Label REGISTRATION_FEE_LABEL = new Label("Registration fee: ");
    static final Label GRAND_TOTAL_LABEL = new Label("Grand total: ");
    static final Label ALL_COURSES_LABEL = new Label("All courses");
    static final Label SELECTED_COURSES_LABEL = new Label("Selected courses");
    static final Label HINT_LABEL = new Label("(Check only if you intend\n"
            + "to register for 9 or more credits)");
    static final Label NUMBER_OF_COURSES_LABEL = new Label("Number of courses: ");
    static final Label MATRICULATED_LABEL = new Label("Matriculated: ");
    static final Label CREDIT_COURSES_LABEL = new Label("Credit courses: ");
    static final Label NON_CREDIT_COURSES_LABEL = new Label("Non-credit courses: ");
    // buttons
    static final Button SELECT_BUTTON = new Button("Select");
    static final Button OK_BUTTON = new Button("OK");
    static final Button CANCEL_BUTTON = new Button("Cancel");
    static final Button LR_BUTTON = new Button("->");
    static final Button RL_BUTTON = new Button("<-");
    static final Button NEW_ENTRY_BUTTON = new Button("New entry");
    static final Button UPDATE_ENTRY_BUTTON = new Button("Update entry");
    static final Button SSN_OK_BUTTON = new Button("OK");
    // tableviews
    static TableView allCoursesTV = new TableView();    
        // table columns for all courses
        static TableColumn courseIDTC1 = new TableColumn("#");
        static TableColumn courseNameTC1 = new TableColumn("Name");
        static TableColumn dayTC1 = new TableColumn("Day");
        static TableColumn timeTC1 = new TableColumn("Time");
        static TableColumn placeTC1 = new TableColumn("Place");
        static TableColumn priceTC1 = new TableColumn("Price");
    static TableView selectedCoursesTV = new TableView(); 
        // table columns for selected courses
        static TableColumn courseIDTC2 = new TableColumn("#");
        static TableColumn courseNameTC2 = new TableColumn("Name");
        static TableColumn dayTC2 = new TableColumn("Day");
        static TableColumn timeTC2 = new TableColumn("Time");
        static TableColumn placeTC2 = new TableColumn("Place");
        static TableColumn priceTC2 = new TableColumn("Price");
    static TableView creditCoursesTV = new TableView(); // for receivables
        // # column
        static TableColumn creditCourseIDTC1 = new TableColumn("#");
        // name column
        static TableColumn creditCourseNameTC1 = new TableColumn("Name");
        // price column
        static TableColumn creditCoursePriceTC1 = new TableColumn("Price");
    static TableView nonCreditCoursesTV = new TableView(); // for receivables
        // # column
        static TableColumn creditCourseIDTC2 = new TableColumn("#");
        // name column
        static TableColumn creditCourseNameTC2 = new TableColumn("Name");
        // price column
        static TableColumn creditCoursePriceTC2 = new TableColumn("Price");
    static TableView scheduleTV = new TableView();
        // table columns for schedule
        // # column
        // name column
        // time column
        // place column
    // cell value factories for tableviews
    
    // textfields
    static TextField ssnTF = new TextField();
    static TextField firstNameTF = new TextField();
    static TextField miTF = new TextField();
    static TextField lastNameTF = new TextField();
    static TextField streetAddressTF = new TextField();
    static TextField cityTF = new TextField();
    static TextField zipTF = new TextField();
    static TextField numberOfCreditsTF = new TextField();
    static TextField registrationFeeTF = new TextField();
    static TextField grandTotalTF = new TextField();
    static TextField dateTF = new TextField();
    static TextField numberOfCoursesTF = new TextField();
    // checkboxes
    static CheckBox hsDiplomaCheckBox = new CheckBox();
    static CheckBox immunizedCheckBox = new CheckBox();
    static CheckBox matriculatedCheckBox = new CheckBox();
    // observable list of degrees
    static final ObservableList<String> DEGREES_LIST = FXCollections.observableArrayList(
            "Associate of Science in Computer Programming",
            "Associate of Arts in Humanities"
    );
    // observable list of matriculation years
    private static final ObservableList<String> MATRICULATION_YEARS_LIST = FXCollections.observableArrayList(
            "2017",
            "2018",
            "2019",
            "2020"
    );
    // observable list of states
    static final ObservableList<String> STATES_LIST = FXCollections.observableArrayList(
            "Alabama",
            "Alaska",
            "Arizona",
            "Arkansas",
            "California",
            "Colorado",
            "Connecticut",
            "Delaware",
            "Florida",
            "Georgia",
            "Hawaii",
            "Idaho",
            "Illinois",
            "Indiana",
            "Iowa",
            "Kansas",
            "Kentucky",
            "Louisiana",
            "Maine",
            "Maryland",
            "Massachusetts",
            "Michigan",
            "Minnesota",
            "Mississippi",
            "Missouri",
            "Montana",
            "Nebraska",
            "Nevada",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "New York",
            "North Carolina",
            "North Dakota",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Pennsylvania",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virginia",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming"
    );
    // combo box for degrees
    static final ComboBox<String> DEGREES_COMBO_BOX = new ComboBox<>(DEGREES_LIST);
    // combo box for marticulations years
    static final ComboBox<String> MATRICULATION_YEARS_COMBO_BOX = new ComboBox(MATRICULATION_YEARS_LIST);
    // combobox for states
    static final ComboBox<String> STATES_COMBO_BOX = new ComboBox<>(STATES_LIST);
    // menus and menuitems
    static final MenuBar MENU_BAR = new MenuBar();
    static final Menu REPORTS_MENU = new Menu("Reports");
    static final MenuItem RECEIVABLES_MENU_ITEM = new MenuItem("Receivables");
    static final MenuItem CLASS_SCHEDULE_MENU_ITEM = new MenuItem("Class Schedule");
    static final Menu REGISTRATION_MENU = new Menu("Registration");
    static final MenuItem FULL_TIME_MENU_ITEM = new MenuItem("Full-time");    
    static final MenuItem PART_TIME_MENU_ITEM = new MenuItem("Part-time");    
    static final MenuItem NON_CREDIT_MENU_ITEM = new MenuItem("Non-credit");
    static final Menu ADMISSIONS_MENU = new Menu("Admissions");
    static final MenuItem MATRICULATED_MENU_ITEM = new MenuItem("Matriculated");
    static final MenuItem NON_MATRICULATED_MENU_ITEM = new MenuItem("Non-Matricluated");
    static final MenuItem QUIT_MENU_ITEM = new MenuItem("Quit");
    // panes
    static GridPane gridPane;
    static GridPane gridPane1;
    static VBox vBox;
    static VBox vBox1;
    static VBox vBox2;
    static HBox hBox;
    static HBox hBox1;
    static HBox hBox2;
    static BorderPane borderPane;
    // stage
    static Stage stage;
    // flag for new/update scenes
    static boolean matriculatedSelected = false;
    // temporary static variable to hold student's ssn
    static String tempSSN = "";
    // other constants
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE = "jdbc:mysql://localhost/project1";
    static final String LOGIN = "root";
    static final String PASSWORD = "mysql";
    static boolean fullTimeSelected = true; // flag for full time selection
    // temporariy variable to hold number of credits
    static int numCredits = 0;
    // temp variable to hold grandtotal
    static int grandTotal = 0;
    // counter to hold number of selected courses
    static int selectedCount = 0;
    // static data initialization
    static boolean editModeSelected = false;
    // ----------------------------------------------------------------------------------------------------------------
    static{
        // set table views dimenstions
        StaticData.allCoursesTV.setMaxHeight(350);
        StaticData.allCoursesTV.setMaxWidth(435);
        StaticData.selectedCoursesTV.setMaxHeight(350);
        StaticData.selectedCoursesTV.setMaxWidth(435);
        // set dementions of table columns
        StaticData.courseIDTC1.setPrefWidth(60);
        StaticData.courseIDTC2.setPrefWidth(60);
        StaticData.courseNameTC1.setPrefWidth(150);
        StaticData.courseNameTC2.setPrefWidth(150);
        StaticData.dayTC1.setPrefWidth(45);
        StaticData.dayTC2.setPrefWidth(45);
        StaticData.timeTC1.setPrefWidth(65);
        StaticData.timeTC2.setPrefWidth(65);
        StaticData.placeTC1.setPrefWidth(45);
        StaticData.placeTC2.setPrefWidth(45);
        StaticData.priceTC1.setPrefWidth(70);
        StaticData.priceTC2.setPrefWidth(70);
        // turn off table columns resizable property
        StaticData.courseIDTC1.setResizable(false);
        StaticData.courseIDTC2.setResizable(false);
        StaticData.courseNameTC1.setResizable(false);
        StaticData.courseNameTC2.setResizable(false);
        StaticData.dayTC1.setResizable(false);
        StaticData.dayTC2.setResizable(false);
        StaticData.timeTC1.setResizable(false);
        StaticData.timeTC2.setResizable(false);
        StaticData.placeTC1.setResizable(false);
        StaticData.placeTC2.setResizable(false);
        StaticData.priceTC1.setResizable(false);
        StaticData.priceTC2.setResizable(false);
        FULL_TIME_MENU_ITEM.setAccelerator(KeyCombination.keyCombination("Ctrl+F")); // accelerator for full time
        PART_TIME_MENU_ITEM.setAccelerator(KeyCombination.keyCombination("Ctrl+P")); // accelerator for part time
        NON_CREDIT_MENU_ITEM.setAccelerator(KeyCombination.keyCombination("Ctrl+N")); // accelerator for non credit
        RECEIVABLES_MENU_ITEM.setAccelerator(KeyCombination.keyCombination("Ctrl+R")); // accelerator for receivables
        CLASS_SCHEDULE_MENU_ITEM.setAccelerator(KeyCombination.keyCombination("Ctrl+C")); // accelerator for class schedule
        REGISTRATION_MENU.getItems().addAll(FULL_TIME_MENU_ITEM, PART_TIME_MENU_ITEM, NON_CREDIT_MENU_ITEM);
        REPORTS_MENU.getItems().addAll(RECEIVABLES_MENU_ITEM, CLASS_SCHEDULE_MENU_ITEM);
        MATRICULATED_MENU_ITEM.setAccelerator(KeyCombination.keyCombination("Ctrl+M")); // accelerator for matriculated
        ADMISSIONS_MENU.getItems().addAll(MATRICULATED_MENU_ITEM, NON_MATRICULATED_MENU_ITEM, QUIT_MENU_ITEM);
        NON_MATRICULATED_MENU_ITEM.setAccelerator(KeyCombination.keyCombination("Ctrl+Z")); // accelerator for nonmatriculated
        QUIT_MENU_ITEM.setAccelerator(KeyCombination.keyCombination("Ctrl+Q")); // accelerator for quit
        StaticData.MENU_BAR.getMenus().addAll(StaticData.ADMISSIONS_MENU, StaticData.REGISTRATION_MENU, StaticData.REPORTS_MENU);
        // set the font for the top label (FT, PT, or non-credit)
        StaticData.headerLabel.setFont(Font.font("Times", FontWeight.BOLD, 16));
        StaticData.scheduleTV.setMaxHeight(300);
        StaticData.scheduleTV.setMaxWidth(400);
        OK_BUTTON.setMinWidth(75);
        CANCEL_BUTTON.setMinWidth(75);
        SELECT_BUTTON.setMinWidth(75);
        StaticData.SSN_OK_BUTTON.setMinWidth(75);
        // disable number of credtis, regisration fee, grand total textfields
        StaticData.numberOfCreditsTF.setEditable(false);
        StaticData.registrationFeeTF.setEditable(false);
        StaticData.grandTotalTF.setEditable(false);        
        // disable number of courses text field
        StaticData.numberOfCoursesTF.setEditable(false);
        // disable grand total text field
        StaticData.grandTotalTF.setEditable(false);
        // disable matriculated checkbox
        StaticData.matriculatedCheckBox.setDisable(true);
        // add default listeners to buttons and menu items
        MATRICULATED_MENU_ITEM.setOnAction(ov->{
            // set matriculated flag to true
            StaticData.matriculatedSelected = true;
            // clear contents of control elements
            StaticData.clearContent();
            // load matriculated scene
            loadScene(ov, Scenes.getNewUpdateScene());
        });
        StaticData.NON_MATRICULATED_MENU_ITEM.setOnAction(ov->{
            // set matriculated flag to false
            StaticData.matriculatedSelected = false;
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load non-matriculated scene
            loadScene(ov, Scenes.getNewUpdateScene());
        });
        StaticData.FULL_TIME_MENU_ITEM.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // set full time flag to true
            StaticData.fullTimeSelected = true;
            // load full-time registration scene
            loadScene(ov, Scenes.getFullTimeScene());
            // load registration table columns
            StaticData.loadRegistrationColumns();
        });
        StaticData.PART_TIME_MENU_ITEM.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // set full time flag to false
            StaticData.fullTimeSelected = false;
            // load part-time registration scene
            loadScene(ov, Scenes.getPartTimeScene());
            // load registration table columns
            StaticData.loadRegistrationColumns();
        });
        StaticData.NON_CREDIT_MENU_ITEM.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load non-credit registration scene
            loadScene(ov, Scenes.getNonCreditScene());
            // load registration table columns
            StaticData.loadRegistrationColumns();
        });
        StaticData.RECEIVABLES_MENU_ITEM.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load receivables scene
            loadScene(ov, Scenes.getReceivablesScene());
            // load receivables table columns
            StaticData.loadReceivablesColumns();
        });
        StaticData.CLASS_SCHEDULE_MENU_ITEM.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load class schedule scene
            loadScene(ov, Scenes.getClassScheduleScene());
            // load class schedule table columns
            StaticData.loadScheduleColumns();
        });
        StaticData.OK_BUTTON.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load welcome scene
            loadScene(ov, Scenes.getWelcomeScene());
        });
        StaticData.CANCEL_BUTTON.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load welcome scene
            // unlock ssntf if locked
            StaticData.ssnTF.setEditable(true);
            loadScene(ov, Scenes.getWelcomeScene());
        });
        StaticData.QUIT_MENU_ITEM.setOnAction(ov->{
            // exit if quit is chosen
            Alerts.quitAlert();
        });
        StaticData.NEW_ENTRY_BUTTON.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load new scene matriculated/non-matriculated
            if (StaticData.matriculatedSelected == true){
                loadScene(ov, Scenes.getMatriculatedScene());
            } else {
                loadScene(ov, Scenes.getNonMatriculatedScene());
            }
        });
        StaticData.UPDATE_ENTRY_BUTTON.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // load enter ssn scene
            loadScene(ov, Scenes.getEnterSSNScene());
        });
        StaticData.SSN_OK_BUTTON.setOnAction(ov->{
            // clear panes
            StaticData.clearPanes();
            // clear contents of control elements
            StaticData.clearContent();
            // !!!!!!!!!!!!!!!! make query to DB here
            // 1) check if entry exists
            // 2) if yes, populate static textfields with data from DB
            // 3) check if student matriculated flag is the same as program's
            // matriculated flag; if do not match, display an alert with a message
            if (StaticData.matriculatedSelected == true){
                loadScene(ov, Scenes.getMatriculatedScene());
            } else {
                loadScene(ov, Scenes.getNonMatriculatedScene());
            }
        });
        // create classes table if it doesn't exist
        StaticData.initClassesTable();
        // create student table if it doesn't exist
        StaticData.initStudentTable();
        // create combined table if it doesn't exist
        StaticData.initCombinedTable();
    }
    // --------------------------------------------------------------------------------
    /**
     * Method clears memory allocated for a specific static pane.
     * If a pane is not null, assign it to null. After process is complete,
     * garbage collection method is called.
     */
    public static void clearPanes(){
        if (vBox != null) StaticData.vBox = null;
        if (vBox1 != null) StaticData.vBox1 = null;
        if (vBox2 != null) StaticData.vBox2 = null;
        if (gridPane != null) StaticData.gridPane = null;
        if (gridPane1 != null) StaticData.gridPane1 = null;
        if (hBox != null) StaticData.hBox = null;
        if (hBox1 != null) StaticData.hBox1 = null;
        if (hBox2 != null) StaticData.hBox2 = null;
        System.gc();   
    }
    /**
     * Method clears the contents of non-constant labels and text fields.
     */
    public static void clearContent(){
        editModeSelected = false;
        // clear labels
        StaticData.headerLabel.setText("");
        StaticData.studentFoundLabel.setText("");
        // uncheck checkboxes if checked
        StaticData.hsDiplomaCheckBox.setSelected(false);
        StaticData.immunizedCheckBox.setSelected(false);
        StaticData.matriculatedCheckBox.setSelected(false);
        StaticData.firstNameTF.setEditable(true);
        StaticData.miTF.setEditable(true);
        StaticData.lastNameTF.setEditable(true);
        // clear textfields
        StaticData.ssnTF.setText("");
        StaticData.firstNameTF.setText("");
        StaticData.miTF.setText("");
        StaticData.lastNameTF.setText("");
        StaticData.streetAddressTF.setText("");
        StaticData.cityTF.setText("");
        StaticData.zipTF.setText("");
        StaticData.numberOfCreditsTF.setText("");
        StaticData.registrationFeeTF.setText("");
        StaticData.grandTotalTF.setText("");
        StaticData.numberOfCoursesTF.setText("");
        // clear combo box selection
        StaticData.DEGREES_COMBO_BOX.getSelectionModel().clearSelection();
        StaticData.MATRICULATION_YEARS_COMBO_BOX.getSelectionModel().clearSelection();
        StaticData.STATES_COMBO_BOX.getSelectionModel().clearSelection();
        // reset settranslate for header label
        StaticData.headerLabel.setTranslateX(0);
        // clear tableview columns
        StaticData.allCoursesTV = new TableView();
        StaticData.selectedCoursesTV = new TableView();
        StaticData.scheduleTV = new TableView();
        StaticData.creditCoursesTV = new TableView();
        StaticData.nonCreditCoursesTV = new TableView();
        StaticData.courseIDTC1.setPrefWidth(60);
        StaticData.courseIDTC2.setPrefWidth(60);
        StaticData.courseNameTC1.setPrefWidth(150);
        StaticData.courseNameTC2.setPrefWidth(150);
        StaticData.numCredits = 0;
        StaticData.grandTotal = 0;
    }
    /**
     * Method loads new scene onto the stage.
     * @param event an action event. Used to obtain the stage of the event.
     * @param scene a scene to be loaded onto the stage.
     */
    public static void loadScene(Event event, Scene scene){
        // get the stage of the action event
        // if event came from menu item
        if (event.getSource() instanceof MenuItem){
            stage = (Stage)(((MenuItem)(event.getSource())).getParentPopup().getOwnerWindow());
        } else {
            stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        }
        // hide the stage
        stage.hide();
        // put new scene on the stage
        stage.setScene(scene);
        // call garbage collector
        System.gc();
        // show the stage
        stage.show();
    }
    /**
     * Method creates a hard coded MYSQL table with the list of classes.
     * courseID varchar(7), name varchar(50)
     * courseID is a string with a maximum of 7 characters (PHY-251 for example)
     * name is a string describing a course (College Physics for example)
     */
    private static void initClassesTable(){
        // 1) load the driver
        try {
            Class.forName(DRIVER);
            // 2) connect to database (create a connection)
            try(Connection connection = DriverManager.getConnection(DATABASE, LOGIN, PASSWORD)){
                // 3) create a statement
                Statement statement = connection.createStatement();
                // create new table if not exists
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS classes "
                        + "(courseID char(7) NOT NULL PRIMARY KEY," // PK
                        + "name varchar(50) NOT NULL,"
                        + "day varchar(2) NOT NULL,"
                        + "time varchar(11) NOT NULL,"
                        + "place char(4) NOT NULL,"
                        + "ftprice int NOT NULL,"
                        + "ftdprice int NOT NULL," // discounted price
                        + "ptprice int NOT NULL, "
                        + "numCredits int NOT NULL);");
                // check if table is not empty
                ResultSet resultSet = statement.executeQuery("SELECT * FROM classes;");
                while (!resultSet.next()){
                    // add courses
                    // credit courses
                    insertCourse(statement, "CMP 100","Introduction to Computers", "M", "6:30-8:45", "D211", 285, 265, 300, 3);
                    insertCourse(statement, "OIM 220","Keyboarding I", "Tu", "5:00-6:15", "B101", 285, 265, 300, 3);
                    insertCourse(statement, "ENG 111","English I", "W", "8:30-11:45", "C202", 285, 265, 300, 3);
                    insertCourse(statement, "CMP 545","Web Programming", "F", "6:30-9:15", "D117", 285, 265, 300, 3);
                    insertCourse(statement, "CMP 237","C++ Programming", "Th", "6:30-9:15", "D121", 285, 265, 300, 3);
                    // non credit
                    insertCourse(statement, "NC100","Basic Cookie Baking", "M", "6:00-8:00", "E415", 150, 150, 150, 0);
                    insertCourse(statement, "NC200","Advanced Tire Inflation", "W", "6:00-8:00", "D100", 150, 150, 150, 0);
                    insertCourse(statement, "NC300","Intro to Sinusitis", "W", "6:00-8:00", "B345", 150, 150, 150, 0);
                    insertCourse(statement, "NC400","Shoe Polish and You", "Tu", "6:15-8:15", "C202", 150, 150, 150, 0);
                    insertCourse(statement, "NC500","Gout for Fun and Profit", "F", "7:00-9:00", "A300", 150, 150, 150, 0);
                }
                
            }
        } catch (ClassNotFoundException e){
            // use the error alert
            Alerts.alertError("JDBC driver not found!");
        } catch (SQLException e){
            // use error alert
            Alerts.alertError("Unsuccessfull SQL query!");
            e.printStackTrace();
        }
        
    }
    /**
     * Method executes insertion of the class entries.
     * @param statement statement to be used for passing commands.
     * @param courseID course id, such as PHY-101 for example.
     * @param courseName course description, such as College Physics.
     * @param day day of the class M, Tu, W, Th, F, Sa, Su
     * @param time time of the meeting.
     * @param place place of the meeting.
     * @param ftPrice full-time price.
     * @param ptPrice part-time price.
     * @throws SQLException 
     */
    private static void insertCourse(Statement statement, String courseID, 
            String courseName, String day, String time, String place, 
            int ftPrice, int ftdPrice, int ptPrice, int numCredits) throws SQLException{
        // execute insertion statement into classes
        statement.executeUpdate("INSERT INTO classes "
                    + "(courseID, name, day, time, place, ftprice, ftdprice, ptprice, numCredits) "
                    + "VALUES "
                    + "('" + courseID + "', '" + courseName + "', '" + day + "', '"
                            + time + "', '" + place + "', " + ftPrice + ", " + ftdPrice + ", "
                                    + "" + ptPrice + ", " + numCredits
                                    + ");");
    }
    private static void initStudentTable(){
        try{
            // 1) connect driver
            Class.forName(DRIVER);
            // 2) connect database (create connection)
            Connection connection = DriverManager.getConnection(DATABASE, LOGIN, PASSWORD);
            // 3) create statement
            Statement statement = connection.createStatement();
            // create new table if not exists
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS students "
                    + "("
                    + "SSN char(" + StaticData.SSN_LENGTH + ") NOT NULL PRIMARY KEY," // PK
                    + "fname varchar(" + StaticData.FNAME_LENGTH + ") NOT NULL,"
                    + "mi char(" + StaticData.MI_LENGTH + ")," // can be null
                    + "lname varchar(" + StaticData.LNAME_LENGTH + ") NOT NULL,"
                    + "address varchar(" + StaticData.ADDRESS_LENGTH + ") NOT NULL,"
                    + "city varchar(" + StaticData.CITY_LENGTH + ") NOT NULL,"
                    + "state varchar(" + StaticData.STATE_LENGTH + ") NOT NULL,"
                    + "zip char(" + StaticData.ZIP_LENGTH + ") NOT NULL,"
                    + "date varchar(10) NOT NULL," // 10 characters for date
                    + "matYear char(4) NOT NULL," // 4 characters for matriculation year
                    + "degree varchar(50) NOT NULL," // 50 characters for degree size
                    + "hasDiploma tinyint(1) NOT NULL," // tinyint(1) serves as boolean, but actually a byte type
                    + "hasImmunization tinyint(1) NOT NULL," // ~bool
                    + "isMatriculated tinyint(1) NOT NULL,"// ~bool
                    + "numCredits int NOT NULL,"
                    + "grandTotal int NOT NULL,"
                    + "nonGrandTotal int NOT NULL," // holds grand total for non credit courses
                    + "paidRegFee tinyint(1) NOT NULL" // bool for registration fee
                    + ");");
        } catch (ClassNotFoundException e){
            Alerts.alertError("JDBC driver not found!");
        } catch (SQLException e){
            Alerts.alertError("Error execute SQL query!");
        }
        
    }
    // method adds new entry into students table
    public static void addStudent(String ssn, String fname,
            String mi, String lname, String address, String city,
            String state, String zip, String date, String matYear, 
            String degree, int hasDiploma /* should be boolean but MySQL doesn't have boolean*/,
            int hasImmunization){
        try{
            // 1) connect driver
            Class.forName(DRIVER);
            // 2) connect database
            try(Connection connection = DriverManager.getConnection(DATABASE, LOGIN, PASSWORD)){
                // 3) create statement
                // 3.1) check if student with such ssn already exists, and if so
                // update info instead
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * "
                        + "FROM students "
                        + "WHERE ssn = " + ssn + ";");
                // if entry is empty then insert new student into students table
                if (!resultSet.next()) {
                    PreparedStatement prepStatement = connection.prepareStatement(""
                        + "INSERT INTO students ("
                        + "SSN, fname, mi, lname, address, city, state, zip,"
                        + "date, matYear, degree, hasDiploma, hasImmunization, "
                        + "isMatriculated, numCredits, grandTotal, nonGrandTotal, paidRegFee) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                    // set the parameters of the prepared statement with the parameters of the method
                    prepStatement.setString(1, ssn);
                    prepStatement.setString(2, fname);
                    prepStatement.setString(3, mi);
                    prepStatement.setString(4, lname);
                    prepStatement.setString(5, address);
                    prepStatement.setString(6, city);
                    prepStatement.setString(7, state);
                    prepStatement.setString(8, zip);
                    prepStatement.setString(9, date);
                    prepStatement.setString(10, matYear);
                    prepStatement.setString(11, degree);
                    prepStatement.setInt(12, hasDiploma);
                    prepStatement.setInt(13, hasImmunization);
                    prepStatement.setInt(14, StaticData.matriculatedSelected == true ? 1 : 0);
                    prepStatement.setInt(15, 0); // set number of credits to 0
                    prepStatement.setInt(16, 0); // set grand total to 0
                    prepStatement.setInt(17, 0); // set noncredit grant total to 0
                    prepStatement.setInt(18, 0); // paid registration fee
                    // execute prepared statement
                    prepStatement.execute();

                } else {
                    // else update info
                    
                    // try with parameters (autoclose)
                    try(RowSet rowset = new JdbcRowSetImpl();){
                        rowset.setUrl(DATABASE);
                        rowset.setUsername(LOGIN);
                        rowset.setPassword(PASSWORD);
                        // extract a row from DB
                        rowset.setCommand("SELECT * "
                                + "FROM students "
                                + "WHERE ssn = " + ssn + ";");
                        rowset.execute();
                        rowset.absolute(1); // select first row (and the only)
                        // update info
                        rowset.updateString("fname", fname);
                        rowset.updateString("mi", mi);
                        rowset.updateString("lname", lname);
                        rowset.updateString("address", address);
                        rowset.updateString("city", city);
                        rowset.updateString("state", state);
                        rowset.updateString("zip", zip);
                        // if student is non matriculated than date, matyear, degree
                        // and hasdiploma is empty
                        if (StaticData.matriculatedSelected == false){
                            date = "";
                            matYear = "";
                            degree = "";
                            hasDiploma = 0;
                            hasImmunization = 0;
                        }
                        rowset.updateString("date", date); 
                        rowset.updateString("matYear", matYear);
                        rowset.updateString("degree", degree);
                        rowset.updateInt("hasDiploma", hasDiploma);
                        rowset.updateInt("hasImmunization", hasImmunization);
                        rowset.updateInt("isMatriculated", StaticData.matriculatedSelected == true ? 1 : 0);
                        rowset.updateRow();
                    }
                }
                
            }
            
        } catch (ClassNotFoundException e){
            Alerts.alertError("JDBC driver not found!");
            System.exit(1);
        } catch (SQLException e){
            Alerts.alertError("Unsuccessfull query!");
            e.printStackTrace();
        } 
        
    }
    /**
     * Method initialized a combined table of students and courses
     */
    private static void initCombinedTable(){
        try{
            // 1) connect driver
            Class.forName(DRIVER);
            // 2) connect database
            Connection connection = DriverManager.getConnection(DATABASE, LOGIN, PASSWORD);
            // 3) create statement
            Statement statement = connection.createStatement();
            // create combined table if it does not exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS combined ("
                    + "SSN char(" + StaticData.SSN_LENGTH + ") NOT NULL,"
                    + "courseID char(7) ," // can be null - student did not register for courses yet
                    + "FOREIGN KEY (SSN) REFERENCES students(SSN),"
                    + "FOREIGN KEY (courseID) REFERENCES classes(courseID)"
                    + ");");
        
        } catch (SQLException e){
            Alerts.alertError("Error execute SQL query!");
        } catch (ClassNotFoundException e) {
            Alerts.alertError("JDBC driver not found!");
        }
        
    }
    // method adds a combined entry when student registers for courses
    // when student selected desired courses in his table view and pressed "OK" button
    public static void addCombined(Connection connection, String ssn, String courseID) throws SQLException{
        // while student is registering for classes, there will be a static variable
        // holding the vaule of SSN and then passed as a parameter
        // courseID will be taken directly from the tableview
        PreparedStatement prepStatement = connection.prepareStatement(""
                + "INSERT INTO combined (SSN, courseID)"
                + "VALUES (?, ?);");
        prepStatement.setString(1, ssn);
        prepStatement.setString(2, courseID);
        prepStatement.execute(); // add values to the table
    }
    // method populates textfield and tableview with data
    // by executing a query with the info from fname, mi, and lname textfields
    public static void getStudentData(Connection connection, String fname, String mi, String lname){
        
        try{
            // execute query
            ResultSet resultSet = (ResultSet) connection.prepareStatement(""
                + "SELECT numCredits, grandTotal FROM student "
                + "WHERE student.fname = " + fname + " "
                + "AND student.mi = " + mi + " "
                + "AND student.lname = " + lname + ");");
            while (resultSet.next()){
                // set the value of the 'number of credits' textfield
                StaticData.numberOfCreditsTF.setText(Integer.toString(resultSet.getInt("numCredits")));
                // set the value of 'grand total' text field
                StaticData.grandTotalTF.setText(Integer.toString(resultSet.getInt("grandTotal")));
            }
        } catch (SQLException e){
            // add alert 'student not found'
            System.out.println("Student not found!");
        }
        
        
    }
    // method loads registration columns onto registration tableviews
    private static void loadRegistrationColumns(){
        // add table columns to registration tableviews
        
        StaticData.allCoursesTV.getColumns().addAll(
            StaticData.courseIDTC1, // course id
            StaticData.courseNameTC1, // course name
            StaticData.dayTC1, // meeting day
            StaticData.timeTC1, // meeting time
            StaticData.placeTC1, // meeting place
            StaticData.priceTC1 // price of the course
        );
        
        StaticData.selectedCoursesTV.getColumns().addAll(
                StaticData.courseIDTC2,
                StaticData.courseNameTC2,
                StaticData.dayTC2, // meeting day
                StaticData.timeTC2, // meeting time
                StaticData.placeTC2, // meeting place
                StaticData.priceTC2 // price of the course
        );
    }
    // method loads table columns onto receivable tableviews
    private static void loadReceivablesColumns(){
        // add table columns to receivables tableviews
        StaticData.courseIDTC1.setPrefWidth(100);
        StaticData.courseIDTC2.setPrefWidth(100);
        StaticData.courseNameTC1.setPrefWidth(200);
        StaticData.courseNameTC2.setPrefWidth(200);
        StaticData.creditCoursesTV.getColumns().addAll(
                StaticData.courseIDTC1,
                StaticData.courseNameTC1
        );
        StaticData.nonCreditCoursesTV.getColumns().addAll(
                StaticData.courseIDTC2,
                StaticData.courseNameTC2
        );
        
    }
    // method loads table columns onto schedule tableview
    private static void loadScheduleColumns(){
        // add table columns to schedule tableview
        StaticData.scheduleTV.getColumns().addAll(
                StaticData.courseIDTC1,
                StaticData.courseNameTC1,
                StaticData.dayTC1,
                StaticData.timeTC1,
                StaticData.placeTC1
        );
    }
    
}
