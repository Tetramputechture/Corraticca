/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Actions.Action;
import coratticca.Utils.Screen.MainMenuScreen;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

/**
 * This class is for a button on a screen.
 * A button can have any action assigned to it.
 * If you just want to make a simple text object, leave the action as null.
 * @author Nick
 */
public class Button extends Input {
    
    private final Text text;
    private final Color defaultColor;
    
    private Action action;
    private boolean shouldPlaySelectSound;
    
    private final boolean movesWithCamera;
    
    /**
     * Constructs a button.
     * @param pos the button's position
     * @param fontSize the button text's font size.
     * @param label the button's label.
     * @param fontName the name of the text font.
     * @param color the color of the button text.
     * @param action the action assigned to the button.
     * @param movesWithCamera if the button should move with the camera.
     */
    public Button(  Vector2f pos, 
                    int fontSize, 
                    String label, 
                    String fontName, 
                    Color color,
                    Action action,
                    boolean movesWithCamera) {
        
        // set action
        this.action = action;
        
        defaultColor = color;
        
        // set text
        Font font = CPrecache.getOpenSansFont();
        text = new Text();
        
        text.setFont(font);
        text.setCharacterSize(fontSize);
        text.setString(label);
        
        // set bounds for clicking
        FloatRect textbounds = text.getLocalBounds();
        
        text.setColor(color);
        text.setOrigin( textbounds.left + textbounds.width/2.0f,
                        textbounds.top  + textbounds.height/2.0f);
        text.setPosition(pos);
        this.movesWithCamera = movesWithCamera;
    }
    
    /**
     * Draws the button on the screen.
     */
    public void draw() {     
        if (movesWithCamera) {
            Window.getWindow().setView(Camera.getView());
            Window.getWindow().draw(text);
            Window.getWindow().setView(Window.getWindow().getDefaultView());
        } else {
            Window.getWindow().setView(Window.getWindow().getDefaultView());
            Window.getWindow().draw(text);
        }
    }
    
    /**
     * Handles mouse hovering.
     */
    public void handleMouseHover() {
        select();
    }
    
    /**
     * Plays a sound and changes the text color.
     */
    public void select() {
        if (shouldPlaySelectSound) {
            Audio.playSound("sounds/buttonsound1.wav", 1f);
        }
        text.setColor(Color.RED);
        Window.getWindow().draw(text);
    }
    
    /**
     * Sets the position of the text.
     * @param pos the position to be set.
     */
    public void setPosition(Vector2f pos) {
        text.setPosition(pos);
    }
    
    /**
     * Sets the button's text.
     * @param t the text to be set.
     */
    public void setText(String t) {
        text.setString(t);
    }
    
    /**
     * Sets the color of the button to the default color.
     */
    public void setToDefaultColor() {
        text.setColor(defaultColor);
    }
    
    /**
     * Sets the button's action.
     * @param action the action to be set.
     */
    public void setClickAction(Action action) {
        this.action = action;
    }
    
    /**
     * Executes the button's action.
     */
    public void executeAction() {
        this.action.execute();
    }
    
    /**
     * Gets the text object of the button.
     * @return the button's text object.
     */
    public Text getTextObject() {
        return text;
    }
    
    /**
     * Gets the action of the button.
     * @return the String representation of the button's action.
     */
    public Action getAction() {
        return action;
    }
    
    /**
     * Sets if the button should play the select sound.
     * @param b if the button should play the select sound.
     */
    public void shouldPlaySelectSound(boolean b) {
        this.shouldPlaySelectSound = b;
    }
    
}
