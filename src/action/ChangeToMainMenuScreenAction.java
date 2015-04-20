package coratticca.action;

import coratticca.util.screen.MainMenuScreen;
import coratticca.util.Window;

/**
 * Changes the current screen to the Main Menu Screen.
 * @author Nick
 */
public class ChangeToMainMenuScreenAction extends Action {
    
    public ChangeToMainMenuScreenAction(Window window) {
        super(window);
    }
    
    @Override
    public void execute() {
        window.changeCurrentScreen(new MainMenuScreen(window));
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_MAIN_MENU_SCREEN";
    }
}
