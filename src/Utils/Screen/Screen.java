/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils.Screen;

import coratticca.Utils.Button;
import coratticca.Utils.Window;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jsfml.graphics.Color;

/**
 * A Screen abstract class.
 * @author Nick
 */
public abstract class Screen {
    
    protected final Window window;
    
    protected final ArrayList<Button> buttons;
    
    private Color bgColor;
    
    public Screen(Window window) {
        this.window = window;
        buttons = new ArrayList<>();
    }
    
    /**
     * Shows the screen.
     */
    public abstract void show();
    
    public Window getWindow() {
        return window;
    }
    
    /**
     * Gets the buttons of the screen.
     * @return a List containing the screen's buttons.
     */
    public List<Button> getButtons() {
        return Collections.unmodifiableList(buttons);
    }

    /**
     * @return the bgColor
     */
    public Color getBgColor() {
        return bgColor;
    }
    
    public abstract ScreenName getName();
        
    /**
     * @param bgColor the bgColor to set
     */
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }
    
}
