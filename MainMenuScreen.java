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
public class MainMenuScreen implements Screen {
    
    private static final Color bgColor;
    
    private final List<Button> buttons;
    
    static {
        bgColor = Color.BLACK;
    }
    
    public MainMenuScreen() {
        buttons = new ArrayList<>();
        
        Window.getWindow().setMouseCursorVisible(true);
        buttons.add(new Button(Window.getWidth()/2, 
                                Window.getHeight()/2-40,
                                104, 
                                "Play!", 
                                "OpenSans-Regular.ttf", 
                                Color.RED,
                                new ChangeToGameScreenAction(true)));
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
        return "MAIN_MENU";
    }
    
}
