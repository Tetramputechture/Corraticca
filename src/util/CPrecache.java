package coratticca.util;

import coratticca.entity.PlayerEntity;
import coratticca.util.screen.MainMenuScreen;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Texture;

/**
 * Handles initializing / precaching of all textures used in the game.
 * @author Nick
 */
public class CPrecache {
    
    private static Texture starfieldTexture;
    
    private static Texture pointerTexture;
    
    private static Texture playerTexture;
    
    private static Texture bulletTexture;
    
    private static Texture asteroidTextureA;
    private static Texture asteroidTextureB;
    
    private static Texture enemyTexture;
    
    private static Font openSansFont;
    
    public static void precacheTextures() {
        precacheStarfieldTexture();
        
        precachePointerTexture();
        
        precachePlayerTexture();
        
        precacheBulletTexture();
        
        precacheAsteroidTextures();
        
        precacheEnemyTexture();
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
    
    private static void precacheEnemyTexture() {
        enemyTexture = loadTexture("sprites/enemy.png");
    }
    
    public static Texture getStarfieldTexture() {
        return starfieldTexture;
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
    
    public static Texture getEnemyTexture() {
        return enemyTexture;
    }
    
    public static Font getOpenSansFont() {
        return openSansFont;
    }
}
