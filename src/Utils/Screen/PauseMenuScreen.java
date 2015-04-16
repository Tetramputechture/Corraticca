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
public class PauseMenuScreen extends Screen {
    
    private final Sprite backgroundSprite;
    
    public PauseMenuScreen(GameScreen game) {
        super(game.getWindow());
        
        // cursor must be visible
        super.window.getRenderWindow().setMouseCursorVisible(true);
        
        // add exit button
        buttons.add(new Button( super.window,
                                new Vector2f(super.window.getSize().x/2, 
                                super.window.getSize().y/2-100),
                                24, 
                                "Exit to Main Menu", 
                                "fonts/OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ChangeToMainMenuScreenAction()));
        
        // add resume game button
        buttons.add(new Button( super.window,
                                new Vector2f(super.window.getSize().x/2,
                                super.window.getSize().y/2 + 100),
                                24,
                                "Resume Game",
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                new ChangeToGameScreenAction(game)));
        
        // init background sprite
        
        Texture backgroundTexture = new Texture();
        try {
            backgroundTexture.loadFromImage(((GameScreen)super.window.getCurrentScreen()).getBGImage());
        } catch (TextureCreationException ex) {
            Logger.getLogger(PauseMenuScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        backgroundSprite = new Sprite(backgroundTexture);
    }
   
    @Override
    public void show() {
        super.window.getRenderWindow().clear();
        
        super.window.getRenderWindow().draw(backgroundSprite);
        
        for (Button b : buttons) {
            b.draw();
        }

        super.window.getRenderWindow().display();
    }  
    
    @Override
    public ScreenName getName() {
        return ScreenName.PAUSE_MENU_SCREEN;
    }
}
