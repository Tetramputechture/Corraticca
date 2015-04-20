package coratticca.util.widget.widgetListener;

import coratticca.action.Action;
import coratticca.util.Audio;
import coratticca.util.widget.Button;
import org.jsfml.graphics.Color;

/**
 * A CMouseListener that can play a sound and change color on mouse entrance, and performs an
 * Action on click.
 * @author Nick
 */
public class ButtonAdapter extends MouseAdapter {
    
    private final Action clickAction;
    private final String soundFileName;
    private final Color entranceColor;
    private final Audio audio;
    
    private final Color exitColor;
    
    private boolean shouldPlaySound;
    
    /**
     * Constructs a new CButtonAdapater.
     * @param clickAction the Action to perform on click.
     * @param soundFileName the Sound to play when the mouse enters the label.
     * Null for no sound.
     * @param entranceColor the Color for the button to change when the mouse enters the label.
     * Null for no color change.
     * @param exitColor the Color for the button to change when the mouse exits the Label.
     * Null for no color change.
     * @param audio the Audio to play the Sound when the mouse enters the label.
     * Null if no sound.
     */
    public ButtonAdapter(Action clickAction, 
            String soundFileName, 
            Color entranceColor,
            Color exitColor,
            Audio audio) {
        this.clickAction = clickAction;
        this.soundFileName = soundFileName;
        this.entranceColor = entranceColor;
        this.exitColor = exitColor;
        this.audio = audio;
    }
    
    @Override
    public void mouseClicked(Button b) {
        clickAction.execute();
    }

    @Override
    public void mouseEntered(Button b) {
        if (shouldPlaySound && soundFileName != null) {
            audio.playSound(soundFileName, 1f);
        }
        shouldPlaySound = false;
        if (entranceColor != null) {
            b.getLabel().setColor(entranceColor);
        }
    }
    
    @Override
    public void mouseExited(Button b) {
        if (exitColor != null) {
            b.getLabel().setColor(exitColor);
        }
        shouldPlaySound = true;
    }
}
