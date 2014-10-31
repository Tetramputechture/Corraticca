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
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public class FireAction implements Action {
    
    public static final String NAME = "FIRE_ACTION";
    
    private Texture bulletTexture;
    private final Sprite bulletSprite;
    private final int vx = 20;
    private final int vy = 20;
    private Clock clock;
    private float x;
    private float y;
    private float dt;
    
    public FireAction() {
        System.out.println("Fire key pressed!");
        clock = new Clock();
        
        bulletTexture = new Texture();
        String bulletTextureFile = "bullet.png";
       
        try {
            this.bulletTexture.loadFromFile(Paths.get(bulletTextureFile));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, 
                    String.format("Unable to load file %s!\n", bulletTextureFile), 
                    ex);
        }
        
        bulletTexture.setSmooth(true);
        
        bulletSprite = new Sprite(bulletTexture);
        
        bulletSprite.setOrigin(Vector2f.div(new Vector2f(bulletTexture.getSize()), 2));
        
        dt = 0;
    }
    
    @Override
    public void execute() {
        dt = clock.getElapsedTime().asSeconds();
        x = Player.getPos().x + 20 * dt;
        y = Player.getPos().y + 20 * dt;
        bulletSprite.setRotation((float)Player.getAngle());
        
        Entity bulletEntity = new Entity(bulletSprite);
        
        GameScreen.addEntity(bulletEntity);
        
        bulletSprite.setPosition(x, y);
    }
    
    private void move() {
        x += vx;
        y -= vy;
    }
    
    public Sprite getSprite() {
        return this.bulletSprite;
    }
    
    @Override
    public String toString() {
        return NAME;
    }
}
