package coratticca;

import coratticca.util.Precache;
import coratticca.util.Window;
import coratticca.util.screen.MainMenuScreen;
import java.io.IOException;
import org.jsfml.system.Vector2i;

/**
 * The main method of the game.
 * @author Nick
 */
public class Coratticca {

    public static void main(String[] args) throws IOException {
        
        Precache.precacheFonts();
        Precache.precacheTextures();
        
        Vector2i pos = new Vector2i(1920/4, 1080/4);
        
        Window w = new Window(pos, "Floppy Disk Laser Shooter In Space - Alpha");
        Window.setCurrentScreen(new MainMenuScreen());
        w.display();

    }
    
}
