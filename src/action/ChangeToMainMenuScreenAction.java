/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.action;

import coratticca.util.screen.MainMenuScreen;
import coratticca.util.Window;

/**
 * Changes the current screen to the Main Menu Screen.
 * @author Nick
 */
public class ChangeToMainMenuScreenAction implements Action {

    @Override
    public void execute(Window w) {
        w.changeCurrentScreen(new MainMenuScreen(w));
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_MAIN_MENU_SCREEN";
    }
}
