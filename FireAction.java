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
 *
 * @author Nick
 */
public class FireAction implements Action {
    
    public static final String NAME = "FIRE_ACTION";
    
    private Texture bulletTexture;
    
    public FireAction() {
        System.out.println("Fire key pressed!");
        
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
    
    @Override
    public void execute() {
        Sprite bulletSprite = new Sprite(bulletTexture);
        
        bulletSprite.setOrigin(Vector2f.div(new Vector2f(bulletTexture.getSize()), 2));
        
        Entity bulletEntity = new BulletEntity(bulletSprite);
        bulletSprite.setRotation(PlayerEntity.getAngle());
        GameScreen.addEntity(bulletEntity); 
    }

    @Override
    public String toString() {
        return NAME;
    }
}
