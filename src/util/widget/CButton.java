/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.util.widget;

import coratticca.util.widget.widgetListener.CClickListener;
import coratticca.util.widget.widgetListener.CSelectListener;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;


/**
 * 
 *
 * @author Nick
 */
public class CButton extends CFrame {

    /**
     * Constructs a new CButton, and fits its size to according to the Text.
     *
     * @param position the position of this CButton.
     * @param text the Text object of this CButton.
     * @param view the View this CButton is parented to. Can be null.
     * @param borderColor the color of the border of this CButton.
     * @param backgroundColor the color of the background of this CButton.
     */
    public CButton(Vector2f position,
            Text text,
            View view,
            Color borderColor,
            Color backgroundColor) {
        
        super(position, text, view, borderColor, backgroundColor);
    }
    
    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        super.draw(rt, states);
    }

    /**
     * If this CClickable's state has been changed.
     */
    private boolean clicked = false;
    
    /**
     * The CClickListener listening to this CClickable.
     */
    private CClickListener clickListener;

    /**
     * Adds a CClickListener to this CClickable.
     * @param clickListener the CClickListener to add to this CClickable.
     */
    public void addClickListener(CClickListener clickListener) {
        this.clickListener = clickListener;
    }
    
    /**
     * Removes the CClickListener from this CClickable.
     */
    public void removeClickListener() {
        clickListener = null;
    }
    
    /**
     * If this CClickable has changed, notify all CListeners of the
     * change and indicate that this CClickable is no longer changed.
     */
    public void notifyClickListener() {
        if (!clicked || clickListener == null) {
            return;
        }
        
        clickListener.clicked(this);
        
        clearClicked();
    }
    
    /**
     * Mark this CClickable as having been clicked.
     */
    public void setClicked() {
        clicked = true;
    }
    
    /**
     * Mark this CClickable has no longer been clicked.
     */
    public void clearClicked() {
        clicked = false;
    }
    
    /**
     * If this CClickable has been clicked.
     * @return true if this CClickable has been clicked, false otherwise.
     */
    public boolean hasClicked() {
        return clicked;
    }
    
    /**
     * If this CSelectable's state has been selected.
     */
    private boolean selected = false;
    
    /**
     * The CSelectListener listening to this CSelectable.
     */
    private CSelectListener selectListener;

    /**
     * Adds a CSelectListener to this CSelectable.
     * @param selectListener the CSelectListener to add to this CSelectable.
     */
    public void addSelectListener(CSelectListener selectListener) {
        this.selectListener = selectListener;
    }
    
    /**
     * Removes the CSelectListener from this CSelectable.
     */
    public void removeSelectListener() {
        selectListener = null;
    }
    
    /**
     * If this CSelectable has changed, notify all CListeners of the
     * change and indicate that this CSelectable is no longer changed.
     */
    public void notifySelectListener() {
        if (!selected || selectListener == null) {
            return;
        }

        selectListener.select(this);
    }
    
    public void notifyUnselectListener() {
        if (selected || selectListener == null) {
            return;
        }
        
        selectListener.unselect(this);
    }
    
    /**
     * Mark this CSelectable as having been selected.
     */
    public void setSelected() {
        selected = true;
    }
    
    /**
     * Mark this CSelectable has no longer been selected. 
     * Changes the background color back to the assigned backgroundColor.
     */
    public void clearSelected() {
        selected = false;
        borderRect.setFillColor(backgroundColor);
    }
    
    /**
     * If this CSelectable has been selected.
     * @return true if this CSelectable has been selected, false otherwise.
     */
    public boolean hasSelected() {
        return selected;
    }
    
}
