package coratticca.screen;

import coratticca.window.Window;
import coratticca.widget.Button;
import coratticca.action.ExitGameAction;
import coratticca.action.ChangeToGameScreenAction;
import coratticca.util.PrecacheUtils;
import coratticca.util.TextUtils;
import coratticca.widget.Widget;
import coratticca.widget.widgetlistener.ButtonAdapter;
import coratticca.widget.widgetlistener.MouseAdapter;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import coratticca.vector.Vector2;

/**
 * The Main Menu screen.
 *
 * @author Nick
 */
public class MainMenuScreen extends Screen {

    private final Button playButton;

    private final Button exitButton;

    public MainMenuScreen() {

        Font font = PrecacheUtils.getOpenSansFont();
        int fontSize = 52;

        // add play button
        Text playText = new Text("play", font, fontSize);
        TextUtils.setOriginToCenter(playText);

        playButton = new Button(playText);
        playButton.getFrame().setBorderColor(Color.TRANSPARENT);

        MouseAdapter playAdapter = new ButtonAdapter(new ChangeToGameScreenAction(), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        playButton.addMouseListener(playAdapter);

        widgets.add(playButton);

        // add exit button
        Text exitText = new Text("exit", font, fontSize);
        TextUtils.setOriginToCenter(exitText);

        exitButton = new Button(exitText);
        exitButton.getFrame().setBorderColor(Color.TRANSPARENT);

        MouseAdapter exitAdapter = new ButtonAdapter(new ExitGameAction(), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        exitButton.addMouseListener(exitAdapter);

        widgets.add(exitButton);

        updateWidgets(Window.getSize());
    }

    @Override
    public final void updateWidgets(Vector2 size) {
        float halfWidth = size.x / 2;

        playButton.setPosition(halfWidth, size.y / 3);

        exitButton.setPosition(halfWidth, size.y * .5417f);
    }

    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        // need to show the cursor
        ((org.jsfml.window.Window) rt).setMouseCursorVisible(true);

        rt.clear(bgColor);

        for (Widget w : widgets) {
            rt.draw(w);
        }
        ((org.jsfml.window.Window) rt).display();
    }

    @Override
    public ScreenName getName() {
        return ScreenName.MAIN_MENU_SCREEN;
    }
}
