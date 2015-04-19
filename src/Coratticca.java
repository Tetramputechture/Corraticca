package coratticca;

import coratticca.util.CPrecache;
import coratticca.util.Window;
import java.io.IOException;
import org.jsfml.system.Vector2i;

/**
 * The main method of the game.
 * @author Nick
 */
public class Coratticca {

    public static void main(String[] args) throws IOException {
        
        CPrecache.precacheFonts();
        CPrecache.precacheTextures();
        
        Vector2i pos = new Vector2i(1920/4, 1080/4);
        Vector2i size = new Vector2i(640, 480);
        
        Window w = new Window(pos, size, "Floppy Disk Laser Shooter In Space - Alpha");
        w.display();

    }
    
}
