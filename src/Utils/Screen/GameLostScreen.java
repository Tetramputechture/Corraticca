/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils.Screen;

import coratticca.Utils.Window;
import coratticca.Utils.Button;
import coratticca.Actions.ChangeToGameScreenAction;
import coratticca.Actions.ChangeToMainMenuScreenAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public class GameLostScreen implements Screen {
    
    private static final ScreenName name = ScreenName.GAME_LOST_SCREEN;
    
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
        buttons.add(new Button( new Vector2f(Window.getSize().x/2,
                                Window.getSize().y/2 - 50),
                                48,
                                "Game Lost! :(",
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                null,
                                false));
        
        // add exit button
        buttons.add(new Button( new Vector2f(Window.getSize().x/2, 
                                Window.getSize().y/2 + 50),
                                24, 
                                "Exit to Main Menu", 
                                "fonts/OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ChangeToMainMenuScreenAction(),
                                false));
        
        // add new game button
        buttons.add(new Button( new Vector2f(Window.getSize().x/2,
                                Window.getSize().y/2 + 100),
                                24,
                                "New Game",
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                new ChangeToGameScreenAction(true),
                                false));
        
        // add stats text
        buttons.add(new Button( new Vector2f(Window.getSize().x - 100,
                                Window.getSize().y/2 + 200),
                                18,
                                String.format("Asteroids blasted: %s\n"
                                        + "Shots fired: %s\n"
                                        + "Accuracy: %.4f", 
                                        GameScreen.getAsteroidsBlasted(), 
                                        GameScreen.getShotsFired(), 
                                        GameScreen.getAccuracy()),
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                null,
                                false));
    }

    @Override
    public void show() {
        
        Window.getWindow().clear(bgColor);
        
        for (Button i : buttons) {
            i.draw();
        }

        Window.getWindow().display();
    }

    @Override
    public List<Button> getButtons() {
        return Collections.unmodifiableList(buttons);
    }

    @Override
    public ScreenName getName() {
        return name;
    } 
}
