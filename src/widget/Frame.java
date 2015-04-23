package coratticca.widget;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import coratticca.vector.Vector2;

/**
 * A Widget that has a border and a fill color.
 *
 * @author Nick
 */
public class Frame extends Widget {

    private RectangleShape borderRect;

    private FloatRect boundingRect;

    public Frame(RectangleShape borderRect, Color fillColor) {
        this.borderRect = borderRect;
        this.borderRect.setFillColor(fillColor);

        boundingRect = borderRect.getGlobalBounds();
    }

    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        if (view != null) {
            rt.setView(view);
            rt.draw(borderRect);
            rt.setView(rt.getDefaultView());
        } else {
            rt.draw(borderRect);
        }
    }

    public void setBorderRect(RectangleShape borderRect) {
        this.borderRect = borderRect;
        boundingRect = borderRect.getGlobalBounds();
    }

    public void setBorderColor(Color color) {
        borderRect.setOutlineColor(color);
    }

    public Color getBorderColor() {
        return borderRect.getOutlineColor();
    }

    public void setFillColor(Color color) {
        borderRect.setFillColor(color);
    }

    public Color getFillColor() {
        return borderRect.getFillColor();
    }

    public boolean contains(Vector2 v) {
        return boundingRect.contains(v.toVector2f());
    }
}
