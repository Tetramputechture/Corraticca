/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Utils.Screen.GameScreen;
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
 * Singleton.
 * @author Nick
 */
public final class Window {
    
    private final RenderWindow window;
    
    private final Input inputHandler;
    
    private final Audio audioHandler;
    
    private Screen currentScreen;
    
    public Window(Vector2i pos, Vector2i size, String title) throws IOException {
        window = new RenderWindow();
        
        window.create(new VideoMode(size.x, size.y), title);
        
        window.setPosition(pos);
        
        window.setVerticalSyncEnabled(true);
        
        changeScreen(new MainMenuScreen(this));
        
        inputHandler = new Input(this);
        
        audioHandler = new Audio();
        
        // set the inputs
        inputHandler.setInputs();
    }
    
    /**
     * Makes a window that handles screens. 
     */
    public void display() {
        
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
    public void changeScreen(Screen screen) {
        currentScreen = screen;
    }
    
    /**
     * Polls the window's events. (Move to inputHandler?)
     */
    public void handleEvents() {
        
        for(Event event : window.pollEvents()) {
            switch(event.type) {
                case CLOSED:
                    window.close();
                    break;
                
                case LOST_FOCUS:
                    if (getCurrentScreen() instanceof GameScreen) {
                        changeScreen(new PauseMenuScreen((GameScreen) currentScreen));
                    }
                    break;

                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    inputHandler.handleKeyInput(keyEvent);
                    break;
                    
                case MOUSE_BUTTON_PRESSED:
                    MouseButtonEvent mouseButtonEvent = event.asMouseButtonEvent();
                    inputHandler.handleMouseClickInput(mouseButtonEvent);
                    break;
                    
                case MOUSE_MOVED:
                    MouseEvent mouseEvent = event.asMouseEvent();
                    inputHandler.handleMouseMoveInput(mouseEvent);
                    break;
                
                default:
                    break;
            }
        }
    }
    
    public Input getInputHandler() {
        return inputHandler;
    }
    
    public Audio getAudioHandler() {
        return audioHandler;
    }
   
    /**
     * Gets the current screen the window is displaying.
     * @return the current screen being displayed.
     */
    public Screen getCurrentScreen() {
        return currentScreen;
    }
    
    /**
     * Gets the current screen name.
     * @return the name of the current screen being displayed.
     */
    public ScreenName getCurrentScreenName() {
        return currentScreen.getName();
    }
    
    /**
     * Sets the window's size.
     * @param width the width to be set.
     * @param height the height to be set.
     */
    public void setSize(int width, int height) {
        window.setSize(new Vector2i(width, height));
    }
    
    /**
     * Gets the window's size.
     * @return the size of the window.
     */
    public Vector2f getSize() {
        return new Vector2f(window.getSize().x, window.getSize().y);
    }
    
    /**
     * Gets the center of the window.
     * @return the window's center.
     */
    public Vector2f getCenter() {
        return new Vector2f(window.getSize().x/2, window.getSize().y/2);
    }
    
    /**
     * Sets the window's position.
     * @param pos the position to be set.
     */
    public void setPos(Vector2i pos) {
        window.setPosition(pos);
    }
    
    /**
     * Gets the window's position.
     * @return an integer vector of the window's position.
     */
    public Vector2i getPos() {
        return window.getPosition();
    }
    
    /**
     * Sets the window's title.
     * @param title the title to be set.
     */
    public void setTitle(String title) {
        window.setTitle(title);
    }
    
    /**
     * Gets the render window of the window.
     * @return the window's render window.
     */
    public RenderWindow getRenderWindow() {
        return window;
    }
}
