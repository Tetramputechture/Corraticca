package coratticca.action;

import coratticca.window.Window;

/**
 *
 * @author Nick
 */
public class ExitGameAction extends Action {

    @Override
    public void execute() {
        Window.getRenderWindow().close();
    }

    @Override
    public String getName() {
        return "EXIT_GAME";
    }
}
