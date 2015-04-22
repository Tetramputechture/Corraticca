package coratticca.util.widget;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

/**
 * A Label 
 * @author Nick
 */
public class Label extends Widget {
    
    /**
     * The Text object for this Label.
     */
    protected Text text;
    
    /**
     * Creates a new CLabel.
     * @param text the text of this CLabel.
     */
    public Label(Text text) {
        this.text = text;
    }
        
    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        if (view != null) {
            rt.setView(view);
            rt.draw(text);
            rt.setView(rt.getDefaultView());
        } else {
            rt.setView(rt.getDefaultView());
            rt.draw(text);
        }
    }
    
    public Text getTextObject() {
        return text;
    }
    
    public String getText() {
        return text.getString();
    }
    
    public void setText(String text) {
        this.text.setString(text);
    }
    
    public void setPosition(float x, float y) {
        text.setPosition(new Vector2f(x, y));
    }
    
    public Vector2f getPosition() {
        return text.getPosition();
    }
    
    public void setColor(Color color) {
        text.setColor(color);
    }
    
    public Color getColor() {
        return text.getColor();
    }
    
}
