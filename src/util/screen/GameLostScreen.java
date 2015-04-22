package coratticca.util.screen;

import coratticca.action.ChangeToGameScreenAction;
import coratticca.action.ChangeToMainMenuScreenAction;
import coratticca.util.widget.Button;
import coratticca.util.Precache;
import coratticca.util.TextUtils;
import coratticca.util.Window;
import coratticca.util.widget.Label;
import coratticca.util.widget.Widget;
import coratticca.util.widget.widgetListener.ButtonAdapter;
import coratticca.util.widget.widgetListener.MouseAdapter;
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
    
    private final GameScreen game;
    
    private Label gameLostLabel;
    
    private Button exitButton;
    
    private Button newGameButton;
    
    private Label statsLabel;
    
    public GameLostScreen(GameScreen game) {
        super();
        this.game = game;     
        
        Font font = Precache.getOpenSansFont();
        int fontSize = 24;
        
        // game lost label
        Text gameLostText = new Text("Game Lost! :(", font, 72);
        gameLostText.setColor(Color.WHITE);
        TextUtils.setOriginToCenter(gameLostText);
        
        gameLostLabel = new Label(gameLostText);
        
        widgets.add(gameLostLabel);
        
        // exit button
        Text exitText = new Text("Exit To Main Menu", font, fontSize);
        exitText.setColor(Color.WHITE);
        TextUtils.setOriginToCenter(exitText);
        
        exitButton = new Button(exitText);
        exitButton.getFrame().setBorderColor(Color.TRANSPARENT);
        
        MouseAdapter exitAdapter = new ButtonAdapter(new ChangeToMainMenuScreenAction(), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        exitButton.addMouseListener(exitAdapter);
        
        widgets.add(exitButton);
        
        // new game button
        Text newGameText = new Text("New Game", font, fontSize);
        newGameText.setColor(Color.WHITE);
        TextUtils.setOriginToCenter(newGameText);
        
        newGameButton = new Button(newGameText);
        newGameButton.getFrame().setBorderColor(Color.TRANSPARENT);
        
        MouseAdapter newGameAdapter = new ButtonAdapter(new ChangeToGameScreenAction(), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        newGameButton.addMouseListener(newGameAdapter);
        
        widgets.add(newGameButton);

        // stats label
        Text statsText = new Text(String.format("Asteroids blasted: %s\n"
                                        + "Shots fired: %s\n"
                                        + "Accuracy: %.4f", 
                                        game.getAsteroidsBlasted(), 
                                        game.getShotsFired(), 
                                        game.getAccuracy()),
                                  font,
                                  fontSize-5);
        statsText.setColor(Color.WHITE);
        TextUtils.setOriginToCenter(statsText);
        
        statsLabel = new Label(statsText);
        widgets.add(statsLabel);
        
        updateWidgets(Window.getSize());
    }
    
    @Override
    public final void updateWidgets(Vector2f screenSize) {
        float halfWidth = screenSize.x/2;
        
        gameLostLabel.setPosition(halfWidth, screenSize.y*.395833f);
        exitButton.setPosition(halfWidth, screenSize.y*.604167f);
        newGameButton.setPosition(halfWidth, screenSize.y*.708333f);
        statsLabel.setPosition(screenSize.x*.84375f, screenSize.y*.916667f);
    }

    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        // need to show the cursor
        ((org.jsfml.window.Window)rt).setMouseCursorVisible(true);
        
        rt.clear(bgColor);
        
        for (Widget w : widgets) {
            w.draw(rt, states);
        }
        ((org.jsfml.window.Window)rt).display();
    }
    
    @Override
    public ScreenName getName() {
        return ScreenName.GAME_LOST_SCREEN;
    }
}
