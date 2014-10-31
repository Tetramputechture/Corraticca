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
import java.lang.reflect.InvocationTargetException;
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

    static final Map<String, Action> Actions = new HashMap<>();

    private static Keyboard.Key currentKey;

    private static Vector2f currentMousePos;
    
    private static Mouse.Button currentMouseButton;

    // init actions
    static {
        Actions.put(FireAction.NAME, new FireAction());
        Actions.put(UseAction.NAME, new UseAction());
        Actions.put(ChangeToMainMenuScreenAction.NAME, new ChangeToMainMenuScreenAction());
        Actions.put(ChangeToPauseMenuScreenAction.NAME, new ChangeToPauseMenuScreenAction());
        Actions.put(ChangeToGameScreenAction.NAME, new ChangeToGameScreenAction());
    }

    public static void handleKeyInput(KeyEvent keyEvent) {

        currentKey = keyEvent.key;

        switch (Window.getCurrentScreen().toString()) {
            case "MAIN_MENU":
                handleMainMenuKeyInput();
                break;

            case "GAME":
                handleGameKeyInput();
                handlePlayerKeyInput();
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
        }
    }
    
    public static void handlePlayerKeyInput() {
        if (gameKeys.containsKey(currentKey)) {
            try {
                gameKeys.get(currentKey).getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
            }
            gameKeys.get(currentKey).execute();
        }
    }

    public static void handleMouseClickInput(MouseButtonEvent mouseEvent) {

        currentMousePos = new Vector2f(mouseEvent.position.x, mouseEvent.position.y);
        currentMouseButton = mouseEvent.button;
        System.out.format("Mouse clicked on position (%s, %s)\n", currentMousePos.x, currentMousePos.y);

        if (currentMouseButton == Mouse.Button.LEFT) {
            for (Button i : Window.getCurrentScreen().getButtons()) {
                if (i.getTextObject().getGlobalBounds().contains(currentMousePos)) {
                    i.executeAction();
                    break;
                }
            }
        }
    }
    
    public static void handleMouseMoveInput(MouseEvent mouseEvent) {
        currentMousePos = new Vector2f(mouseEvent.position.x, mouseEvent.position.y);
    }


    public static void setKeys() throws FileNotFoundException, IOException {

        FileInputStream incfg = new FileInputStream(new File("inputconfig.cfg"));

        try (BufferedReader br = new BufferedReader(new InputStreamReader(incfg))) {

            String line, key, action;

            while ((line = br.readLine()) != null) {

                String[] l = line.split("\\: ");

                if (Actions.containsKey(l[1])) {
                    gameKeys.put(Keyboard.Key.valueOf(l[0]), Actions.get(l[1]));
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
