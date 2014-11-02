/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

/**
 * @author Nick
 * Changes the current screen to the Pause Menu Screen.
 */
public class ChangeToPauseMenuScreenAction implements Action {
    
    public static final String NAME = "CHANGE_TO_PAUSE_MENU_SCREEN";

    @Override
    public void execute() {
        System.out.println("Changing screen to Pause Menu Sreen!");
        Window.changeScreen(new PauseMenuScreen());
    }
    
    @Override
    public String toString() {
        return NAME;
    }
    
}
