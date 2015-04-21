package coratticca.util.screen;

import coratticca.util.widget.Button;
import coratticca.action.ChangeToGameScreenAction;
import coratticca.action.ChangeToMainMenuScreenAction;
import coratticca.util.Precache;
import coratticca.util.TextUtils;
import coratticca.util.Window;
import coratticca.util.widget.Widget;
import coratticca.util.widget.widgetListener.ButtonAdapter;
import coratticca.util.widget.widgetListener.MouseAdapter;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;

/**
 * The Pause Menu screen.
 *
 * @author Nick
 */
public class PauseMenuScreen extends Screen {
    
    private final GameScreen previousGameScreen;
    
    private final Sprite bgSprite;

    public PauseMenuScreen(GameScreen game) {

        previousGameScreen = game;
        
        bgSprite = Window.getBGSprite();

        float halfWidth = Window.getSize().x/2;
        float halfHeight = Window.getSize().y/2;

        Font font = Precache.getOpenSansFont();
        int fontSize = 24;

        // add exit button
        Text exitText = new Text("Exit to Main Menu", font, fontSize);
        exitText.setColor(Color.WHITE);
        TextUtils.setOriginToCenter(exitText);
        exitText.setPosition(halfWidth, halfHeight - 100);

        Button exitButton = new Button(exitText);
        exitButton.getFrame().setBorderColor(Color.TRANSPARENT);

        MouseAdapter exitAdapter = new ButtonAdapter(new ChangeToMainMenuScreenAction(), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        exitButton.addMouseListener(exitAdapter);

        widgets.add(exitButton);

        // add resume button
        Text resumeText = new Text("Resume Game", font, fontSize);
        TextUtils.setOriginToCenter(resumeText);
        resumeText.setPosition(halfWidth, halfHeight + 100);
        resumeText.setColor(Color.WHITE);

        // add resume game button
        Button resumeButton = new Button(resumeText);
        resumeButton.getFrame().setBorderColor(Color.TRANSPARENT);

        MouseAdapter resumeAdapter = new ButtonAdapter(new ChangeToGameScreenAction(game), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        resumeButton.addMouseListener(resumeAdapter);
        widgets.add(resumeButton);
    }

    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        // need to show the cursor
        ((org.jsfml.window.Window) rt).setMouseCursorVisible(true);
        
        rt.clear(bgColor);
        
        rt.draw(bgSprite);

        for (Widget w : widgets) {
            w.draw(rt, states);
        }
        
        ((org.jsfml.window.Window) rt).display();
    }

    public GameScreen getPreviousGameScreen() {
        return previousGameScreen;
    }

    @Override
    public ScreenName getName() {
        return ScreenName.PAUSE_MENU_SCREEN;
    }

}
