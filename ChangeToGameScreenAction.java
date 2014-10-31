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
public class ChangeToGameScreenAction implements Action {
    
    public static final String NAME = "CHANGE_TO_GAME_SCREEN_ACTION";

    @Override
    public void execute() {
        System.out.println("Changing screen to Game Sreen!");
        Window.changeScreen(new GameScreen());
    }
    
    @Override
    public String toString() {
        return NAME;
    }
    
}
