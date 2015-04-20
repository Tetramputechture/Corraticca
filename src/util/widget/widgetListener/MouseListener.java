package coratticca.util.widget.widgetListener;

import coratticca.util.widget.Button;

/**
 *
 * @author Nick
 */
public interface MouseListener extends Listener {
    
    /**
     * Called when the mouse is clicked on a button's bounding rectangle.
     * @param b the button that was clicked.
     */
    void mouseClicked(Button b);
    
    /**
     * Called when the mouse is released on a button's bounding rectangle.
     * @param b the button that was released.
     */
    void mouseReleased(Button b);
    
    /**
     * Called when the mouse enters the button's bounding rectangle.
     * @param b the button that was entered.
     */
    void mouseEntered(Button b);
    
    /**
     * Called when the mouse exits the button's bounding rectangle.
     * @param b the button that was exited.
     */
    void mouseExited(Button b);
    
}
