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
import org.jsfml.system.Vector2f;

/**
 * The Pause Menu screen.
 * @author Nick
 */
public class PauseMenuScreen implements Screen {
    
    private static final ScreenName name = ScreenName.PAUSE_MENU_SCREEN;
    
    private static final Color bgColor;
    
    private final List<Button> buttons;
    
    static {
        bgColor = GameScreen.getBGColor();
    }
    
    /**
     * Initializes the pause menu and all of it's buttons.
     */
    public PauseMenuScreen() {
        buttons = new ArrayList<>();
        
        // cursor must be visible
        Window.getWindow().setMouseCursorVisible(true);
        
        // add exit button
        buttons.add(new Button( new Vector2f(Window.getWidth()/2, 
                                Window.getHeight()/2-100),
                                24, 
                                "Exit to Main Menu", 
                                "fonts/OpenSans-Regular.ttf", 
                                Color.BLACK,
                                new ChangeToMainMenuScreenAction()));
        
        // add resume game button
        buttons.add(new Button( new Vector2f(Window.getWidth()/2,
                                Window.getHeight()/2),
                                24,
                                "Resume Game",
                                "fonts/OpenSans-Regular.ttf",
                                Color.BLACK,
                                new ChangeToGameScreenAction(false)));
    }
   
    /**
     * Shows the Pause Screen and all it's buttons. 
     */
    @Override
    public void show() {
        
        Window.getWindow().clear(bgColor);
        
        for (Button b : buttons) {
            b.draw();
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
