/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.io.IOException;
import org.jsfml.graphics.RenderWindow;
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
     * @param width the width of the window.
     * @param height the height of the window.
     * @param title the title of the window.
     * @throws IOException
     */
    public static void display(Vector2i pos, 
                               int width, 
                               int height, 
                               String title) throws IOException {
        
        window.create(new VideoMode(width, height), title);
        
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
        System.out.format("%n%s Showing!%n%n", screen);
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
                    if (currentScreen.toString().equals("GAME")) {
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
     * Gets the window's width.
     * @return the width of the window.
     */
    public static int getWidth() {
        return window.getSize().x;
    }
    
    /**
     * Gets the window's height.
     * @return the height of the window.
     */
    public static int getHeight() {
        return window.getSize().y;
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
