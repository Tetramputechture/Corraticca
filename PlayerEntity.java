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
    
    private double angle;
    
    private Vector2f pos;
    private Vector2f v;
    private Vector2f target;
    private final float moveSpeed;
    private final float accelRate;
    private final float fConst;
    
    private int health;
    
    static {
        playerTexture = new Texture();
        String playerTextureFile = "player.png";
       
        try {
            playerTexture.loadFromFile(Paths.get(playerTextureFile));
        } catch (IOException ex) {
            Logger.getLogger(PlayerEntity.class.getName()).log(Level.SEVERE, 
                    String.format("Unable to load file %s!%n", playerTextureFile), 
                    ex);
        }
        
        playerTexture.setSmooth(true);
        
        playerSprite = new Sprite(playerTexture);
    }
    
    /**
     * Sets the player entity's sprite.
     */
    public PlayerEntity() {
        super(playerSprite);
        health = 3;
        
        playerSprite.setOrigin(Vector2f.div(new Vector2f(playerTexture.getSize()), 2));
        
        playerSprite.setPosition(Window.getWidth()/2.0f, Window.getHeight()/2.0f);
        pos = playerSprite.getPosition();
        v = Vector2f.ZERO;
        
        // set movement variables
        moveSpeed = 120;
        accelRate = 20;
        fConst = 0.95f;
    }
    
    /**
     * Updates the player entity based on frametime.
     * @param dt the difference in time since last frame.
     */
    @Override
    public void update(float dt) {
        
        // handle key presses
        int ttx = (Keyboard.isKeyPressed(Keyboard.Key.A) ? -1 : 0) + (Keyboard.isKeyPressed(Keyboard.Key.D) ? 1 : 0);
	int tty = (Keyboard.isKeyPressed(Keyboard.Key.W) ? -1 : 0) + (Keyboard.isKeyPressed(Keyboard.Key.S) ? 1 : 0);
        target = new Vector2f(ttx, tty);

        // find true velocity by getting the magnitude of the vector.
        double length = Math.sqrt((target.x * target.x) + (target.y * target.y));   

        /* 
         * normalize the vector
        */
        if (length > 0) {
            target = Vector2f.div(target, (float)length);
        }

        // set length to target velocity
        // increasing accelRate should make movements more sharp and dramatic
        target = Vector2f.mul(target, accelRate);

        // set acc variables
        Vector2f acc = new Vector2f(target.x, target.y);
        
        // integrate acceleration to get velocity
        v = Vector2f.add(v, new Vector2f(acc.x * dt, acc.y * dt));

        // limit velocity vector to moveSpeed
        double speed = Math.sqrt((v.x * v.x) + (v.y * v.y));
        if (speed > moveSpeed) {
            v = Vector2f.div(v, (float)speed);
            v = Vector2f.mul(v, moveSpeed);
        }

        // set velocity
        pos = Vector2f.add(pos, v);
        
        // apply friction
        v = Vector2f.mul(v, fConst);
        
        handleWallCollision();
        
        playerSprite.setPosition(pos);
        
        // set player angle based on mouse position
        angle = Math.atan2( Input.getMousePos().y - playerSprite.getPosition().y, 
                            Input.getMousePos().x - playerSprite.getPosition().x);
        
        angle *= (180/Math.PI);
        if(angle < 0) {
            angle = 360 + angle;
        }
        
        playerSprite.setRotation(90 + (float)angle);
    }
    
    public void reset() {
        playerSprite.setPosition(Window.getWidth()/2.0f, Window.getHeight()/2.0f);
        pos = playerSprite.getPosition();
        v = Vector2f.ZERO;
        health = 3;
    }
    
    public void handleWallCollision() {
        int tx = -1;
        int ty = -1;
        
        if (pos.x > Window.getWidth()) {
            tx = Window.getWidth();
        } else if (pos.x < 0) {
            tx = 0;
        }
        if (pos.y > Window.getHeight()) {
            ty = Window.getHeight();
        } else if (pos.y < 0) {
            ty = 0;
        }
        
        if (tx != -1) {
            pos = new Vector2f(tx, pos.y);
        }
        if (ty != -1) {
            pos = new Vector2f(pos.x, ty);
        }
    }
    
    /**
     * If the player should be removed.
     * @return if the enemy has no health, and therefore should be removed.
     */
    @Override
    public boolean toBeRemoved() {
        if (health <= 0) {
            System.out.println("Game lost!");
            Window.changeScreen(new GameLostScreen());
            return true;
        }
        return false;
    }
    
    public void changeHealth(int r) {
        health += r;
        System.out.println("Health: " + health);
    }
    
    /**
     * Sets the angle of the player.
     * @param angle the angle for the player to be set at.
     */
    public void setAngle(float angle) {
        playerSprite.setRotation(angle);
    }

    
    /**
     * Sets the position of the player.
     * @param pos
     */
    @Override
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    
    public static Sprite getCurrentSprite() {
        return playerSprite;
    }
    
    /**
     * Gets the player's position.
     * @return the player's position.
     */
    @Override
    public Vector2f getPos() {
        return playerSprite.getPosition();
    }

    /**
     * Gets the player's size.
     * @return the player's size.
     */
    public Vector2f getSize() {
        return new Vector2f(playerSprite.getLocalBounds().width, playerSprite.getLocalBounds().height);
    }
    
    /**
     * Gets the player's angle.
     * @return the player's angle.
     */
    public float getAngle() {
        return playerSprite.getRotation();
    }
    
    public int getHealth() {
        return health;
    }
    
    @Override
    public String toString() {
        return "Player";
    }
}
