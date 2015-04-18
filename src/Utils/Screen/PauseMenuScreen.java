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
    
    private final GameScreen previousGameScreen;
    
    public PauseMenuScreen(GameScreen game) {
        super(game.getWindow());
        
        previousGameScreen = game;
        
        // cursor must be visible
        window.getRenderWindow().setMouseCursorVisible(true);
        
        Vector2f winSize = window.getSize();
        
        // add exit button
        buttons.add(new Button( window,
                                new Vector2f(winSize.x/2, 
                                winSize.y/2-100),
                                24, 
                                "Exit to Main Menu", 
                                "fonts/OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ChangeToMainMenuScreenAction()));
        
        // add resume game button
        buttons.add(new Button( window,
                                new Vector2f(winSize.x/2,
                                winSize.y/2 + 100),
                                24,
                                "Resume Game",
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                new ChangeToGameScreenAction(game)));
        
        // init background sprite
        
        Texture backgroundTexture = new Texture();
        try {
            backgroundTexture.loadFromImage(game.getBGImage());
        } catch (TextureCreationException ex) {
            Logger.getLogger(PauseMenuScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        backgroundSprite = new Sprite(backgroundTexture);
    }
    
    public GameScreen getPreviousGameScreen() {
        return previousGameScreen;
    }
   
    @Override
    public void show() {
        window.getRenderWindow().clear();
        
        window.getRenderWindow().draw(backgroundSprite);
        
        for (Button b : buttons) {
            b.draw();
        }

        window.getRenderWindow().display();
    }  
    
    @Override
    public ScreenName getName() {
        return ScreenName.PAUSE_MENU_SCREEN;
    }
}
