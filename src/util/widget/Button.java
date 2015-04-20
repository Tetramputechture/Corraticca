package coratticca.util.widget;

import coratticca.util.widget.widgetListener.MouseListener;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;


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
     * @param text the Text of the button.
     */
    public Button(Text text) {
        label = new Label(text);
        
        RectangleShape buttonBorder = new RectangleShape();
        FloatRect textRect = text.getGlobalBounds();
        
        buttonBorder.setPosition(textRect.left, textRect.top);
        buttonBorder.setSize(new Vector2f(textRect.width+10, textRect.height+10));
        buttonBorder.setOutlineColor(Color.WHITE);
        buttonBorder.setOutlineThickness(5);
        
        frame = new Frame(buttonBorder, Color.TRANSPARENT);
    }
    
    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        rt.draw(label);
        rt.draw(frame);
    }
    
    public Label getLabel() {
        return label;
    }
    
    public void setLabel(Label label) {
        this.label = label;
    }
    
    public Frame getFrame() {
        return frame;
    }
    
    public void setFrame(Frame frame) {
        this.frame = frame;
    }
    
    /**
     * Adds a MouseListener to this Label.
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
