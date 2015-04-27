package coratticca.util;

import coratticca.entity.gameentity.PlayerEntity;
import coratticca.screen.MainMenuScreen;
import coratticca.vector.Vector2;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

/**
 * Handles initializing / precaching of all textures used in the game.
 *
 * @author Nick
 */
public class PrecacheUtils {

    private static Texture starfieldTexture;

    private static Texture pointerTexture;

    private static Texture playerTexture;

    private static Texture bulletTexture;

    private static Texture asteroidTextureA;
    private static Texture asteroidTextureB;

    private static Font openSansFont;

    public static void precacheTextures() {
        precacheStarfieldTexture();

        precachePointerTexture();

        precachePlayerTexture();

        precacheBulletTexture();

        precacheAsteroidTextures();
    }

    public static void precacheFonts() {
        openSansFont = loadFont("fonts/OpenSans-Regular.ttf");
    }

    private static Font loadFont(String filepath) {
        Font f = new Font();
        try {
            f.loadFromFile(Paths.get(filepath));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuScreen.class.getName()).log(Level.SEVERE,
                    String.format("File %s not found!", filepath),
                    ex);
        }

        return f;
    }

    private static Texture loadTexture(String filepath) {
        Texture t = new Texture();
        try {
            t.loadFromFile(Paths.get(filepath));
        } catch (IOException ex) {
            Logger.getLogger(PlayerEntity.class.getName()).log(Level.SEVERE,
                    String.format("Unable to load file %s!%n", filepath),
                    ex);
        }
        t.setSmooth(true);
        return t;
    }

    private static void precacheStarfieldTexture() {
        starfieldTexture = loadTexture("sprites/starfield.png");
    }

    private static void precachePointerTexture() {
        pointerTexture = loadTexture("sprites/pointer.png");
    }

    private static void precachePlayerTexture() {
        playerTexture = loadTexture("sprites/player.png");
    }

    private static void precacheBulletTexture() {
        bulletTexture = loadTexture("sprites/bullet.png");
    }

    private static void precacheAsteroidTextures() {
        asteroidTextureA = loadTexture("sprites/asteroidA.png");
        asteroidTextureB = loadTexture("sprites/asteroidB.png");
    }

    public static Texture getStarfieldTexture() {
        return starfieldTexture;
    }

    public static Texture getRandomStarfieldTexture(Vector2 textureSize) {
        int[] pixels = new int[(int) (textureSize.x * textureSize.y)];
        int rows = (int) textureSize.y;
        int cols = (int) textureSize.x;

        // calculate ARGB values for white and black pixels
        int argbWhite = ((0xff) << 24) 
                | ((255 & 0x0ff) << 16)
                | ((255 & 0x0ff) << 8)
                | (255 & 0x0ff);
        
        int argbBlack = ((255 & 0xff) << 24) 
                | ((0 & 0x0ff) << 16)
                | ((0 & 0x0ff) << 8)
                | (0 & 0x0ff);
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = row * cols + col;
                if (Math.random() < 0.0005) {
                    pixels[index] = argbWhite;
                } else {
                    pixels[index] = argbBlack;
                }
            }
        }
        
        Image i = new Image();
        i.create(cols, rows, pixels);
        
        Texture t = new Texture();
        try {
            t.loadFromImage(i);
        } catch (TextureCreationException ex) {
            Logger.getLogger(PrecacheUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return t;
    }

    public static Texture getPointerTexture() {
        return pointerTexture;
    }

    public static Texture getPlayerTexture() {
        return playerTexture;
    }

    public static Texture getBulletTexture() {
        return bulletTexture;
    }

    public static Texture getAsteroidTextureA() {
        return asteroidTextureA;
    }

    public static Texture getAsteroidTextureB() {
        return asteroidTextureB;
    }

    public static Font getOpenSansFont() {
        return openSansFont;
    }
}
