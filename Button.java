/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;

/**
 * This class is for a button on a screen.
 * A button can have any action assigned to it.
 * If you just want to make a simple text object, assign the button to
 * Unassigned Action.
 * @author Nick
 */
public class Button extends Input {
    
    private final Text text;
    
    private Action action;
    
    /**
     * Constructs a button.
     * @param posX the x position of the button.
     * @param posY the y position of the button.
     * @param fontSize the button text's font size.
     * @param label the button's label.
     * @param fontName the name of the text font.
     * @param color the color of the button text.
     * @param action the action assigned to the button.
     */
    public Button(  int posX, 
                    int posY, 
                    int fontSize, 
                    String label, 
                    String fontName, 
                    Color color,
                    Action action) {
        
        // set action
        this.action = action;
        
        // set text
        Font font = new Font();
        
        Path f = FileSystems.getDefault().getPath(fontName);
                
        try {
            font.loadFromFile(f);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuScreen.class.getName()).log(Level.SEVERE, 
                    String.format("File %s not found!", f), 
                    ex);
        }
        
        text = new Text();
        
        text.setFont(font);
        text.setCharacterSize(fontSize);
        text.setString(label);
        
        // set bounds for clicking
        FloatRect textbounds = text.getLocalBounds();
        
        text.setColor(color);
        text.setOrigin( textbounds.left + textbounds.width/2.0f,
                        textbounds.top  + textbounds.height/2.0f);
        text.setPosition(posX, posY);
    }
    
    /**
     * Draws the button on the screen.
     */
    public void draw() {     
        Window.getWindow().draw(text);
    }
    
    /**
     * Sets the button's text.
     * @param t the text to be set.
     */
    public void setText(String t) {
        text.setString(t);
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
    public String getAction() {
        return action.toString();
    }
    
}
