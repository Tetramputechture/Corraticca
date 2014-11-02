/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

/**
 * @author Nick
 * This interface handles every action in the game, so keys can easily be bound
 * to any action. Uses the Command design pattern.
 */
public interface Action {
    
    void execute();
    
    @Override
    String toString();
    
}
