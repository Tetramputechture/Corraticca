/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Color;

/**
 *
 * @author Nick
 */
public class MainMenuScreen implements Screen {
    
    private static final Color bgColor;
    
    private static final int numButtons;
    
    private static final Button[] buttons;
    
    static {
        numButtons = 1;
        buttons = new Button[numButtons];
        bgColor = Color.BLACK;
    }
    
    public MainMenuScreen() {
        buttons[0] = new Button(Window.getWidth()/2, 
                                Window.getHeight()/2-40,
                                104, 
                                "Play!", 
                                "OpenSans-Regular.ttf", 
                                Color.RED,
                                new ChangeToGameScreenAction());
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
    public Button[] getButtons() {
        return buttons;
    }
    
    @Override
    public String toString() {
        return "MAIN_MENU";
    }
    
}
