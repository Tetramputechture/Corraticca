/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Actions.Action;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

/**
 * This class is for a button on a screen. A button can have any action assigned
 * to it. If you just want to make a simple text object, leave the action as
 * null.
 *
 * @author Nick
 */
public class Button {

    private final Window window;

    private final Text text;
    private final Color defaultColor;

    private Action action;
    private boolean shouldPlaySelectSound;

    private final Camera camera;

    /**
     * Constructs a button.
     *
     * @param window the window this Button is parented to.
     * @param pos the button's position
     * @param fontSize the button text's font size.
     * @param label the button's label.
     * @param fontName the name of the text font.
     * @param color the color of the button text.
     * @param action the action assigned to the button.
     */
    public Button(Window window,
            Vector2f pos,
            int fontSize,
            String label,
            String fontName,
            Color color,
            Action action) {

        this(window, pos, fontSize, label, fontName, color, action, null);
    }

    /**
     * Constructs a button.
     *
     * @param window the window this Button is parented to.
     * @param pos the button's position
     * @param fontSize the button text's font size.
     * @param label the button's label.
     * @param fontName the name of the text font.
     * @param color the color of the button text.
     * @param action the action assigned to the button.
     * @param camera the Camera for this Button to be parented to.
     */
    public Button(Window window,
            Vector2f pos,
            int fontSize,
            String label,
            String fontName,
            Color color,
            Action action,
            Camera camera) {

        this.window = window;

        // set action
        this.action = action;

        defaultColor = color;

        // set text
        Font font = CPrecache.getOpenSansFont();
        text = new Text();

        text.setFont(font);
        text.setCharacterSize(fontSize);
        text.setString(label);

        // set bounds for clicking
        FloatRect textbounds = text.getLocalBounds();

        text.setColor(color);
        text.setOrigin(textbounds.left + textbounds.width / 2.0f,
                textbounds.top + textbounds.height / 2.0f);
        text.setPosition(pos);
        this.camera = camera;
    }

    /**
     * Draws the button on the screen.
     */
    public void draw() {
        RenderWindow rw = window.getRenderWindow();
        if (camera != null) {
            rw.setView(camera.getView());
            rw.draw(text);
            rw.setView(window.getRenderWindow().getDefaultView());
        } else {
            rw.draw(text);
        }
    }

    /**
     * Plays a sound and changes the text color.
     */
    public void select() {
        if (shouldPlaySelectSound) {
            window.getAudioHandler().playSound("sounds/buttonsound1.wav", 1f);
            shouldPlaySelectSound = false;
        } 
        text.setColor(Color.RED);
        window.getRenderWindow().draw(text);
    }
    
    public boolean contains(Vector2f coords) {
        return text.getGlobalBounds().contains(coords);
    }

    /**
     * Sets the position of the text.
     *
     * @param pos the position to be set.
     */
    public void setPosition(Vector2f pos) {
        text.setPosition(pos);
    }

    /**
     * Sets the button's text.
     *
     * @param t the text to be set.
     */
    public void setText(String t) {
        text.setString(t);
    }

    /**
     * Sets the color of the button to the default color.
     */
    public void setToDefaultColor() {
        text.setColor(defaultColor);
        shouldPlaySelectSound = true;
    }

    /**
     * Sets the button's action.
     *
     * @param action the action to be set.
     */
    public void setClickAction(Action action) {
        this.action = action;
    }

    /**
     * Executes the button's action.
     */
    public void executeAction() {
        this.action.execute(window);
    }

    /**
     * Gets the text object of the button.
     *
     * @return the button's text object.
     */
    public Text getTextObject() {
        return text;
    }

    /**
     * Gets the action of the button.
     *
     * @return the String representation of the button's action.
     */
    public Action getAction() {
        return action;
    }
}
