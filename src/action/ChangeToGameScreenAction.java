package coratticca.action;

import coratticca.util.screen.GameScreen;
import coratticca.util.Window;

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
        this(null);
    }
    
    /**
     * Changes the screen to the gameScreen specified.
     * @param game the gameScreen to be changed to.
     */
    public ChangeToGameScreenAction(GameScreen game) {
        this.game = game;
    }

    @Override
    public void execute() {
        if (game != null) {
            Window.setCurrentScreen(game);
            game.resume();
        } else {
            GameScreen g = new GameScreen();
            Window.setCurrentScreen(g);
        }
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_GAME_SCREEN";
    }
}
