package coratticca.action;

import coratticca.util.Window;

/**
 *
 * @author Nick
 */
public class ExitGameAction extends Action {
    
    public ExitGameAction(Window window) {
        super(window);
    }

    @Override
    public void execute() {
        window.getRenderWindow().close();
    }
    
    @Override
    public String getName() {
        return "EXIT_GAME";
    }
}
