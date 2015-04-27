package coratticca.screen;

import coratticca.action.ChangeToGameScreenAction;
import coratticca.action.ChangeToMainMenuScreenAction;
import coratticca.widget.Button;
import coratticca.util.PrecacheUtils;
import coratticca.util.TextUtils;
import coratticca.window.Window;
import coratticca.widget.Label;
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

        Font font = PrecacheUtils.getOpenSansFont();
        int fontSize = 24;

        // game lost label
        Text gameLostText = new Text("game lost :(", font, 72);
        TextUtils.setOriginToCenter(gameLostText);

        gameLostLabel = new Label(gameLostText);

        widgets.add(gameLostLabel);

        // exit button
        Text exitText = new Text("exit to main menu", font, fontSize);
        TextUtils.setOriginToCenter(exitText);

        exitButton = new Button(exitText);
        exitButton.getFrame().setBorderColor(Color.TRANSPARENT);

        MouseAdapter exitAdapter = new ButtonAdapter(new ChangeToMainMenuScreenAction(), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        exitButton.addMouseListener(exitAdapter);

        widgets.add(exitButton);

        // new game button
        Text newGameText = new Text("new game", font, fontSize);
        TextUtils.setOriginToCenter(newGameText);

        newGameButton = new Button(newGameText);
        newGameButton.getFrame().setBorderColor(Color.TRANSPARENT);

        MouseAdapter newGameAdapter = new ButtonAdapter(new ChangeToGameScreenAction(), "sounds/buttonsound1.wav", Color.RED, Color.WHITE);
        newGameButton.addMouseListener(newGameAdapter);

        widgets.add(newGameButton);

        // stats label
        StringBuilder statsSb = new StringBuilder("asteroids blasted: " );
        statsSb.append(game.getAsteroidsBlasted());
        statsSb.append("\nshots fired: ");
        statsSb.append(game.getShotsFired());
        statsSb.append("\naccuracy: ");
        statsSb.append(game.getAccuracy());
        Text statsText = new Text(statsSb.toString(), font, fontSize - 5);
        TextUtils.setOriginToCenter(statsText);

        statsLabel = new Label(statsText);
        widgets.add(statsLabel);

        updateWidgets(Window.getSize());
    }

    @Override
    public final void updateWidgets(Vector2 screenSize) {
        float halfWidth = screenSize.x / 2;

        gameLostLabel.setPosition(halfWidth, screenSize.y * .395833f);
        exitButton.setPosition(halfWidth, screenSize.y * .604167f);
        newGameButton.setPosition(halfWidth, screenSize.y * .708333f);
        statsLabel.setPosition(screenSize.x * .84375f, screenSize.y * .916667f);
    }

    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        // need to show the cursor
        ((org.jsfml.window.Window) rt).setMouseCursorVisible(true);

        rt.clear(bgColor);

        for (Widget w : widgets) {
            w.draw(rt, states);
        }
        ((org.jsfml.window.Window) rt).display();
    }

    @Override
    public ScreenName getName() {
        return ScreenName.GAME_LOST_SCREEN;
    }
}
