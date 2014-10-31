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
public class GameScreen implements Screen {

    private static final Color bgColor;

    private static final int numButtons;

    private static final Button[] buttons;
    
    // private static Level currentLevel;

    static {
        numButtons = 0;
        buttons = new Button[numButtons];
        bgColor = Color.WHITE;
    }

    @Override
    public void show() {

        Window.getWindow().clear(bgColor);
        
        Player.draw(Window.getWidth()/2, Window.getHeight()/2);

        Window.getWindow().display();

    }

    @Override
    public Button[] getButtons() {
        return buttons;
    }
    

    public static Color getBGColor() {
        return bgColor;
    }

    @Override
    public String toString() {
        return "GAME";
    }
}
