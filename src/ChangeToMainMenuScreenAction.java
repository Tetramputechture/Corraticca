/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

/**
 * Changes the current screen to the Main Menu Screen.
 * @author Nick
 */
public class ChangeToMainMenuScreenAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "CHANGE_TO_MAIN_MENU_SCREEN";

    /**
     * Changes the current screen to the Main Menu screen.
     */
    @Override
    public void execute() {
        System.out.println("Changing screen to Main Menu Sreen!");
        Window.changeScreen(new MainMenuScreen());
    }
    
    @Override
    public String toString() {
        return NAME;
    }
    
}
