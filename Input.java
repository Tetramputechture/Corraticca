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
 *
 * @author Nick
 */
public class Input {

    private static final Map<Keyboard.Key, Action> gameKeys = new HashMap<>();
    
    private static final Map<Mouse.Button, Action> gameMouseButtons = new HashMap<>();

    private static final Map<String, Class<? extends Action>> keyActions = new HashMap<>();
    
    private static final Map<String, Class<? extends Action>> mouseActions = new HashMap<>();
    
    private static Keyboard.Key currentKey;
    
    private static Mouse.Button currentMouseButton;

    private static Vector2f currentMousePos;
    
    static {
        // init keyActions
        keyActions.put(UseAction.NAME, UseAction.class);
        keyActions.put(MoveUpAction.NAME, MoveUpAction.class);
        keyActions.put(MoveDownAction.NAME, MoveDownAction.class);
        keyActions.put(MoveLeftAction.NAME, MoveLeftAction.class);
        keyActions.put(MoveRightAction.NAME, MoveRightAction.class);
        keyActions.put(ChangeToMainMenuScreenAction.NAME, ChangeToMainMenuScreenAction.class);
        keyActions.put(ChangeToPauseMenuScreenAction.NAME, ChangeToPauseMenuScreenAction.class);
        keyActions.put(ChangeToGameScreenAction.NAME, ChangeToGameScreenAction.class);
        
        // int mouseActions
        mouseActions.put(FireAction.NAME, FireAction.class);
    }

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

    public static void handleMainMenuKeyInput() {

        switch (currentKey) {

        }
    }

    public static void handleGameKeyInput() {
        if (currentKey == Keyboard.Key.ESCAPE) {
            Window.changeScreen(new PauseMenuScreen());     
        } else if (gameKeys.containsKey(currentKey)) {
            gameKeys.get(currentKey).execute();
        }
    }

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
        
        if (Window.getCurrentScreen().toString().equals("GAME")) {
            if (gameMouseButtons.containsKey(currentMouseButton)) {
                gameMouseButtons.get(currentMouseButton).execute();
            }
        }
        
    }
    
    public static void handleMouseMoveInput(MouseEvent mouseEvent) {
        currentMousePos = new Vector2f(mouseEvent.position.x, mouseEvent.position.y);
    }
    
//    public static void handleGameMouseMoveInput(MouseEvent mouseEvent) {
//        
//    }

    public static void setKeys() throws FileNotFoundException, IOException {

        FileInputStream incfg = new FileInputStream(new File("inputconfig.cfg"));

        try (BufferedReader br = new BufferedReader(new InputStreamReader(incfg))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] l = line.split("\\: ");
                
                int suffixIndex = l[0].indexOf("_") + 1;
                String suffix = l[0].substring(suffixIndex);
                
                if(l[0].startsWith("KEYBOARD_")) {
                    if (keyActions.containsKey(l[1])) {
                        try {
                            gameKeys.put(Keyboard.Key.valueOf(suffix), keyActions.get(l[1]).newInstance());
                        } catch (InstantiationException | IllegalAccessException ex) {
                            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (l[0].startsWith("MOUSE_")) {
                    if (mouseActions.containsKey(l[1])) {
                        try {
                            gameMouseButtons.put(Mouse.Button.valueOf(suffix), mouseActions.get(l[1]).newInstance());
                        } catch (InstantiationException | IllegalAccessException ex) {
                            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    public static Keyboard.Key getCurrentKey() {
        return currentKey;
    }

    public static Vector2f getMousePos() {
        return currentMousePos;
    }

}
