/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.util.screen;

import coratticca.util.widget.CButton;
import coratticca.action.ChangeToGameScreenAction;
import coratticca.action.ChangeToMainMenuScreenAction;
import coratticca.util.CPrecache;
import coratticca.util.widget.CWidget;
import coratticca.util.widget.widgetListener.CClickListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
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
        super(Color.BLACK);
        
        previousGameScreen = game;
        
        int halfWidth = 320;
        int halfHeight = 240;
        
        Font font = CPrecache.getOpenSansFont();
        int fontSize = 24;
        
        Text exitText = new Text("Exit to Main Menu", font, fontSize);
        exitText.setColor(Color.WHITE);
        
        Text resumeText = new Text("Resume Game", font, fontSize);
        resumeText.setColor(Color.WHITE);
        
        // add exit button
        CButton exitButton = new CButton(new Vector2f(halfWidth, halfHeight-100),
                                exitText,
                                null,
                                Color.WHITE,
                                Color.TRANSPARENT);
        exitButton.addClickListener((CButton b) -> {
            System.out.println("Changing to main menu...");
        });
        widgets.add(exitButton);
        
        // add resume game button
        CButton resumeButton = new CButton(new Vector2f(halfWidth, halfHeight + 100),
                                resumeText,
                                null,
                                Color.WHITE,
                                Color.TRANSPARENT);
        resumeButton.addClickListener((CButton b) -> {
            new ChangeToGameScreenAction(game).execute(game.getWindow());
        });
        widgets.add(resumeButton);
        
        // init background sprite
        
        Texture backgroundTexture = new Texture();
        try {
            backgroundTexture.loadFromImage(game.getBGImage());
        } catch (TextureCreationException ex) {
            Logger.getLogger(PauseMenuScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        backgroundSprite = new Sprite(backgroundTexture);
    }
    
    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        // need to show the cursor
        ((org.jsfml.window.Window)rt).setMouseCursorVisible(true);
        rt.clear(bgColor);
        backgroundSprite.draw(rt, states);
        
        for (CWidget w : widgets) {
            w.draw(rt, states);
        }
        ((org.jsfml.window.Window)rt).display();
    }
    
    public GameScreen getPreviousGameScreen() {
        return previousGameScreen;
    }
   
    @Override
    public ScreenName getName() {
        return ScreenName.PAUSE_MENU_SCREEN;
    }


}
