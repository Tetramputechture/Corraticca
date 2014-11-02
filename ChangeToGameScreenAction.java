/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

/**
 * @author Nick
 * Changes the current screen to the Game Screen.
 */
public class ChangeToGameScreenAction implements Action {
    
    public static final String NAME = "CHANGE_TO_GAME_SCREEN";
    
    private final boolean resetGame;
    
    public ChangeToGameScreenAction(boolean resetGame) {
        this.resetGame = resetGame;
    }

    @Override
    public void execute() {
        System.out.println("Changing screen to Game Sreen!");
        Window.changeScreen(new GameScreen(resetGame));
    }
    
    @Override
    public String toString() {
        return NAME;
    }
    
}
