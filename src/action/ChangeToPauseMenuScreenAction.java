/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.action;

import coratticca.util.screen.GameScreen;
import coratticca.util.screen.PauseMenuScreen;
import coratticca.util.Window;

/**
 * Changes the current screen to the Pause Menu Screen.
 * @author Nick
 */
public class ChangeToPauseMenuScreenAction implements Action {
    
    private final GameScreen game;
    
    public ChangeToPauseMenuScreenAction(GameScreen game) {
        this.game = game;
    }

    @Override
    public void execute(Window w) {
        
        GameScreen g;
        // double check if the current screen is a gamescreen
        if (w.getCurrentScreen() instanceof GameScreen) {
            g = (GameScreen) w.getCurrentScreen();
        } else {
            return;
        }
        
        w.changeCurrentScreen(new PauseMenuScreen(game));
        g.pause();
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_PAUSE_MENU_SCREEN";
    }
}
