/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Actions.FireAction;
import coratticca.Actions.ChangeToPauseMenuScreenAction;
import coratticca.Actions.ChangeToMainMenuScreenAction;
import coratticca.Actions.Action;
import coratticca.Utils.Screen.GameScreen;
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
    
    // Window that accepts the output.
    private final Window window;

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
    
    public Input(Window w) {
        this.window = w;
        mouseActions.put(FireAction.NAME, new FireAction());
    }

    /**
     * Handles all key input.
     *
     * @param keyEvent the keyEvent to be handled.
     */
    public void handleKeyInput(KeyEvent keyEvent) {

        currentKey = keyEvent.key;

        switch (window.getCurrentScreenName()) {
            case MAIN_MENU_SCREEN:
                handleMainMenuKeyInput();
                break;

            case GAME_SCREEN:
                handleGameKeyInput();
                break;

            default:
                break;
        }
    }

    /**
     * handles Main Menu key input.
     */
    public void handleMainMenuKeyInput() {

        switch (currentKey) {
            // TO DO
        }
    }

    /**
     * handles Game key input.
     */
    public void handleGameKeyInput() {
        if (currentKey == Keyboard.Key.ESCAPE) {
            new ChangeToPauseMenuScreenAction((GameScreen)window.getCurrentScreen()).execute(window);
        } else if (gameKeys.containsKey(currentKey)) {
            gameKeys.get(currentKey).execute(window);
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
            for (Button i : window.getCurrentScreen().getButtons()) {
                if (i.getTextObject().getGlobalBounds().contains(currentMousePos)) {
                    if (i.getAction() != null) {
                        i.executeAction();
                    }
                    return;
                }
            }
        }

        // If a Button isn't pressed, then execute the associated action on the game screen
        if (window.getCurrentScreen() instanceof GameScreen) {
            if (gameMouseButtons.containsKey(currentMouseButton)) {
                gameMouseButtons.get(currentMouseButton).execute(window);
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

        window.getCurrentScreen().getButtons().stream().forEach((i) -> {
            if (i.getTextObject().getGlobalBounds().contains(new Vector2f(currentMousePos.x, currentMousePos.y))) {
                if (i.getAction() != null) {
                    i.handleMouseHover();
                }
                i.shouldPlaySelectSound(false);
            } else {
                i.setToDefaultColor();
                i.shouldPlaySelectSound(true);
            }
        });
    }

    /**
     * Sets the mouse position for a specified mouse event.
     *
     * @param mouseEvent the mouse event to get position from.
     */
    public void setMousePosition(MouseEvent mouseEvent) {
        Vector2i tpos = window.getRenderWindow().mapCoordsToPixel(new Vector2f(mouseEvent.position.x, mouseEvent.position.y));

        currentMousePos = new Vector2f(tpos.x, tpos.y);
    }

    /**
     * Sets the inputs from a file.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void setInputs() throws FileNotFoundException, IOException {
        // gets the config file   
        Reader reader = new InputStreamReader(new FileInputStream("inputconfig.cfg"), "utf-8");

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
