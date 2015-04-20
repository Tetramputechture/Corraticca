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
            rt.draw(text);
        }
    }
    
    public String getText() {
        return text.getString();
    }
    
    public void setText(String text) {
        this.text.setString(text);
    }
    
    public void setPosition(Vector2f position) {
        text.setPosition(position);
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
