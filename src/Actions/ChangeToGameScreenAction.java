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
public class ChangeToGameScreenAction extends Action {
    
    private final GameScreen game;
    
    /**
     * Changes the screen to a new instance of a gameScreen.
     */
    public ChangeToGameScreenAction() {
        game = null;
    }
    
    /**
     * Changes the screen to the gameScreen specified.
     * @param game the gameScreen to be changed to.
     */
    public ChangeToGameScreenAction(GameScreen game) {
        this.game = game;
    }

    @Override
    public void execute(Window w) {
        if (game != null) {
            w.changeScreen(game);
            game.resume();
        } else {
            GameScreen g = new GameScreen(w);
            g.initPlayer();
            g.initButtons();
            w.changeScreen(g);
        }
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_GAME_SCREEN";
    }
}
