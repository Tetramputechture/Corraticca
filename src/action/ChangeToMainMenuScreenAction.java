package coratticca.action;

import coratticca.util.screen.MainMenuScreen;
import coratticca.util.Window;

/**
 * Changes the current screen to the Main Menu Screen.
 * @author Nick
 */
public class ChangeToMainMenuScreenAction implements Action {

    @Override
    public void execute(Window w) {
        w.changeCurrentScreen(new MainMenuScreen(w));
    }
    
    @Override
    public String getName() {
        return "CHANGE_TO_MAIN_MENU_SCREEN";
    }
}
