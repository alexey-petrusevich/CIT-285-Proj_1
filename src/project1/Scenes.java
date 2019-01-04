/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import javafx.scene.Scene;
import panes.EnterSSNPane;
import panes.FullTimePane;
import panes.MatriculatedPane;
import panes.NewUpdatePane;
import panes.NonCreditPane;
import panes.NonMatriculatedPane;
import panes.PartTimePane;
import panes.ReceivablesPane;
import panes.SchedulePane;
import panes.WelcomePane;

/**
 *
 * @author Aliaksei
 */
public class Scenes {
    private static Scene scene;
    private static final int SCENE_WIDTH = 975;
    private static final int SCENE_HEIGHT = 650;
    
    public static Scene getWelcomeScene(){
        scene = new Scene(WelcomePane.getWelcomePane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    public static Scene getMatriculatedScene(){
        scene = new Scene(MatriculatedPane.getMatriculatedPane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    public static Scene getNonMatriculatedScene(){
        scene = new Scene(NonMatriculatedPane.getNonMatriculatedPane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    public static Scene getFullTimeScene(){
        scene = new Scene(FullTimePane.getFullTimePane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    public static Scene getPartTimeScene(){
        scene = new Scene(PartTimePane.getPartTimePane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    public static Scene getNonCreditScene(){
        scene = new Scene(NonCreditPane.getNonCreditPane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    public static Scene getReceivablesScene(){
        scene = new Scene(ReceivablesPane.getReceivablesPane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    public static Scene getClassScheduleScene(){
        scene = new Scene(SchedulePane.getSchedulePane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    public static Scene getNewUpdateScene(){
        scene = new Scene(NewUpdatePane.getNewUpdatePane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    public static Scene getEnterSSNScene(){
        scene = new Scene(EnterSSNPane.getEnterSSNPane(), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
}
