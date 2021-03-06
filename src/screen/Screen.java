package coratticca.screen;

import coratticca.widget.Widget;
import java.util.ArrayList;
import java.util.List;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import coratticca.vector.Vector2;

/**
 * A Screen abstract class.
 *
 * @author Nick
 */
public abstract class Screen implements Drawable {

    protected final ArrayList<Widget> widgets;

    protected Color bgColor;

    public Screen() {
        bgColor = Color.BLACK;
        widgets = new ArrayList<>();
    }

    @Override
    public abstract void draw(RenderTarget rendertarget, RenderStates states);

    public abstract void updateWidgets(Vector2 screenSize);

    /**
     * Gets the widgets of the screen.
     *
     * @return a List containing the screen's widgets.
     */
    public List<Widget> getWidgets() {
        return widgets;
    }

    public void setBGColor(Color bgColor) {
        this.bgColor = bgColor;
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
