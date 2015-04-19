package coratticca.util.screen;

import coratticca.action.ChangeToGameScreenAction;
import coratticca.action.ChangeToMainMenuScreenAction;
import coratticca.util.widget.CButton;
import coratticca.util.CPrecache;
import coratticca.util.CText;
import coratticca.util.widget.CStandardSelectListener;
import coratticca.util.widget.CWidget;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public class GameLostScreen extends Screen {
    
    public GameLostScreen(GameScreen g) {
        
        super(Color.BLACK);
        
        int halfWidth = 320;
        int halfHeight = 240;
        
        Font font = CPrecache.getOpenSansFont();
        int fontSize = 24;
        
        Text gameLostText = new Text("Game Lost! :(", font, 72);
        gameLostText.setColor(Color.WHITE);
        CText.setOriginToCenter(gameLostText);
        
        Text exitText = new Text("Exit To Main Menu", font, fontSize);
        exitText.setColor(Color.WHITE);
        CText.setOriginToCenter(exitText);
        
        Text newGameText = new Text("New Game", font, fontSize);
        newGameText.setColor(Color.WHITE);
        CText.setOriginToCenter(newGameText);

        Text statsText = new Text(String.format("Asteroids blasted: %s\n"
                                        + "Shots fired: %s\n"
                                        + "Accuracy: %.4f", 
                                        g.getAsteroidsBlasted(), 
                                        g.getShotsFired(), 
                                        g.getAccuracy()),
                                  font,
                                  fontSize-5);
        statsText.setColor(Color.WHITE);
        CText.setOriginToCenter(statsText);
        
        // add game lost text
        CWidget gameLostWidget = new CWidget(new Vector2f(halfWidth,
                                halfHeight - 50),
                                gameLostText,
                                null);
        widgets.add(gameLostWidget);
        
        // add exit button
        CButton exitButton = new CButton(new Vector2f(halfWidth, 
                                halfHeight + 50),
                                exitText,
                                null,
                                Color.TRANSPARENT,
                                Color.TRANSPARENT);
        exitButton.addClickListener((CButton b) -> {
            new ChangeToMainMenuScreenAction().execute(g.getWindow());
        });
        exitButton.addSelectListener(new CStandardSelectListener(Color.RED, g.getWindow()));
        widgets.add(exitButton);
        
        // add new game button
        CButton newGameButton = new CButton(new Vector2f(halfWidth,
                                halfHeight + 100),
                                newGameText,
                                null,
                                Color.TRANSPARENT,
                                Color.TRANSPARENT);
        newGameButton.addClickListener((CButton b) -> {
            new ChangeToGameScreenAction().execute(g.getWindow());
        });
        newGameButton.addSelectListener(new CStandardSelectListener(Color.RED, g.getWindow()));
        widgets.add(newGameButton);
        
        
        // add stats text
        CWidget statsWidget = new CWidget(new Vector2f(halfWidth*2 - 100,
                                halfHeight + 200),
                                statsText,
                                null);
        widgets.add(statsWidget);
        
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
        return ScreenName.GAME_LOST_SCREEN;
    }
}
