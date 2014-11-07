/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/** 
 * Action to create a bullet entity.
 * @author Nick
 */
public class FireAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "FIRE";
    
    private Texture bulletTexture;
    
    /**
     * Set's the bullet's sprite.
     */
    public FireAction() {
        
        bulletTexture = new Texture();
        String bulletTextureFile = "bullet.png";
       
        try {
            this.bulletTexture.loadFromFile(Paths.get(bulletTextureFile));
        } catch (IOException ex) {
            Logger.getLogger(PlayerEntity.class.getName()).log(java.util.logging.Level.SEVERE, 
                    String.format("Unable to load file %s!\n", bulletTextureFile), 
                    ex);
        }
        
        bulletTexture.setSmooth(true);
       
    }
    
    /**
     * Makes a bullet entity and adds it to the game screen.
     */
    @Override
    public void execute() {
        Sprite bulletSprite = new Sprite(bulletTexture);
        
        bulletSprite.setOrigin(Vector2f.div(new Vector2f(bulletTexture.getSize()), 2));

        Audio.playSound("firesound.wav", 1);
        GameScreen.addEntity(new BulletEntity(bulletSprite)); 
        GameScreen.fireShot();
    }

    @Override
    public String toString() {
        return NAME;
    }
}
