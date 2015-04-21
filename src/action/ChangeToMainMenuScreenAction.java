package coratticca.action;

import coratticca.util.screen.MainMenuScreen;
import coratticca.util.Window;

/**
 * Changes the current screen to the Main Menu Screen.
 * @author Nick
 */
public class ChangeToMainMenuScreenAction extends Action {
    
    @Override
    public void execute() {
        Window.setCurrentScreen(new MainMenuScreen());
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_MAIN_MENU_SCREEN";
    }
}
