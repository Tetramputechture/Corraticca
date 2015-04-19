package coratticca.util.widget;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

/**
 * A Widget with a colored border and background color.
 *
 * @author Nick
 */
public class CFrame extends CWidget {
    
    /**
     * The border rectangle of this CFrame.
     */
    protected RectangleShape borderRect;

    /**
     * The color of the border rectangle for this CFrame.
     */
    protected Color borderColor;

    /**
     * The background color for this CFrame.
     */
    protected Color backgroundColor;

    /**
     * Constructs a new CFrame, and fits its size to according to the Text.
     *
     * @param position the position of this CFrame.
     * @param text the Text object of this CFrame.
     * @param view the View this CFrame is parented to. Can be null.
     * @param borderColor the color of the border of this CFrame.
     * @param backgroundColor the color of the background of this CFrame.
     */
    public CFrame(Vector2f position,
            Text text,
            View view,
            Color borderColor,
            Color backgroundColor) {
        
        super(position, text, view);
        
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        initBorderRect();
    }
    
    /**
     * Initializes the border rectangle via borderColor and backgroundColor.
     */
    private void initBorderRect() {
        borderRect = new RectangleShape();
       
        FloatRect textRect = text.getGlobalBounds();
        
        borderRect.setOrigin(text.getOrigin());
        borderRect.setPosition(textRect.left, textRect.top-12);
        borderRect.setSize(new Vector2f(size.x, size.y+23));
        borderRect.setOutlineColor(borderColor);
        borderRect.setFillColor(backgroundColor);
        borderRect.setOutlineThickness(5);
    }
    
    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        if (view != null) {
            rt.setView(view);
            text.draw(rt, states);
            borderRect.draw(rt, states);
            rt.setView(rt.getDefaultView());
        } else {
            text.draw(rt, states);
            borderRect.draw(rt, states);
        }
    }

    /**
     * @return the borderColor
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * @param borderColor the borderColor to set
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * @return the backgroundColor
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
