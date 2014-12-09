/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.Screen.PauseMenuScreen;
import coratticca.Utils.Window;

/**
 * Changes the current screen to the Pause Menu Screen.
 * @author Nick
 */
public class ChangeToPauseMenuScreenAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "CHANGE_TO_PAUSE_MENU_SCREEN";

    /**
     * Changes the current screen to the Pause Menu screen.
     */
    @Override
    public void execute() {
        Window.changeScreen(new PauseMenuScreen());
        GameScreen.pauseGame();
    }
    
    @Override
    public String toString() {
        return NAME;
    }
    
}
