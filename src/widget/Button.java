package coratticca.widget;

import coratticca.widget.widgetlistener.MouseListener;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import coratticca.vector.Vector2;

/**
 * A Widget that has a Label and a Frame, surrounding the Label.
 *
 * @author Nick
 */
public class Button extends Widget {

    /**
     * The Label of this Button.
     */
    private Label label;

    /**
     * The Frame of this Button.
     */
    private Frame frame;

    /**
     * The MouseListener of this Button. Can be null.
     */
    private MouseListener mouseListener;

    /**
     * Constructs a new CButton with a white border and transparent background.
     *
     * @param text the Text of the button.
     */
    public Button(Text text) {
        label = new Label(text);
        frame = new Frame(new RectangleShape(), Color.TRANSPARENT);

        updateFrame();
    }

    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        rt.draw(label);
        rt.draw(frame);
    }

    private void updateFrame() {
        RectangleShape buttonBorder = new RectangleShape();
        FloatRect textRect = label.getTextObject().getGlobalBounds();

        buttonBorder.setPosition(textRect.left, textRect.top);
        buttonBorder.setSize(new Vector2(textRect.width + 10, textRect.height + 10).toVector2f());
        buttonBorder.setFillColor(Color.TRANSPARENT);

        frame.setBorderRect(buttonBorder);
    }

    public void setPosition(float x, float y) {
        label.setPosition(x, y);
        updateFrame();
    }

    public Label getLabel() {
        return label;
    }

    /**
     * Sets the Label of this button, also changing the Frame of this button.
     *
     * @param label the Label to set.
     */
    public void setLabel(Label label) {
        this.label = label;
        updateFrame();
    }

    public Frame getFrame() {
        return frame;
    }

    /**
     * Adds a MouseListener to this Label.
     *
     * @param mouseListener the mouseListener to add.
     */
    public void addMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    /**
     * Removes this Label's mouseListener.
     */
    public void removeMouseListener() {
        mouseListener = null;
    }

    /**
     * Notifies this Label's mouseListener that this Label has been clicked.
     */
    public void setClicked() {
        mouseListener.mouseClicked(this);
    }

    /**
     * Notifies this Label's mouseListener that this Label has been released.
     */
    public void setReleased() {
        mouseListener.mouseReleased(this);
    }

    /**
     * Notifies this Label's mouseListener that this Label has been entered.
     */
    public void setEntered() {
        mouseListener.mouseEntered(this);
    }

    /**
     * Notifies this Label's mouseListener that this Label has been exited.
     */
    public void setExited() {
        mouseListener.mouseExited(this);
    }
}
