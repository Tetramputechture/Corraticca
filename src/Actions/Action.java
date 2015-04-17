/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Utils.Window;

/**
 * This interface handles every action in the game, so keys can easily be bound
 * to any action. Uses the Command design pattern.
 * @author Nick
 */
public abstract class Action {
    
    /**
     * executes the action.
     * 
     * @param window the window to handle the action
     */
    public abstract void execute(Window window);
    
    /**
     * Returns the name of the action.
     * @return the name of the action.
     */
    public abstract String getName();
    
    @Override
    public String toString() {
        return getName();
    }
    
}
