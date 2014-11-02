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
 * The Main Menu screen.
 * @author Nick
 */
public class MainMenuScreen implements Screen {
    
    private static final Color bgColor;
    
    private final List<Button> buttons;
    
    static {
        bgColor = Color.BLACK;
    }
    
    /**
     * Initializes the screen, and adds the buttons.
     */
    public MainMenuScreen() {
        buttons = new ArrayList<>();
        
        // add play button
        Window.getWindow().setMouseCursorVisible(true);
        buttons.add(new Button(Window.getWidth()/2, 
                                Window.getHeight()/2-40,
                                104, 
                                "Play!", 
                                "OpenSans-Regular.ttf", 
                                Color.RED,
                                new ChangeToGameScreenAction(true)));
    }
   
    /**
     * Shows the Main Menu screen and all it's buttons.
     */
    @Override
    public void show() {
        
        Window.getWindow().clear(bgColor);
        
        for (Button i : buttons) {
            i.draw();
        }

        Window.getWindow().display();
    }
    
    /**
     * Gets the Main Menu's buttons. 
     * @return a List containing the main menu's buttons.
     */
    @Override
    public List<Button> getButtons() {
        return Collections.unmodifiableList(buttons);
    }
    
    @Override
    public String toString() {
        return "MAIN_MENU";
    }
    
}
