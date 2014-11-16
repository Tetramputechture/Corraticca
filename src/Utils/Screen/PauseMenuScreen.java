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
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Vector2f;

/**
 * The Pause Menu screen.
 * @author Nick
 */
public class PauseMenuScreen implements Screen {
    
    private static final ScreenName name = ScreenName.PAUSE_MENU_SCREEN;
    
    //private static final Color bgColor;
    private final Sprite backgroundSprite;
    
    private final List<Button> buttons;
    
    
    /**
     * Initializes the pause menu and all of it's buttons.
     */
    public PauseMenuScreen() {
        buttons = new ArrayList<>();
        
        // cursor must be visible
        Window.getWindow().setMouseCursorVisible(true);
        
        // add exit button
        buttons.add(new Button( new Vector2f(Window.getSize().x/2, 
                                Window.getSize().y/2-100),
                                24, 
                                "Exit to Main Menu", 
                                "fonts/OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ChangeToMainMenuScreenAction(),
                                false));
        
        // add resume game button
        buttons.add(new Button( new Vector2f(Window.getSize().x/2,
                                Window.getSize().y/2 + 100),
                                24,
                                "Resume Game",
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                new ChangeToGameScreenAction(false),
                                false));
        
        // init background sprite
        
        Texture backgroundTexture = new Texture();
        try {
            backgroundTexture.loadFromImage(((GameScreen)Window.getCurrentScreen()).getBGImage());
        } catch (TextureCreationException ex) {
            Logger.getLogger(PauseMenuScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        backgroundSprite = new Sprite(backgroundTexture);
    }
   
    /**
     * Shows the Pause Screen and all it's buttons. 
     */
    @Override
    public void show() {
        
        Window.getWindow().clear();
        
        Window.getWindow().draw(backgroundSprite);
        
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
