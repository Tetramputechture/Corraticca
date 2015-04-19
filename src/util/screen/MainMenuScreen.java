/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.util.screen;

import coratticca.util.Window;
import coratticca.util.widget.CButton;
import coratticca.action.ExitGameAction;
import coratticca.action.ChangeToGameScreenAction;
import coratticca.util.CPrecache;
import coratticca.util.widget.CFrame;
import coratticca.util.widget.CWidget;
import coratticca.util.widget.widgetListener.CSelectListener;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

/**
 * The Main Menu screen.
 * @author Nick
 */
public class MainMenuScreen extends Screen {

    public MainMenuScreen(Window w) {
        super(Color.BLACK);
        
        int halfWidth = 320;
        int halfHeight = 240;
        
        Font font = CPrecache.getOpenSansFont();
        int fontSize = 52;

        Text playText = new Text("Play!", font, fontSize);
        playText.setColor(Color.WHITE);
        
        Text exitText = new Text("Exit!", font, fontSize);
        exitText.setColor(Color.WHITE);
        
        CButton playButton = new CButton(new Vector2f(halfWidth, halfHeight-80),
                                playText,
                                null,
                                Color.RED,
                                Color.TRANSPARENT);
        playButton.addClickListener((CButton b) -> { 
            new ChangeToGameScreenAction().execute(w);
        });
        playButton.addSelectListener(new CSelectListener() {

            @Override
            public void select(CButton button) {
                button.setTextColor(Color.RED);
            }

            @Override
            public void unselect(CButton button) {
                button.setTextColor(Color.WHITE);
            }
           
        });
        
        widgets.add(playButton);
        
        CButton exitButton = new CButton(new Vector2f(halfWidth, halfHeight+20),
                                exitText,
                                null,
                                Color.RED,
                                Color.TRANSPARENT);
        exitButton.addClickListener((CButton b) -> {
            new ExitGameAction().execute(w);
        });
        
        widgets.add(exitButton);
    }
   
    
    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        // need to show the cursor
        ((org.jsfml.window.Window)rt).setMouseCursorVisible(true);
        
        rt.clear(bgColor);
        
        for (CWidget w : widgets) {
            w.draw(rt, states);
        }
        ((org.jsfml.window.Window)rt).display();
    }
    
    @Override
    public ScreenName getName() {
        return ScreenName.MAIN_MENU_SCREEN;
    }  
}
