/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.entity;

import coratticca.util.CPhysics;
import coratticca.util.CSprite;
import coratticca.util.CPrecache;
import coratticca.util.CVector;
import coratticca.util.Grid;
import coratticca.util.screen.GameScreen;
import coratticca.util.screen.GameLostScreen;
import coratticca.util.Window;
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
    
    private final Window window;
    
    public PlayerEntity(Window window, Vector2f pos) {
        super(pos);
        initSprite();
                
        this.window = window;
        
        velocity = Vector2f.ZERO;
               
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
        velocity = Vector2f.add(velocity, Vector2f.mul(acc, dt));

        // limit velocity vector to maxMoveSpeed
        float speed = CVector.length(velocity);
        if (speed > maxMoveSpeed) {
            velocity = Vector2f.div(velocity, speed);
            velocity = Vector2f.mul(velocity, maxMoveSpeed);
        }

        // set velocity
        pos = Vector2f.add(pos, velocity);
        
        // apply friction
        velocity = Vector2f.mul(velocity, fConst);
        
        sprite.setPosition(pos);
        
        // set player angle based on mouse position
        Vector2f mousePos = window.getInputHandler().getMousePos();
        Vector2i truePos = window.getRenderWindow().mapCoordsToPixel(pos);
        angle = Math.atan2( mousePos.y - truePos.y, 
                            mousePos.x - truePos.x);
        
        angle *= (180/Math.PI);
        if(angle < 0) {
            angle += 360;
        }
        
        sprite.setRotation(90 + (float)angle);
    }
    
    @Override
    public void initSprite() {
        Texture t = CPrecache.getPlayerTexture();
        Sprite s = new Sprite(t);
        CSprite.setOriginAtCenter(s, t);
        
        sprite = s;
        sprite.setPosition(pos);
    }
    
    @Override
    public void detectAndHandleCollisions(Grid grid, CPhysics physics, float dt) {
        if (isOutOfBounds(grid)) {
            handleOutOfBounds(grid);
        }
    }
    
    @Override
    public boolean isOutOfBounds(Grid grid) {
        float gWidth = grid.getSize().x;
        float gHeight = grid.getSize().y;
        
        float pAdjustedWidth = sprite.getLocalBounds().width * .9f;
        float pAdjustedHeight = sprite.getLocalBounds().height * .9f;
        
        return (pos.x > gWidth - pAdjustedWidth ||
                pos.x < -gWidth + pAdjustedWidth ||
                pos.y > gHeight - pAdjustedHeight ||
                pos.y < -gHeight + pAdjustedHeight);
    }
    
    @Override
    public void handleOutOfBounds(Grid grid) {
        float tx = -1;
        float ty = -1;
        
        float gWidth = grid.getSize().x;
        float gHeight = grid.getSize().y;
        
        float pAdjustedWidth = sprite.getLocalBounds().width * .9f;
        float pAdjustedHeight = sprite.getLocalBounds().height * .9f;
        
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
    
    @Override
    public boolean toBeRemoved(Grid grid) {
        return currentHealth <= 0;
    }
    
    @Override
    public void handleRemoval(GameScreen g) {
        window.changeCurrentScreen(new GameLostScreen(g));
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