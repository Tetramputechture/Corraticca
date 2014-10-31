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
    private final Sprite bulletSprite;
    private final Entity bulletEntity;
    
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
        
        bulletSprite = new Sprite(bulletTexture);
        
        bulletSprite.setOrigin(Vector2f.div(new Vector2f(bulletTexture.getSize()), 2));
        
        bulletEntity = new BulletEntity(bulletSprite);
    }
    
    @Override
    public void execute() {
        bulletSprite.setRotation(PlayerEntity.getAngle());
        GameScreen.addEntity(bulletEntity); 
    }
    
    public Sprite getSprite() {
        return this.bulletSprite;
    }
    
    @Override
    public String toString() {
        return NAME;
    }
}
