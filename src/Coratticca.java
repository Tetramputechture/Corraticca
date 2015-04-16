/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import coratticca.Utils.CPrecache;
import coratticca.Utils.Window;
import java.io.IOException;
import org.jsfml.system.Vector2i;

/**
 * The main method of the game.
 * @author Nick
 */
public class Coratticca {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        CPrecache.precacheFonts();
        CPrecache.precacheTextures();
        
        Vector2i pos = new Vector2i(1366/4, 768/4);
        Vector2i size = new Vector2i(640, 480);
        
        Window w = new Window(pos, size, "Floppy Disk Laser Shooter In Space - Alpha");
        w.display();
        
    }
    
}
