package coratticca.util.widget.widgetListener;

import coratticca.util.widget.CButton;

/**
 *
 * @author Nick
 */
public interface CSelectListener extends CListener {
    
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
