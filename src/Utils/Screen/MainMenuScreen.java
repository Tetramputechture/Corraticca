/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils.Screen;

import coratticca.Utils.Window;
import coratticca.Utils.Button;
import coratticca.Actions.ExitGameAction;
import coratticca.Actions.ChangeToGameScreenAction;
import java.util.Collections;
import java.util.List;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;

/**
 * The Main Menu screen.
 * @author Nick
 */
public class MainMenuScreen extends Screen {

    public MainMenuScreen(Window w) {
        super(w);
        setBgColor(Color.BLACK);
        
        // add play button
        window.getRenderWindow().setMouseCursorVisible(true);
        buttons.add(new Button( window,
                                new Vector2f(window.getSize().x/2, 
                                window.getSize().y/2-80),
                                52, 
                                "Play!", 
                                "fonts/OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ChangeToGameScreenAction()));
        
        buttons.add(new Button( window,
                                new Vector2f(window.getSize().x/2, 
                                window.getSize().y/2+20),
                                52, 
                                "Exit!", 
                                "fonts/OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ExitGameAction()));
    }
   
    @Override
    public void show() {
        window.getRenderWindow().clear(getBgColor());
        
        for (Button b : buttons) {
            b.draw();
        }

        window.getRenderWindow().display();
    }
    
    @Override
    public List<Button> getButtons() {
        return Collections.unmodifiableList(buttons);
    }
    
    @Override
    public ScreenName getName() {
        return ScreenName.MAIN_MENU_SCREEN;
    }  
}
