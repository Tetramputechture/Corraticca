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
import org.jsfml.graphics.Font;

/**
 *
 * @author Nick
 */
public class PauseMenuScreen implements Screen {
    
    private static final Color bgColor;
    
    private final List<Button> buttons;
    
    static {
        bgColor = GameScreen.getBGColor();
    }
    
    public PauseMenuScreen() {
        buttons = new ArrayList<>();
        
        Window.getWindow().setMouseCursorVisible(true);
        buttons.add(new Button(Window.getWidth()/2, 
                                Window.getHeight()/2-100,
                                24, 
                                "Exit to Main Menu", 
                                "OpenSans-Regular.ttf", 
                                Color.BLACK,
                                new ChangeToMainMenuScreenAction()));
        
        buttons.add(new Button(Window.getWidth()/2,
                                Window.getHeight()/2,
                                24,
                                "Resume Game",
                                "OpenSans-Regular.ttf",
                                Color.BLACK,
                                new ChangeToGameScreenAction(false)));
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
    public String toString() {
        return "PAUSE_MENU";
    }
    
}
