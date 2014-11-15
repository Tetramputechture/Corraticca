/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * An entity representing a bullet, what the player shoots.
 * @author Nick
 */
public class BulletEntity extends Entity {
    
    private final Sprite bulletSprite;
    
    private Vector2f pos;
    private final Vector2f v;
    private static final int speed = 300;
    
    private boolean hitEnemy;

    /**
     * Sets rotation and position of bullet.
     * @param s the sprite of the bullet.
     */
    public BulletEntity(Sprite s) {
        // init sprite
        super(s);
        bulletSprite = s;
        
        // set position and angle based off current player sprite
        float tx = (float) (GameScreen.getCurrentPlayer().getPos().x + Math.cos(Math.toRadians(GameScreen.getCurrentPlayer().getAngle())));
        float ty = (float) (GameScreen.getCurrentPlayer().getPos().y + Math.sin(Math.toRadians(GameScreen.getCurrentPlayer().getAngle())));
        pos = new Vector2f(tx, ty);
        float tvx = (float) (speed * Math.sin(Math.toRadians(GameScreen.getCurrentPlayer().getAngle())));
        float tvy = -1 * (float) (speed * Math.cos(Math.toRadians(GameScreen.getCurrentPlayer().getAngle())));
        v = new Vector2f(tvx, tvy);
        bulletSprite.setRotation(GameScreen.getCurrentPlayer().getAngle());
    }
    
    /**
     * Gets the velocity of the bullet
     * @return the bullet's velocity
     */
    public Vector2f getVelocity() {
        return v;
    }
    
    /**
     * Checks if the bullet is out of the window's bounds.
     * @return if the bullet is out of the window's bounds.
     */
    public boolean isOutOfBounds() {
        return pos.x > Window.getWidth() || pos.x < 0 || 
                pos.y > Window.getHeight() || pos.y < 0;
    }
    
    /**
     * Changes the status of enemyHit.
     * @param b if the bullet hit an enemy or not.
     */
    public void setEnemyHit(boolean b) {
        this.hitEnemy = b;
    }

    /**
     * Updates the bullet's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {
        pos = Vector2f.add(pos, new Vector2f(v.x * dt, v.y * dt));
        bulletSprite.setPosition(pos);
    }
    
    /**
     * If bullet should be removed.
     * @return if the bullet is out of bounds or hit an enemy
     */
    @Override
    public boolean toBeRemoved() {
        return isOutOfBounds() || hitEnemy;
    }
    
    @Override
    public Vector2f getPos() {
        return bulletSprite.getPosition();
    }
    
    @Override
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
}
