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
    
    private static final ScreenName name = ScreenName.MAIN_MENU_SCREEN;
    
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
                                Window.getHeight()/2-80,
                                52, 
                                "Play!", 
                                "OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ChangeToGameScreenAction(true)));
        
        buttons.add(new Button(Window.getWidth()/2, 
                                Window.getHeight()/2+20,
                                52, 
                                "Exit!", 
                                "OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ExitGameAction()));
    }
   
    /**
     * Shows the Main Menu screen and all it's buttons.
     */
    @Override
    public void show() {
        
        Window.getWindow().clear(bgColor);
        
        for (Button b : buttons) {
            b.draw();
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
    
    @Override
    public ScreenName getName() {
        return name;
    }
    
}
