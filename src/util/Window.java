package coratticca.util;

import coratticca.util.screen.GameScreen;
import coratticca.util.screen.PauseMenuScreen;
import coratticca.util.screen.Screen;
import coratticca.util.screen.ScreenName;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;

/**
 * A Window class to handle the game's window. Singleton.
 *
 * @author Nick
 */
public final class Window {

    public static final Vector2f size;

    private static Screen currentScreen;

    private static final RenderWindow renderWindow;

    private static final Input inputHandler;

    private static final Audio audioHandler;

    static {
        size = new Vector2f(640, 480);

        renderWindow = new RenderWindow();

        renderWindow.setVerticalSyncEnabled(true);

        inputHandler = new Input();

        audioHandler = new Audio();
    }

    public Window(Vector2i pos, String title) throws IOException {
        renderWindow.create(new VideoMode((int) size.x, (int) size.y), title);
        renderWindow.setPosition(pos);
        renderWindow.setVerticalSyncEnabled(true);
    }

    public static Vector2f getSize() {
        return size;
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static void setCurrentScreen(Screen screenToSet) {
        currentScreen = screenToSet;
    }

    public static RenderWindow getRenderWindow() {
        return renderWindow;
    }

    public static Input getInputHandler() {
        return inputHandler;
    }

    public static Audio getAudioHandler() {
        return audioHandler;
    }

    public void display() {
        while (renderWindow.isOpen()) {
            // poll events
            handleEvents();
            // show the current screen
            renderWindow.draw(currentScreen);
        }
    }

    public static Sprite getBGSprite() {
        Image i = renderWindow.capture();
        Texture t = new Texture();
        try {
            t.loadFromImage(i);
        } catch (TextureCreationException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Sprite(t);
    }

    /**
     * Polls the window's events. (Move to inputHandler?)
     */
    public void handleEvents() {

        for (Event event : renderWindow.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    renderWindow.close();
                    break;

                case LOST_FOCUS:
                    if (currentScreen instanceof GameScreen) {
                        currentScreen = new PauseMenuScreen((GameScreen) currentScreen);
                    }
                    break;

                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    inputHandler.handleKeyInput(keyEvent);
                    break;

                case MOUSE_BUTTON_PRESSED:
                    MouseButtonEvent mouseButtonEvent = event.asMouseButtonEvent();
                    inputHandler.handleMouseClickInput(mouseButtonEvent);
                    break;

                case MOUSE_MOVED:
                    MouseEvent mouseEvent = event.asMouseEvent();
                    inputHandler.handleMouseMoveInput(mouseEvent);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Gets the current screen name.
     *
     * @return the name of the current screen being displayed.
     */
    public ScreenName getCurrentScreenName() {
        return currentScreen.getName();
    }

    /**
     * Gets the center of the window.
     *
     * @return the window's center.
     */
    public Vector2f getCenter() {
        return new Vector2f(renderWindow.getSize().x / 2, renderWindow.getSize().y / 2);
    }

    /**
     * Sets the window's position.
     *
     * @param pos the position to be set.
     */
    public void setPos(Vector2i pos) {
        renderWindow.setPosition(pos);
    }

    /**
     * Gets the window's position.
     *
     * @return an integer vector of the window's position.
     */
    public Vector2i getPos() {
        return renderWindow.getPosition();
    }

    /**
     * Sets the window's title.
     *
     * @param title the title to be set.
     */
    public void setTitle(String title) {
        renderWindow.setTitle(title);
    }
}
