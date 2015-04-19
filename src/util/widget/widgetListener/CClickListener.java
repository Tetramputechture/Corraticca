package coratticca.util.widget.widgetListener;

import coratticca.util.widget.CButton;

/**
 * A Listener that listens to a Widget and is notified of a Widget's state
 * change.
 * @author Nick
 */
public interface CClickListener extends CListener  {
    
    /**
     * Called whenever the listener object is changed. 
     * @param button the button object
     */
    public void clicked(CButton button);
    
}
