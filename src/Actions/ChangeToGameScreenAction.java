/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.Window;

/**
 * Changes the current screen to the Game Screen.
 * @author Nick
 */
public class ChangeToGameScreenAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "CHANGE_TO_GAME_SCREEN";
    
    private final boolean resetGame;
    
    /**
     * Sets if the game screen is to be reset or not.
     * @param resetGame if the game is to be reset.
     */
    public ChangeToGameScreenAction(boolean resetGame) {
        this.resetGame = resetGame;
    }

    /**
     * Changes the screen to the game screen.
     */
    @Override
    public void execute() {
        Window.changeScreen(new GameScreen(resetGame));
        GameScreen.resumeGame();
    }
    
    @Override
    public String toString() {
        return NAME;
    }
    
}
