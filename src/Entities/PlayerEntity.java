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
    
    private double angle;
    
    private final float maxMoveSpeed;
    private Vector2f target;
    
    private final float accelRate;
    private final float fConst;
    
    private final int maxHealth;
    private int currentHealth;
    
    public PlayerEntity(GameScreen g, Vector2f pos) {
        super(g, pos);
        
        super.velocity = Vector2f.ZERO;
        
        // set movement variables
        maxMoveSpeed = 120;
        accelRate = 20;
        fConst = 0.95f;
        maxHealth = 3;
        currentHealth = maxHealth;
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
        super.velocity = Vector2f.add(super.velocity, Vector2f.mul(acc, dt));

        // limit velocity vector to maxMoveSpeed
        float speed = CVector.length(super.velocity);
        if (speed > maxMoveSpeed) {
            super.velocity = Vector2f.div(super.velocity, speed);
            super.velocity = Vector2f.mul(super.velocity, maxMoveSpeed);
        }

        // set velocity
        pos = Vector2f.add(pos, super.velocity);
        
        // apply friction
        super.velocity = Vector2f.mul(super.velocity, fConst);
        
        super.sprite.setPosition(pos);
        
        // set player angle based on mouse position
        Vector2f mousePos = game.getWindow().getInputHandler().getMousePos();
        Vector2i truePos = game.getWindow().getRenderWindow().mapCoordsToPixel(pos);
        angle = Math.atan2( mousePos.y - truePos.y, 
                            mousePos.x - truePos.x);
        
        angle *= (180/Math.PI);
        if(angle < 0) {
            angle += 360;
        }
        
        super.sprite.setRotation(90 + (float)angle);
    }
    
    @Override
    public Sprite initSprite() {
        Texture t = CPrecache.getPlayerTexture();
        Sprite s = new Sprite(t);
        CSprite.setOriginAtCenter(s, t);
        return s;
    }
    
    @Override
    public void detectCollisions(float dt) {
        if (isOutOfBounds()) {
            handleOutOfBounds();
        }
    }
    
    /**
     * Resets the player's position, velocity, and health.
     */
    public void reset() {
        super.velocity = Vector2f.ZERO;
        currentHealth = maxHealth;
    }
    
    /**
     * Handles if the player collided with a wall.
     */
    public void handleOutOfBounds() {
        float tx = -1;
        float ty = -1;
        
        float gWidth = game.getBounds().x;
        float gHeight = game.getBounds().y;
        
        float pAdjustedWidth = super.sprite.getLocalBounds().width * .9f;
        float pAdjustedHeight = super.sprite.getLocalBounds().height * .9f;
        
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
        return currentHealth <= 0;
    }
    
    @Override
    public void handleRemoval() {
        getWindow().changeScreen(new GameLostScreen(game));
    }
    
    /**
     * Changes the player's health.
     * @param r the health amount to be changed by.
     */
    public void changeHealth(int r) {
        currentHealth += r;
    }
    
    /**
     * Gets the player's current health.
     * @return the health of the player.
     */
    public int getHealth() {
        return currentHealth;
    }
    
    @Override
    public String toString() {
        return "Player";
    }
}