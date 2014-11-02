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
 *
 * @author Nick
 */
public final class Window {
    
    private static final RenderWindow window;
    
    private static Screen currentScreen;
    
    static {
        window = new RenderWindow();
    }
    
    public static void display(int posX, 
                               int posY, 
                               int sizeX, 
                               int sizeY, 
                               String title) throws IOException {
        
        window.create(new VideoMode(sizeX, sizeY), title);
        
        window.setPosition(new Vector2i(posX, posY));
        
        window.setVerticalSyncEnabled(true);
        
        changeScreen(new MainMenuScreen());
        
        Input.setInputs();
        
        while(window.isOpen()) {
            Window.handleEvents();
            Window.currentScreen.show();
        }
    }  
    
    public static void changeScreen(Screen screen) {
        System.out.format("\n%s Showing!\n\n", screen);
        currentScreen = screen;
    }
    
    public static void handleEvents() {
        
        for(Event event : window.pollEvents()) {
            switch(event.type) {
                case CLOSED:
                    window.close();
                    break;

                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    Input.handleKeyInput(keyEvent);
                    break;
                    
                case KEY_RELEASED:
                    //Input.setCurrentActions(new UnassignedAction(), Input.getCurrentKeys().);
                    break;
                    
                case MOUSE_BUTTON_PRESSED:
                    MouseButtonEvent mouseButtonEvent = event.asMouseButtonEvent();
                    Input.handleMouseClickInput(mouseButtonEvent);
                    break;
                    
                case MOUSE_MOVED:
                    MouseEvent mouseEvent = event.asMouseEvent();
                    Input.handleMouseMoveInput(mouseEvent);
                    break;
            }
        }
    }
   
    public static Screen getCurrentScreen() {
        return currentScreen;
    }
    
    public static void setSize(int sizeX, int sizeY) {
        window.setSize(new Vector2i(sizeX, sizeY));
    }
    
    public static int getWidth() {
        return window.getSize().x;
    }
    
    public static int getHeight() {
        return window.getSize().y;
    }
    
    public static void setPos(int posX, int posY) {
        window.setPosition(new Vector2i(posX, posY));
    }
    
    public static int getPosX() {
        return window.getPosition().x;
    }
    
    public static int getPosY() {
        return window.getPosition().y;
    }
    
    public static void setTitle(String title) {
        window.setTitle(title);
    }
    
    public static RenderWindow getWindow() {
        return window;
    }
}
