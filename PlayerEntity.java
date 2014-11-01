/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public final class PlayerEntity extends Entity {
    
    private static final Texture playerTexture;
    
    private static final Sprite playerSprite;
    
    private static double angle;
    
    private static float x;
    private static float y;
    private static float vx;
    private static float vy;
    private static float targetX;
    private static float targetY;
    
    public PlayerEntity(Sprite s) {
        super(s);
    }
    
    static {
        playerTexture = new Texture();
        String playerTextureFile = "player.png";
       
        try {
            playerTexture.loadFromFile(Paths.get(playerTextureFile));
        } catch (IOException ex) {
            Logger.getLogger(PlayerEntity.class.getName()).log(Level.SEVERE, 
                    String.format("Unable to load file %s!\n", playerTextureFile), 
                    ex);
        }
        
        playerTexture.setSmooth(true);
        
        playerSprite = new Sprite(playerTexture);
        
        playerSprite.setOrigin(Vector2f.div(new Vector2f(playerTexture.getSize()), 2));
        
        playerSprite.setPosition(Window.getWidth()/2, Window.getHeight()/2);
        x = playerSprite.getPosition().x;
        y = playerSprite.getPosition().y;
    }
    
    public static void setAngle(float angle) {
        playerSprite.setRotation(angle);
    }
    
    public static void setPos(int x, int y) {
        playerSprite.setPosition(x, y);
    }
    
    public static Sprite getSprite() {
        return playerSprite;
    }
    
    public static Vector2f getPos() {
        return playerSprite.getPosition();
    }

    public static Vector2f getSize() {
        return new Vector2f(playerSprite.getLocalBounds().width, playerSprite.getLocalBounds().height);
    }
    
    public static float getAngle() {
        return playerSprite.getRotation();
    }

    @Override
    public void update(float dt) {
        
        vx += (targetX - vx) * 100 * dt;
        vy += (targetY - vy) * 100 * dt;
        x += vx * dt;
        y += vy * dt;
        
        playerSprite.setPosition(x, y);
        
        angle = Math.atan2( Input.getMousePos().y - playerSprite.getPosition().y, 
                            Input.getMousePos().x - playerSprite.getPosition().x);
        
        angle *= (180/Math.PI);
        if(angle < 0) {
            angle = 360 + angle;
        }
        
        playerSprite.setRotation(90 + (float)angle);
    }
    
    public static void moveUp() {
        targetY = -1;
    }
    
    public static void moveDown() {
        targetY = 1;
    }
        
    public static void moveLeft() {
        targetX = -1;
    }
    
    public static void moveRight() {
        targetX  = 1;
    }
}
