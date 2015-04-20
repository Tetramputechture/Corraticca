package coratticca.util;

import coratticca.util.widget.Button;
import coratticca.action.ChangeToPauseMenuScreenAction;
import coratticca.action.ChangeToGameScreenAction;
import coratticca.action.FireAction;
import coratticca.util.screen.GameScreen;
import coratticca.util.screen.MainMenuScreen;
import coratticca.util.screen.PauseMenuScreen;
import coratticca.util.screen.Screen;
import coratticca.util.widget.Widget;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;

/**
 * Handles all input.
 *
 * @author Nick
 */
public class Input {

    private Keyboard.Key currentKey;

    private Mouse.Button currentMouseButton;

    private Vector2f currentMousePos;

    /**
     * Handles all key input.
     *
     * @param keyEvent the keyEvent to be handled.
     */
    public void handleKeyInput(KeyEvent keyEvent, Window window) {

        currentKey = keyEvent.key;

        Screen currentScreen = window.getCurrentScreen();

        switch (window.getCurrentScreenName()) {
            case MAIN_MENU_SCREEN:
                handleMainMenuKeyInput((MainMenuScreen) currentScreen);
                break;

            case GAME_SCREEN:
                handleGameKeyInput((GameScreen) currentScreen, window);
                break;

            case PAUSE_MENU_SCREEN:
                handlePauseMenuKeyInput((PauseMenuScreen) currentScreen, window);
                break;

            default:
                break;
        }
    }

    /**
     * handles Main Menu key input.
     *
     * @param m the main menu to handle the input
     */
    public void handleMainMenuKeyInput(MainMenuScreen m) {
        switch (currentKey) {
            // TO DO
        }
    }

    /**
     * handles Game key input.
     *
     * @param g the game to handle the input
     */
    public void handleGameKeyInput(GameScreen g, Window window) {
        if (currentKey == Keyboard.Key.ESCAPE) {
            new ChangeToPauseMenuScreenAction(window, g).execute();
        }
    }

    public void handlePauseMenuKeyInput(PauseMenuScreen p, Window window) {
        if (currentKey == Keyboard.Key.ESCAPE) {
            new ChangeToGameScreenAction(p.getPreviousGameScreen().getWindow()).execute();
        }
    }

    /**
     * Handles mouse click input.
     *
     * @param mouseEvent the Mouse Event to be handled.
     */
    public void handleMouseClickInput(MouseButtonEvent mouseEvent, Window window) {

        setMousePosition(mouseEvent, window);
        currentMouseButton = mouseEvent.button;

        if (currentMouseButton == Mouse.Button.LEFT) {
            if (window.getCurrentScreen() instanceof GameScreen) {
                new FireAction(window).execute();
                return;
            }
            for (Widget w : window.getCurrentScreen().getWidgets()) {
                if (w instanceof Button) {
                    Button b = (Button) w;
                    if (b.getFrame().contains(currentMousePos)) {
                        b.setClicked();
                        return;
                    }
                }
            }
        }

    }

    /**
     * Handles mouse move input.
     *
     * @param mouseEvent the Mouse Event to be handled.
     */
    public void handleMouseMoveInput(MouseEvent mouseEvent, Window window) {

        setMousePosition(mouseEvent, window);

        for (Widget w : window.getCurrentScreen().getWidgets()) {
            if (w instanceof Button) {
                Button b = (Button) w;
                if (b.getFrame().contains(currentMousePos)) {
                    b.setEntered();
                    return;
                } else {
                    b.setExited();
                }
            }
        }
    }

    /**
     * Sets the mouse position for a specified mouse event.
     *
     * @param mouseEvent the mouse event to get position from.
     */
    private void setMousePosition(MouseEvent mouseEvent, Window window) {
        Vector2i tpos = window.getRenderWindow().mapCoordsToPixel(new Vector2f(mouseEvent.position));
        currentMousePos = new Vector2f(tpos);
    }

    /**
     * Gets the current key pressed.
     *
     * @return the current key being pressed.
     */
    public Keyboard.Key getCurrentKey() {
        return currentKey;
    }

    /**
     * Gets the current mouse position.
     *
     * @return the current mouse position.
     */
    public Vector2f getMousePos() {
        return currentMousePos;
    }

}
