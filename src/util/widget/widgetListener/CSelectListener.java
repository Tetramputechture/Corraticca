/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.util.widget.widgetListener;

import coratticca.util.widget.CButton;

/**
 *
 * @author Nick
 */
public interface CSelectListener {
    
    /**
     * Called whenever the selectable object is selected. 
     * @param button the button object
     */
    public void select(CButton button);
    
    /**
     * Called whenever the selectable object is unselected.
     * @param button the button object
     */
    public void unselect(CButton button);
    
}
