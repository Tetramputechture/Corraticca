package coratticca.action;

import coratticca.util.Window;

/**
 * This interface handles every action in the game, so keys can easily be bound
 * to any action. Uses the Command design pattern.
 * @author Nick
 */
public abstract class Action {
    
    protected Window window;
    
    public Action(Window window) {
        this.window = window;
    }
    
    /**
     * executes the action.
     */
    public abstract void execute();
    
    /**
     * Returns the name of the action.
     * @return the name of the action.
     */
    public abstract String getName();
}
