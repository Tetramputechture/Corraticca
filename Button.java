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
 *
 * @author Nick
 */
public class Button extends Input {
    
    private final String fontName;
    
    private final String label;
    
    private final Text text;
    
    private final int posX;
    private final int posY;
    
    private final int fontSize;
    
    private final Color color;
    
    private Action action;
    
    public Button(  int posX, 
                    int posY, 
                    int fontSize, 
                    String label, 
                    String fontName, 
                    Color color,
                    Action action) {
        
        this.posX = posX;
        this.posY = posY;
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.color = color;
        this.label = label;
        this.action = action;
        
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
        text.setColor(color);
        FloatRect textbounds = text.getLocalBounds();
        text.setOrigin( textbounds.left + textbounds.width/2.0f,
                        textbounds.top  + textbounds.height/2.0f);
        text.setPosition(posX, posY);
    }
    
    public void draw() {     
        Window.getWindow().draw(text);
    }
    
    public void setText(String t) {
        text.setString(t);
    }
    
    public void setClickAction(Action action) {
        this.action = action;
    }
    
    public void executeAction() {
        this.action.execute();
    }
    
    public Text getTextObject() {
        return text;
    }
    
    public String getAction() {
        return action.toString();
    }
    
}
