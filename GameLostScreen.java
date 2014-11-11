/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jsfml.graphics.Color;

/**
 *
 * @author Nick
 */
public class GameLostScreen implements Screen {
    
    private static final Color bgColor;
    
    private final List<Button> buttons;
    
    static {
        bgColor = Color.BLACK;
    }
    
    /**
     * Initializes the pause menu and all of it's buttons.
     */
    public GameLostScreen() {
        buttons = new ArrayList<>();
        
        // cursor must be visible
        Window.getWindow().setMouseCursorVisible(true);
        
        // add game lost text
        buttons.add(new Button(Window.getWidth()/2,
                                Window.getHeight()/2 - 50,
                                48,
                                "Game Lost! :(",
                                "OpenSans-Regular.ttf",
                                Color.WHITE,
                                null));
        
        // add exit button
        buttons.add(new Button(Window.getWidth()/2, 
                                Window.getHeight()/2 + 50,
                                24, 
                                "Exit to Main Menu", 
                                "OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ChangeToMainMenuScreenAction()));
        
        // add new game button
        buttons.add(new Button(Window.getWidth()/2,
                                Window.getHeight()/2 + 100,
                                24,
                                "New Game",
                                "OpenSans-Regular.ttf",
                                Color.WHITE,
                                new ChangeToGameScreenAction(true)));
        
        // add stats text
        buttons.add(new Button(Window.getWidth() - 100,
                                Window.getHeight()/2 + 200,
                                18,
                                String.format("Enemies killed: %s\n"
                                        + "Shots fired: %s\n"
                                        + "Accuracy: %.4f", 
                                        GameScreen.getEnemiesKilled(), 
                                        GameScreen.getShotsFired(), 
                                        GameScreen.getAccuracy()),
                                "OpenSans-Regular.ttf",
                                Color.WHITE,
                                null));
    }

    @Override
    public void show() {
        
        Window.getWindow().clear(bgColor);
        
        for (Button i : buttons) {
            i.draw();
        }

        Window.getWindow().display();
    }

    /**
     * Gets the Game Lost Screen's buttons.
     * @return a List containing all the buttons of the Game Lost Screen.
     */
    @Override
    public List<Button> getButtons() {
        return Collections.unmodifiableList(buttons);
    }
    
    @Override
    public String toString() {
        return "GAME_LOST";
    }
    
}
