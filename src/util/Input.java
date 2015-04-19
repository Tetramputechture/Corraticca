package coratticca.util;

import coratticca.util.widget.CButton;
import coratticca.action.FireAction;
import coratticca.action.ChangeToPauseMenuScreenAction;
import coratticca.action.Action;
import coratticca.action.ChangeToGameScreenAction;
import coratticca.util.screen.GameScreen;
import coratticca.util.screen.MainMenuScreen;
import coratticca.util.screen.PauseMenuScreen;
import coratticca.util.screen.Screen;
import coratticca.util.widget.CWidget;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
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

    // To read the game keyboard keys.
    private final Map<Keyboard.Key, Action> gameKeys = new HashMap<>();

    // To read the game mouse buttons.
    private final Map<Mouse.Button, Action> gameMouseButtons = new HashMap<>();

    // To assign the keyboard key actions.
    private final Map<String, Action> keyActions = new HashMap<>();

    // To assign the mouse button actions.
    private final Map<String, Action> mouseActions = new HashMap<>();

    private Keyboard.Key currentKey;

    private Mouse.Button currentMouseButton;

    private Vector2f currentMousePos;

    public Input() {
        FireAction f = new FireAction();
        mouseActions.put(f.getName(), f);
    }

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
            new ChangeToPauseMenuScreenAction(g).execute(window);
        } else if (gameKeys.containsKey(currentKey)) {
            gameKeys.get(currentKey).execute(window);
        }
    }

    public void handlePauseMenuKeyInput(PauseMenuScreen p, Window window) {
        if (currentKey == Keyboard.Key.ESCAPE) {
            new ChangeToGameScreenAction(p.getPreviousGameScreen()).execute(window);
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

        if (window.getCurrentScreen() instanceof GameScreen) {
            if (gameMouseButtons.containsKey(currentMouseButton)) {
                gameMouseButtons.get(currentMouseButton).execute(window);
            }
            return;
        }

        if (currentMouseButton == Mouse.Button.LEFT) {
            for (CWidget w : window.getCurrentScreen().getWidgets()) {
                if (w instanceof CButton && w.contains(currentMousePos)) {
                    CButton b = (CButton)w;
                    b.setClicked();
                    b.notifyClickListener();
                    return;
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

        for (CWidget w : window.getCurrentScreen().getWidgets()) {
            if (w instanceof CButton) {
                CButton b = (CButton) w;
                if (b.contains(currentMousePos)) {
                    b.setSelected();
                    b.notifySelectListener();
                    return;
                } else {
                    b.notifyUnselectListener();
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
     * Sets the inputs from a file.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void setInputs() throws FileNotFoundException, IOException {
        // gets the config file   
        Reader reader = new InputStreamReader(new FileInputStream("inputconfig.cfg"));

        try (BufferedReader br = new BufferedReader(reader)) {

            String line;

            while ((line = br.readLine()) != null) {

                // format: key/button: action
                String[] l = line.split("\\: ");

                int suffixIndex = l[0].indexOf('_') + 1;
                String suffix = l[0].substring(suffixIndex);

                String action = l[1];

                // keyboard input specified
                if (l[0].startsWith("KEYBOARD_")) {
                    if (keyActions.containsKey(l[1])) {
                        gameKeys.put(Keyboard.Key.valueOf(suffix), keyActions.get(action));
                    }
                    // mouse input specified
                } else if (l[0].startsWith("MOUSE_")) {
                    if (mouseActions.containsKey(l[1])) {
                        gameMouseButtons.put(Mouse.Button.valueOf(suffix), mouseActions.get(action));
                    }
                }
            }
        }
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
