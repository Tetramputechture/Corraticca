/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

/**
 *
 * @author Nick
 */
public class ExitGameAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "EXIT_GAME";

    /**
     * Changes the screen to the game screen.
     */
    @Override
    public void execute() {
        System.out.println("Exiting game!");
        Window.getWindow().close();
    }
    
    @Override
    public String toString() {
        return NAME;
    }
    
}
