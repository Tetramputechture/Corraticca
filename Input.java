/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;

/**
 * Handles all input. 
 * @author Nick
 */
public class Input {

    // To read the game keyboard keys.
    private static final Map<Keyboard.Key, Action> gameKeys = new HashMap<>();
    
    // To read the game mouse buttons.
    private static final Map<Mouse.Button, Action> gameMouseButtons = new HashMap<>();

    // To assign the keyboard key actions.
    private static final Map<String, Class<? extends Action>> keyActions = new HashMap<>();
    
    // To assign the mouse button actions.
    private static final Map<String, Class<? extends Action>> mouseActions = new HashMap<>();
    
    private static Keyboard.Key currentKey;
    
    private static Mouse.Button currentMouseButton;

    private static Vector2f currentMousePos;
    
    static {
        // init keyActions
        keyActions.put(ChangeToMainMenuScreenAction.NAME, ChangeToMainMenuScreenAction.class);
        keyActions.put(ChangeToPauseMenuScreenAction.NAME, ChangeToPauseMenuScreenAction.class);
        keyActions.put(ChangeToGameScreenAction.NAME, ChangeToGameScreenAction.class);
        
        // int mouseActions
        mouseActions.put(FireAction.NAME, FireAction.class);
    }

    /**
     * Handles all key input.
     * @param keyEvent the keyEvent to be handled.
     */
    public static void handleKeyInput(KeyEvent keyEvent) {

        currentKey = keyEvent.key;

        switch (Window.getCurrentScreen().toString()) {
            case "MAIN_MENU":
                handleMainMenuKeyInput();
                break;

            case "GAME":
                handleGameKeyInput();
                break;
        }
    }

    /**
     * handles Main Menu key input.
     */
    public static void handleMainMenuKeyInput() {

        switch (currentKey) {
            // TO DO
        }
    }

    /**
     * handles Game key input.
     */
    public static void handleGameKeyInput() {
        if (currentKey == Keyboard.Key.ESCAPE) {
            Window.changeScreen(new PauseMenuScreen());     
        } else if (gameKeys.containsKey(currentKey)) {
            gameKeys.get(currentKey).execute();
        }
    }

    /**
     * Handles mouse click input.
     * @param mouseEvent the Mouse Event to be handled.
     */
    public static void handleMouseClickInput(MouseButtonEvent mouseEvent) {

        currentMousePos = new Vector2f(mouseEvent.position.x, mouseEvent.position.y);
        currentMouseButton = mouseEvent.button;
        System.out.format("Mouse %s clicked on position (%s, %s)\n", currentMouseButton, currentMousePos.x, currentMousePos.y);

        if (currentMouseButton == Mouse.Button.LEFT) {
            for (Button i : Window.getCurrentScreen().getButtons()) {
                if (i.getTextObject().getGlobalBounds().contains(currentMousePos)) {
                    i.executeAction();
                    return;
                }
            }
        }
        
        // If a Button isn't pressed, then execute the associated action on the game screen
        if (Window.getCurrentScreen().toString().equals("GAME")) {
            if (gameMouseButtons.containsKey(currentMouseButton)) {
                gameMouseButtons.get(currentMouseButton).execute();
            }
        }
        
    }
    
    /**
     * Handles mouse move input.
     * @param mouseEvent the Mouse Event to be handled.
     */
    public static void handleMouseMoveInput(MouseEvent mouseEvent) {
        currentMousePos = new Vector2f(mouseEvent.position.x, mouseEvent.position.y);
    }

    /**
     * Sets the inputs from a file.
     * @throws FileNotFoundException
     * @throws IOException
     */
        public static void setInputs() throws FileNotFoundException, IOException {

            
        // gets the config file   
        FileInputStream incfg = new FileInputStream(new File("inputconfig.cfg"));

        try (BufferedReader br = new BufferedReader(new InputStreamReader(incfg))) {

            String line;

            while ((line = br.readLine()) != null) {

                // format: key/button: action
                String[] l = line.split("\\: ");
                
                int suffixIndex = l[0].indexOf("_") + 1;
                String suffix = l[0].substring(suffixIndex);
                
                String action = l[1];
                
                // keyboard input specified
                if(l[0].startsWith("KEYBOARD_")) {
                    if (keyActions.containsKey(l[1])) {
                        try {
                            gameKeys.put(Keyboard.Key.valueOf(suffix), keyActions.get(action).newInstance());
                        } catch (InstantiationException | IllegalAccessException ex) {
                            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, 
                                    String.format("Error in input.cfg at %s!", l[0] + l[1]), ex);
                        }
                    }
                // mouse input specified
                } else if (l[0].startsWith("MOUSE_")) {
                    if (mouseActions.containsKey(l[1])) {
                        try {
                            gameMouseButtons.put(Mouse.Button.valueOf(suffix), mouseActions.get(action).newInstance());
                        } catch (InstantiationException | IllegalAccessException ex) {
                            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, 
                                    String.format("Error in input.cfg at %s!", l[0] + l[1]), ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the current key pressed.
     * @return the current key being pressed.
     */
    public static Keyboard.Key getCurrentKey() {
        return currentKey;
    }

    /**
     * Gets the current mouse position.
     * @return the current mouse position.
     */
    public static Vector2f getMousePos() {
        return currentMousePos;
    }

}
