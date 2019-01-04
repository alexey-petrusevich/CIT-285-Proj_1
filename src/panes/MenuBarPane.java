/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panes;

import javafx.scene.control.MenuBar;

/**
 * Class contains elements of Menu Bar. Contains method getMenuBar that returns 
 * an instance of Menu Bar.
 * @author Aliaksei
 */
public class MenuBarPane {
    
    /**
     * Method returns a menu bar with Admissions, Registration, and Reports menus on it.
     * @return menu bar with three menus.
     */
    public static MenuBar getMenuBar(){
        // return MenuBar with menu
        return StaticData.MENU_BAR;
    }
    
}
