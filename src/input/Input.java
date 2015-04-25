package coratticca.input;

import coratticca.widget.Button;
import coratticca.action.ChangeToPauseMenuScreenAction;
import coratticca.action.ChangeToGameScreenAction;
import coratticca.action.FireAction;
import coratticca.window.Window;
import coratticca.screen.GameScreen;
import coratticca.screen.MainMenuScreen;
import coratticca.screen.PauseMenuScreen;
import coratticca.screen.Screen;
import coratticca.widget.Widget;
import coratticca.vector.Vector2;
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

    private Vector2 currentMousePos;

    /**
     * Handles all key input.
     *
     * @param keyEvent the keyEvent to be handled.
     */
    public void handleKeyInput(KeyEvent keyEvent) {

        currentKey = keyEvent.key;

        Screen currentScreen = Window.getCurrentScreen();

        switch (currentScreen.getName()) {
            case MAIN_MENU_SCREEN:
                handleMainMenuKeyInput((MainMenuScreen) currentScreen);
                break;

            case GAME_SCREEN:
                handleGameKeyInput((GameScreen) currentScreen);
                break;

            case PAUSE_MENU_SCREEN:
                handlePauseMenuKeyInput((PauseMenuScreen) currentScreen);
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
    public void handleGameKeyInput(GameScreen g) {
        if (currentKey == Keyboard.Key.ESCAPE) {
            new ChangeToPauseMenuScreenAction(g).execute();
        }
    }

    public void handlePauseMenuKeyInput(PauseMenuScreen p) {
        if (currentKey == Keyboard.Key.ESCAPE) {
            new ChangeToGameScreenAction(p.getPreviousGameScreen()).execute();
        }
    }

    /**
     * Handles mouse click input.
     *
     * @param mouseEvent the Mouse Event to be handled.
     */
    public void handleMouseClickInput(MouseButtonEvent mouseEvent) {

        setMousePosition(mouseEvent);
        currentMouseButton = mouseEvent.button;

        if (currentMouseButton == Mouse.Button.LEFT) {
            if (Window.getCurrentScreen() instanceof GameScreen) {
                new FireAction().execute();
                return;
            }
            for (Widget w : Window.getCurrentScreen().getWidgets()) {
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
    public void handleMouseMoveInput(MouseEvent mouseEvent) {

        setMousePosition(mouseEvent);

        for (Widget w : Window.getCurrentScreen().getWidgets()) {
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
    private void setMousePosition(MouseEvent mouseEvent) {
        currentMousePos = new Vector2(Window.getRenderWindow().mapCoordsToPixel(new Vector2(mouseEvent.position).toVector2f()));
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
    public Vector2 getMousePos() {
        return currentMousePos;
    }

}
