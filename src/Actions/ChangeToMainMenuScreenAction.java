/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Utils.Screen.MainMenuScreen;
import coratticca.Utils.Window;

/**
 * Changes the current screen to the Main Menu Screen.
 * @author Nick
 */
public class ChangeToMainMenuScreenAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "CHANGE_TO_MAIN_MENU_SCREEN";

    @Override
    public void execute(Window w) {
        w.changeScreen(new MainMenuScreen(w));
    }
    
    @Override
    public String toString() {
        return NAME;
    }
    
}
