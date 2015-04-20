package coratticca.util.screen;

import coratticca.util.widget.Widget;
import java.util.ArrayList;
import java.util.List;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

/**
 * A Screen abstract class.
 * @author Nick
 */
public abstract class Screen implements Drawable {
    
    protected final ArrayList<Widget> widgets;
    
    protected Color bgColor;
    
    public Screen(Color bgColor) {
        widgets = new ArrayList<>();
        this.bgColor = bgColor;
    }
    
    @Override
    public abstract void draw(RenderTarget rendertarget, RenderStates states);
    
    /**
     * Gets the widgets of the screen.
     * @return a List containing the screen's widgets.
     */
    public List<Widget> getWidgets() {
        return widgets;
    }

    /**
     * @return the bgColor
     */
    public Color getBgColor() {
        return bgColor;
    }
        
    /**
     * @param bgColor the bgColor to set
     */
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }
        
    public abstract ScreenName getName();
    
}
