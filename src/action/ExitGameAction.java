package coratticca.action;

import coratticca.util.Window;

/**
 *
 * @author Nick
 */
public class ExitGameAction implements Action {

    @Override
    public void execute(Window w) {
        w.getRenderWindow().close();
    }
    
    @Override
    public String getName() {
        return "EXIT_GAME";
    }
}
