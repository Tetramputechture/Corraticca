/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;

/**
 *
 * @author Nick
 */
public class PauseMenuScreen implements Screen {
    
    private Color bgColor;
    
    private Font menuFont;
    
    private final int numButtons = 2;
    
    private final Button[] buttons = new Button[numButtons];
    
    public PauseMenuScreen() {
        Window.getWindow().setMouseCursorVisible(true);
        buttons[0] = new Button(Window.getWidth()/2, 
                                Window.getHeight()/2-100,
                                24, 
                                "Exit to Main Menu", 
                                "OpenSans-Regular.ttf", 
                                Color.BLACK,
                                new ChangeToMainMenuScreenAction());
        
        buttons[1] = new Button(Window.getWidth()/2,
                                Window.getHeight()/2,
                                24,
                                "Resume Game",
                                "OpenSans-Regular.ttf",
                                Color.BLACK,
                                new ChangeToGameScreenAction());
    }
   

    @Override
    public void show() {
        
        bgColor = GameScreen.getBGColor();
        
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
        return "PAUSE_MENU";
    }
    
}
