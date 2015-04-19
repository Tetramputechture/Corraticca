package coratticca.util.widget;

import coratticca.util.Window;
import coratticca.util.widget.widgetListener.CSelectListener;
import org.jsfml.graphics.Color;

/**
 * A CSelectListener that changes the color of the button on select,
 * and plays a sound.
 * @author Nick
 */
public class CStandardSelectListener implements CSelectListener {
    
    private final Color colorToChange;
    private final Window window;
    private boolean shouldPlaySound;
    
    /**
     * Sets the color to change when the Button is selected, and
     * the Window to play the select audio.
     * @param colorToChange the Color of the Button to change to when selected
     * @param window the Window to handle the select audio
     */
    public CStandardSelectListener(Color colorToChange, Window window) {
        this.colorToChange = colorToChange;
        this.window = window;
        shouldPlaySound = true;
    }

    @Override
    public void select(CButton button) {
        button.setTextColor(colorToChange);
        if (shouldPlaySound) {
            window.getAudioHandler().playSound("sounds/buttonsound1.wav", 1f);
        }
        shouldPlaySound = false;
    }

    @Override
    public void unselect(CButton button) {
        button.setTextColor(button.getDefaultTextColor());
        shouldPlaySound = true;
    }
}
