/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

/**
 * This interface handles every action in the game, so keys can easily be bound
 * to any action. Uses the Command design pattern.
 * @author Nick
 */
public interface Action {
    
    /**
     * executes the action.
     */
    void execute();
    
    /**
     * @return the string representation of the action
     */
    @Override
    String toString();
    
}
