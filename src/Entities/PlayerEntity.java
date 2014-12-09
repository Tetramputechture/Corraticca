/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Utils.CSprite;
import coratticca.Utils.CPrecache;
import coratticca.Utils.CVector;
import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.Screen.GameLostScreen;
import coratticca.Utils.Window;
import coratticca.Utils.Input;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;

/**
 * Entity representing the player.
 * @author Nick
 */
public final class PlayerEntity extends Entity {
    
    private static final Sprite playerSprite;
    
    private double angle;
    
    private Vector2f pos;
    private Vector2f v;
    private final float maxMoveSpeed;
    private Vector2f target;
    
    private final float accelRate;
    private final float fConst;
    
    private int health;
    
    static { 
        Texture t = CPrecache.getPlayerTexture();
        playerSprite = new Sprite(t);
        CSprite.setOriginAtCenter(playerSprite, t);
    }
    
    /**
     * Sets the player entity's sprite.
     */
    public PlayerEntity() {
        super(playerSprite);
        health = 3;
        
        v = Vector2f.ZERO;
        
        // set movement variables
        maxMoveSpeed = 120;
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

        // normalize target direction
        target = CVector.normalize(target);

        // set length to target velocity
        // increasing accelRate should make movements more sharp and dramatic
        target = Vector2f.mul(target, accelRate);

        // target is now acceleration
        Vector2f acc = target;
        
        // integrate acceleration to get velocity
        v = Vector2f.add(v, Vector2f.mul(acc, dt));

        // limit velocity vector to maxMoveSpeed
        float speed = CVector.length(v);
        if (speed > maxMoveSpeed) {
            v = Vector2f.div(v, speed);
            v = Vector2f.mul(v, maxMoveSpeed);
        }

        // set velocity
        pos = Vector2f.add(pos, v);
        
        // apply friction
        v = Vector2f.mul(v, fConst);
        
        handleWallCollision();
        
        playerSprite.setPosition(pos);
        
        // set player angle based on mouse position
        Vector2i truePos = Window.getWindow().mapCoordsToPixel(pos);
        angle = Math.atan2( Input.getMousePos().y - truePos.y, 
                            Input.getMousePos().x - truePos.x);
        
        angle *= (180/Math.PI);
        if(angle < 0) {
            angle += 360;
        }
        
        playerSprite.setRotation(90 + (float)angle);
    }
    
    @Override
    public void detectCollisions(float dt) {
        handleWallCollision();
    }
    
    /**
     * Resets the player's position, velocity, and health.
     */
    public void reset() {
        v = Vector2f.ZERO;
        health = 3;
    }
    
    /**
     * Handles if the player collided with a wall.
     */
    public void handleWallCollision() {
        float tx = -1;
        float ty = -1;
        
        float gWidth = GameScreen.getBounds().x;
        float gHeight = GameScreen.getBounds().y;
        
        float pAdjustedWidth = playerSprite.getLocalBounds().width * .9f;
        float pAdjustedHeight = playerSprite.getLocalBounds().height * .9f;
        
        if (pos.x > gWidth - pAdjustedWidth) {
            tx = gWidth - pAdjustedWidth;
        } else if (pos.x < -gWidth + pAdjustedWidth) {
            tx = -gWidth + pAdjustedWidth;
        }
        if (pos.y > gHeight - pAdjustedHeight) {
            ty = gHeight - pAdjustedHeight;
        } else if (pos.y < -gHeight + pAdjustedHeight) {
            ty = -gHeight + pAdjustedHeight;
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
        return health <= 0;
    }
    
    @Override
    public void handleRemoval() {
        System.out.println("Game lost!");
        Window.changeScreen(new GameLostScreen());
    }
    
    /**
     * Changes the player's health.
     * @param r the health amount to be changed by.
     */
    public void changeHealth(int r) {
        health += r;
    }
     
    @Override
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    
    @Override
    public Vector2f getPos() {
        return pos;
    }
    
    /**
     * Gets the player's angle.
     * @return the player's angle.
     */
    @Override
    public float getRotation() {
        return playerSprite.getRotation();
    }

    @Override
    public Vector2f getVelocity() {
        return v;
    }
    
    /**
     * Gets the player's current health.
     * @return the health of the player.
     */
    public int getHealth() {
        return health;
    }

    @Override
    public float getSize() {
        return playerSprite.getScale().x;
    }

    @Override
    public void setVelocity(Vector2f v) {
        this.v = v;
    }
    
    @Override
    public String toString() {
        return "Player";
    }
}