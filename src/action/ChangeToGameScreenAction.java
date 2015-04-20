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
     * @param window the window for this Action to execute on.
     */
    public ChangeToGameScreenAction(Window window) {
        super(window);
        game = null;
    }
    
    /**
     * Changes the screen to the gameScreen specified.
     * @param window the window for this Action to execute on.
     * @param game the gameScreen to be changed to.
     */
    public ChangeToGameScreenAction(Window window, GameScreen game) {
        super(window);
        this.game = game;
    }

    @Override
    public void execute() {
        if (game != null) {
            window.changeCurrentScreen(game);
            game.resume();
        } else {
            GameScreen g = new GameScreen(window);
            window.changeCurrentScreen(g);
        }
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_GAME_SCREEN";
    }
}
