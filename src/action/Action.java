package coratticca.action;

import coratticca.util.Window;

/**
 * This interface handles every action in the game, so keys can easily be bound
 * to any action. Uses the Command design pattern.
 * @author Nick
 */
public interface Action {
    
    /**
     * executes the action.
     * 
     * @param window the window to handle the action
     */
    public void execute(Window window);
    
    /**
     * Returns the name of the action.
     * @return the name of the action.
     */
    public String getName();
}
