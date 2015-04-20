package coratticca.action;

import coratticca.util.screen.GameScreen;
import coratticca.util.screen.PauseMenuScreen;
import coratticca.util.Window;

/**
 * Changes the current screen to the Pause Menu Screen.
 * @author Nick
 */
public class ChangeToPauseMenuScreenAction extends Action {
    
    private final GameScreen game;
    
    public ChangeToPauseMenuScreenAction(Window window, GameScreen game) {
        super(window);
        this.game = game;
    }

    @Override
    public void execute() {
        
        GameScreen g;
        // double check if the current screen is a gamescreen
        if (window.getCurrentScreen() instanceof GameScreen) {
            g = (GameScreen) window.getCurrentScreen();
        } else {
            return;
        }
        
        window.changeCurrentScreen(new PauseMenuScreen(game));
        g.pause();
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_PAUSE_MENU_SCREEN";
    }
}
