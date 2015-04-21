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
   
    public ChangeToPauseMenuScreenAction(GameScreen game) {
        this.game = game;
    }

    @Override
    public void execute() {
        Window.setCurrentScreen(new PauseMenuScreen(game));
        game.pause();
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_PAUSE_MENU_SCREEN";
    }
}
