/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Entities.AsteroidEntity;
import coratticca.Entities.PlayerEntity;
import coratticca.Utils.Screen.GameScreen;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public class SpawnAsteroidAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "SPAWN_ASTEROID";
    
    private final Texture asteroidTexture;

    public SpawnAsteroidAction() {
        
        asteroidTexture = new Texture();
        String asteroidTextureFile;
        if (Math.random() < 0.5) {
            asteroidTextureFile = "sprites/asteroid1.png";
        } else {
            asteroidTextureFile = "sprites/asteroid2.png";
        }
       
        try {
            this.asteroidTexture.loadFromFile(Paths.get(asteroidTextureFile));
        } catch (IOException ex) {
            Logger.getLogger(PlayerEntity.class.getName()).log(java.util.logging.Level.SEVERE, 
                    String.format("Unable to load file %s!%n", asteroidTextureFile), 
                    ex);
        }
        
        asteroidTexture.setSmooth(true);
    }
    
    @Override
    public void execute() {
        Sprite asteroidSprite = new Sprite(asteroidTexture);
        asteroidSprite.setOrigin(Vector2f.div(new Vector2f(asteroidTexture.getSize()), 2));
        
        GameScreen.addEntity(new AsteroidEntity(asteroidSprite));
    }
    
}
