package coratticca.screen;

import coratticca.widget.Button;
import coratticca.action.ChangeToGameScreenAction;
import coratticca.action.ChangeToMainMenuScreenAction;
import coratticca.util.PrecacheUtils;
import coratticca.util.TextUtils;
import coratticca.window.Window;
import coratticca.widget.Widget;
import coratticca.widget.widgetlistener.ButtonAdapter;
import coratticca.widget.widgetlistener.MouseAdapter;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import coratticca.vector.Vector2;

/**
 * The Pause Menu screen.
 *
 * @author Nick
 */
public class PauseMenuScreen extends Screen {

    private final GameScreen previousGameScreen;

    private final Sprite bgSprite;

    private final Button exitButton;

    private final Button resumeButton;

    public PauseMenuScreen(GameScreen game) {

        previousGameScreen = game;

        bgSprite = Window.getBGSprite();

        Font font = PrecacheUtils.getOpenSansFont();
        int fontSize = 24;

        // add exit button
        Text exitText = new Text("Exit to Main Menu", font, fontSize);
        exitText.setColor(Color.WHITE);
        TextUtils.setOriginToCenter(exitText);

        exitButton = new Button(exitText);
        exitButton.getFrame().setBorderColor(Color.TRANSPARENT);

        MouseAdapter exitAdapter = new ButtonAdapter(new ChangeToMainMenuScreenAction(), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        exitButton.addMouseListener(exitAdapter);

        widgets.add(exitButton);

        // add resume button
        Text resumeText = new Text("Resume Game", font, fontSize);
        TextUtils.setOriginToCenter(resumeText);
        resumeText.setColor(Color.WHITE);

        // add resume game button
        resumeButton = new Button(resumeText);
        resumeButton.getFrame().setBorderColor(Color.TRANSPARENT);

        MouseAdapter resumeAdapter = new ButtonAdapter(new ChangeToGameScreenAction(game), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        resumeButton.addMouseListener(resumeAdapter);
        widgets.add(resumeButton);

        updateWidgets(Window.getSize());
    }

    @Override
    public final void updateWidgets(Vector2 size) {
        float halfWidth = size.x / 2;

        exitButton.setPosition(halfWidth, size.y * .291667f);
        resumeButton.setPosition(halfWidth, size.y * .708333f);
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
