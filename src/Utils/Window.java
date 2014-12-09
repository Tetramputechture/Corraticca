/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Utils.Screen.MainMenuScreen;
import coratticca.Utils.Screen.PauseMenuScreen;
import coratticca.Utils.Screen.Screen;
import coratticca.Utils.Screen.ScreenName;
import java.io.IOException;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;

/**
 * A Window class to handle the game's window.
 * @author Nick
 */
public final class Window {
    
    private static final RenderWindow window;
    
    private static Screen currentScreen;
    
    static {
        window = new RenderWindow();
    }
    
    /**
     * Makes a window that handles screens. 
     * @param pos the position of the window.
     * @param size the size of the window.
     * @param title the title of the window.
     * @throws IOException
     */
    public static void display(Vector2i pos, 
                               Vector2i size,
                               String title) throws IOException {
        
        window.create(new VideoMode(size.x, size.y), title);
        
        window.setPosition(pos);
        
        window.setVerticalSyncEnabled(true);
        
        changeScreen(new MainMenuScreen());
        
        // set the inputs
        Input.setInputs();
        
        while(window.isOpen()) {
            // poll events
            handleEvents();
            // show the current screen
            currentScreen.show();
        }
    }  
    
    /**
     * Changes the screen to the specified screen.
     * @param screen the screen to change to.
     */
    public static void changeScreen(Screen screen) {
        currentScreen = screen;
    }
    
    /**
     * Polls the window's events. (Move to Input?)
     */
    public static void handleEvents() {
        
        for(Event event : window.pollEvents()) {
            switch(event.type) {
                case CLOSED:
                    window.close();
                    break;
                
                case LOST_FOCUS:
                    if (getCurrentScreenName() == ScreenName.GAME_SCREEN) {
                        changeScreen(new PauseMenuScreen());
                    }
                    break;

                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    Input.handleKeyInput(keyEvent);
                    break;
                    
                case MOUSE_BUTTON_PRESSED:
                    MouseButtonEvent mouseButtonEvent = event.asMouseButtonEvent();
                    Input.handleMouseClickInput(mouseButtonEvent);
                    break;
                    
                case MOUSE_MOVED:
                    MouseEvent mouseEvent = event.asMouseEvent();
                    Input.handleMouseMoveInput(mouseEvent);
                    break;
                
                default:
                    break;
            }
        }
    }
   
    /**
     * Gets the current screen the window is displaying.
     * @return the current screen being displayed.
     */
    public static Screen getCurrentScreen() {
        return currentScreen;
    }
    
    /**
     * Gets the current screen name.
     * @return the name of the current screen being displayed.
     */
    public static ScreenName getCurrentScreenName() {
        return currentScreen.getName();
    }
    
    /**
     * Sets the window's size.
     * @param width the width to be set.
     * @param height the height to be set.
     */
    public static void setSize(int width, int height) {
        window.setSize(new Vector2i(width, height));
    }
    
    /**
     * Gets the window's size.
     * @return the size of the window.
     */
    public static Vector2f getSize() {
        return new Vector2f(window.getSize().x, window.getSize().y);
    }
    
    /**
     * Gets the center of the window.
     * @return the window's center.
     */
    public static Vector2f getCenter() {
        return new Vector2f(window.getSize().x/2, window.getSize().y/2);
    }
    
    /**
     * Sets the window's position.
     * @param pos the position to be set.
     */
    public static void setPos(Vector2i pos) {
        window.setPosition(pos);
    }
    
    /**
     * Gets the window's position.
     * @return an integer vector of the window's position.
     */
    public static Vector2i getPos() {
        return window.getPosition();
    }
    
    /**
     * Sets the window's title.
     * @param title the title to be set.
     */
    public static void setTitle(String title) {
        window.setTitle(title);
    }
    
    /**
     * Gets the render window of the window.
     * @return the window's render window.
     */
    public static RenderWindow getWindow() {
        return window;
    }
}
