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
    private static final float moveSpeed;
    private static final float accelRate;
    private static final boolean removable;
    
    public PlayerEntity(Sprite s) {
        super(s);
    }
    
    static {
        
        // should always be in the screen
        removable = false;
        
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
        moveSpeed = 200;
        accelRate = 50;
    }
    
    @Override
    public void update(float dt) {
        
        float desiredX = targetX;
        float desiredY = targetY;

        // Now find true velocity by getting the length of the vector using simple trigonometry. This is just Pythagora's theorem, with desiredX and desiredY instead of a and b!
        double length = Math.sqrt((desiredX * desiredX) + (desiredY * desiredY));   

        // Normalize the vector - that is, set it's length to 1. You do this by dividing each component of the vector by it's length - be careful to make sure you don't divide by 0!
        if (length > 0) {
            desiredX /= length;
            desiredY /= length;
        }

        // Now set the vectors length to your desired velocity. Because the vector is normalized (a length of 1), you can just multiply by it!
        // Increasing the accelRate should make movements more sharp and dramatic
        desiredX *= accelRate;
        desiredY *= accelRate;

        // Now we set the acceleration to our input
        float ax = desiredX;    // a for acceleration
        float ay = desiredY;
        
        // Now lets do the euler integration!
        vx += ax * dt;
        vy += ay * dt;
        
        // Now lets limit our velocity vector to our moveSpeed, using the same method we use to normalize our input
        double speed = Math.sqrt((vx * vx) + (vy * vy));
        if (speed > moveSpeed) {
            vx /= speed;    // If moveSpeed is always > 0, we don't need to worry about diving by 0!
            vy /= speed;

            vx *= moveSpeed;
            vy *= moveSpeed;
        }

        x += vx;
        y += vy;

        // And zero out the inputs
        targetX = 0;
        targetY = 0;
        vx *= 0.95;    // Fake friction. Perhaps we should move this into it's own variable?
        vy *= 0.95;
        // go from one side of the map to another
        if (x > Window.getWidth()) {
            x = 0;
        } else if (x < 0) {
            x = Window.getWidth();
        }
        if (y > Window.getHeight()) {
            y = 0;
        } else if (y < 0) {
            y = Window.getHeight();
        }
        
        playerSprite.setPosition(x, y);
        
        // set player angle based on mouse position
        angle = Math.atan2( Input.getMousePos().y - playerSprite.getPosition().y, 
                            Input.getMousePos().x - playerSprite.getPosition().x);
        
        angle *= (180/Math.PI);
        if(angle < 0) {
            angle = 360 + angle;
        }
        
        playerSprite.setRotation(90 + (float)angle);
    }
    
    @Override
    public boolean isRemovable() {
        return removable;
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
    
    public static void setTargetX(float x) {
        targetX = x;
    }
    
    public static void setTargetY(float y) {
        targetY = y;
    }
}
