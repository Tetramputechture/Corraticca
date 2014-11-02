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
import org.jsfml.window.Keyboard;

/**
 * Entity representing the player.
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
    private static final float fConst;
    private static final boolean removable;
    
    /**
     * Sets the player entity's sprite.
     * @param s the player's sprite
     */
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
        
        // set movement variables
        moveSpeed = 120;
        accelRate = 20;
        fConst = 0.95f;
        
        // player can't be removed
        removable = false;
    }
    
    /**
     * Updates the player entity based on frametime.
     * @param dt the difference in time since last frame.
     */
    @Override
    public void update(float dt) {
        
        targetX = (Keyboard.isKeyPressed(Keyboard.Key.A) ? -1 : 0) + (Keyboard.isKeyPressed(Keyboard.Key.D) ? 1 : 0);
	targetY = (Keyboard.isKeyPressed(Keyboard.Key.W) ? -1 : 0) + (Keyboard.isKeyPressed(Keyboard.Key.S) ? 1 : 0);
        
        float desiredX = targetX;
        float desiredY = targetY;

        // find true velocity by getting the magnitude of the vector.
        double length = Math.sqrt((desiredX * desiredX) + (desiredY * desiredY));   

        /* 
         * normalize the vector
        */
        if (length > 0) {
            desiredX /= length;
            desiredY /= length;
        }

        // set length to desired velocity
        // increasing accelRate should make movements more sharp and dramatic
        desiredX *= accelRate;
        desiredY *= accelRate;

        // set acc variables
        float ax = desiredX;   
        float ay = desiredY;
        
        // integrate acceleration to get velocity
        vx += ax * dt;
        vy += ay * dt;
        
        // limit velocity vector to moveSpeed
        double speed = Math.sqrt((vx * vx) + (vy * vy));
        if (speed > moveSpeed) {
            vx /= speed;    
            vy /= speed;

            vx *= moveSpeed;
            vy *= moveSpeed;
        }

        // set velocity
        x += vx;
        y += vy;
        
        // apply friction
        vx *= fConst;    
        vy *= fConst;
        
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
    
    /**
     * Checks if the player is removable.
     * @return if the player is removable.
     */
    @Override
    public boolean isRemovable() {
        return removable;
    }
    
    /**
     * Checks if the player is out of bounds.
     * @return if the player is out of bounds.
     */
    @Override
    public boolean isOutOfBounds() {
        // will never be out of bounds, since player wraps around screen
        return false;
    }
    
    /**
     * Sets the angle of the player.
     * @param angle the angle for the player to be set at.
     */
    public static void setAngle(float angle) {
        playerSprite.setRotation(angle);
    }
    
    /**
     * Sets the position of the player.
     * @param x the x position for the player to be set at.
     * @param y the y position for the player to be set at.
     */
    public static void setPos(int x, int y) {
        playerSprite.setPosition(x, y);
    }
    
    /**
     * Gets the player's sprite.
     * @return the player's sprite.
     */
    public static Sprite getSprite() {
        return playerSprite;
    }
    
    /**
     * Gets the player's position.
     * @return the player's position.
     */
    public static Vector2f getPos() {
        return playerSprite.getPosition();
    }

    /**
     * Gets the player's size.
     * @return the player's size.
     */
    public static Vector2f getSize() {
        return new Vector2f(playerSprite.getLocalBounds().width, playerSprite.getLocalBounds().height);
    }
    
    /**
     * Gets the player's angle.
     * @return the player's angle.
     */
    public static float getAngle() {
        return playerSprite.getRotation();
    }
}
