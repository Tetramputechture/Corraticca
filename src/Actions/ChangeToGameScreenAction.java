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
    
    private final GameScreen game;
    
    /**
     * The name of the action.
     */
    public static final String NAME = "CHANGE_TO_GAME_SCREEN";
    
    public ChangeToGameScreenAction() {
        game = null;
    }
    
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
    public String toString() {
        return NAME;
    }
    
}
