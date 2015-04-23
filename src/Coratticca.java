package coratticca;

import coratticca.util.PrecacheUtils;
import coratticca.window.Window;
import coratticca.screen.MainMenuScreen;
import coratticca.vector.Vector2;
import java.io.IOException;

/**
 * The main method of the game.
 *
 * @author Nick
 */
public class Coratticca {

    public static void main(String[] args) throws IOException {

        PrecacheUtils.precacheFonts();
        PrecacheUtils.precacheTextures();

        Vector2 pos = new Vector2(1920 / 4, 1080 / 4);

        Window w = new Window(pos, "Floppy Disk Laser Shooter In Space - Alpha");
        Window.setCurrentScreen(new MainMenuScreen());
        w.display();

    }

}
