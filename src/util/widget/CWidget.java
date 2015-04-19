/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.util.widget;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

/**
 * A GUI element.
 *
 * @author Nick
 */
public abstract class CWidget implements Drawable  {

    /**
     * The position of this CWidget.
     */
    protected Vector2f position;

    /**
     * The size of this CWidget.
     */
    protected Vector2f size;

    /**
     * The bounding rectangle of this CWidget.
     */
    protected FloatRect boundingRect;

    /**
     * The Text object used by this CWidget. 
     */
    protected Text text;

    
    /**
     * The View this CWidget is parented to.
     * Can be null.
     */
    protected View view;

    /**
     * Constructs a new CWidget, and fits its size to according to the Text.
     *
     * @param position the position of this CWidget.
     * @param text the Text object of this CWidget.
     * @param view the View this CWidget is parented to. Can be null.
     */
    public CWidget(Vector2f position, Text text, View view) {
        this.position = position;
        this.text = text;
        this.text.setPosition(position);
        this.view = view;
        boundingRect = text.getGlobalBounds();
        this.size = new Vector2f(boundingRect.width, boundingRect.height);
    }

    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        if (view != null) {
            rt.setView(view);
            text.draw(rt, states);
            rt.setView(rt.getDefaultView());
        } else {
            text.draw(rt, states);
        }
    }
    
    public void setView(View view) {
        this.view = view;
    }
    
    
    /**
     * Returns if this Widget contains a point (x, y).
     * @param coords the coordinates.
     * @return true if this Widget's bounding rectangle contains the coordinates,
     * false otherwise.
     */
    public boolean contains(Vector2f coords) {
        return boundingRect.contains(coords);
    }

    /**
     * Returns the position of this CWidget.
     *
     * @return The position of this CWidget, as a Vector2f (x, y).
     */
    public Vector2f getPosition() {
        return text.getPosition();
    }

    /**
     * Sets the position of this CWidget.
     *
     * @param position The position of this CWidget to set.
     */
    public void setPosition(Vector2f position) {
        text.setPosition(position);
    }

    /**
     * Sets the size of this CWidget. Affects the interactive bounding
     * rectangle.
     *
     * @param size the size to set this CWidget to: (width, height)
     */
    public void setSize(Vector2f size) {
        this.size = size;
        boundingRect = new FloatRect(text.getPosition(), size);
    }
    
    /**
     * Returns the size of this CWidget.
     * 
     * @return a Vector defined by the (width, height) of this CWidget.
     */
    public Vector2f getSize() {
        return size;
    }

    /**
     * Returns the text of this CWidget.
     *
     * @return The text of this CWidget. Can be null.
     */
    public String getTextString() {
        return text.getString();
    }

    /**
     * Sets the String used by the text of this CWidget.
     *
     * @param textString the String of the Text of this CWidget to use.
     */
    public void setTextString(String textString) {
        text.setString(textString);
    }

    /**
     * Gets the Font used by the text of this CWidget.
     *
     * @return The Font used by the text of this CWidget. Can be null if this
     * CWidget has no text.
     */
    public Font getTextFont() {
        return (Font)text.getFont();
    }

    /**
     * Sets the Font used by the text of this CWidget.
     *
     * @param textFont the Font for the Text of this CWidget to use. No effect
     * if this CWidget has no text.
     */
    public void setTextFont(Font textFont) {
        text.setFont(textFont);
    }

    /**
     * Gets the color of the text of this CWidget.
     *
     * @return the text color of this CWidget. Can be null if this CWidget has
     * no text.
     */
    public Color getTextColor() {
        return text.getColor();
    }

    /**
     * Sets the color of the text of this CWidget.
     *
     * @param textColor the text color to set this CWidget's text to. No effect
     * if this CWidget has no text.
     */
    public void setTextColor(Color textColor) {
        text.setColor(textColor);
    }
}
