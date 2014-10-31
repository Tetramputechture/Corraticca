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
        this.clock = new Clock();
        
        this.bulletTexture = new Texture();
        String bulletTextureFile = "bullet.png";
       
        try {
            this.bulletTexture.loadFromFile(Paths.get(bulletTextureFile));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, 
                    String.format("Unable to load file %s!\n", bulletTextureFile), 
                    ex);
        }
        
        this.bulletTexture.setSmooth(true);
        
        this.bulletSprite = new Sprite(bulletTexture);
        
        this.bulletSprite.setOrigin(Vector2f.div(new Vector2f(bulletTexture.getSize()), 2));
        
        x = (float) (Player.getPos().x + 20 + Math.random() * 100);
        System.out.println(x);
        y = (float) (Player.getPos().y + 20 + Math.random() * 100);
        System.out.println(y);
        this.bulletSprite.setRotation((float)Player.getAngle());
    }
    
    @Override
    public void execute() {
        //move();
        
        this.bulletSprite.setPosition(x, y);
        
        Window.getWindow().draw(this.bulletSprite);
    }
    
    private void move() {
        x += vx;
        y -= vy;
    }
    
    @Override
    public String toString() {
        return NAME;
    }
}
