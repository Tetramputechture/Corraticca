package coratticca.util;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Text;

/**
 * A few useful functions for Text objects.
 * @author Nick
 */
public class CText {
    
    public static void setOriginToCenter(Text text) {
        FloatRect textbounds = text.getLocalBounds();
        text.setOrigin( textbounds.left + textbounds.width/2.0f,
                        textbounds.top  + textbounds.height/2.0f);
    }
    
}
