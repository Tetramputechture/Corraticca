/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Utils.CPhysics;
import coratticca.Utils.Screen.GameScreen;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * An entity representing a bullet, what the player shoots.
 * @author Nick
 */
public final class BulletEntity extends Entity {
    
    private final Sprite bulletSprite;
    
    private Vector2f pos;
    private Vector2f v;
    private static final int speed = 500;
    
    private static final float playerVelocityScalar = 40f;
    
    private Entity nearestEntity;
    
    private boolean hitEntity;

    /**
     * Sets rotation and position of bullet.
     * @param s the sprite of the bullet.
     */
    public BulletEntity(Sprite s) {
        // init sprite
        super(s);
        bulletSprite = s;
        
        PlayerEntity currentPlayer = GameScreen.getCurrentPlayer();
        float angle = (float)(Math.toRadians(currentPlayer.getRotation()));
        float sin = (float)Math.sin(angle);
        float cos = (float)Math.cos(angle);
        
        // set position and angle based off current player sprite
        pos = Vector2f.add(currentPlayer.getPos(), new Vector2f(sin, cos));
        bulletSprite.setPosition(pos);
        
        Vector2f forward = new Vector2f(sin, -cos);
        forward = Vector2f.mul(forward, speed);
        Vector2f inheritedVelocity = Vector2f.mul(GameScreen.getCurrentPlayer().getVelocity(), playerVelocityScalar);
        v = Vector2f.add(forward, inheritedVelocity);
        
        bulletSprite.setRotation(GameScreen.getCurrentPlayer().getRotation());
    }
    
    /**
     * Updates the bullet's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {   
        pos = Vector2f.add(pos, Vector2f.mul(v, dt));
        bulletSprite.setPosition(pos);
    }
    
    @Override
    public void detectCollisions(float dt) {
        nearestEntity = GameScreen.getGrid().getNearest(this);
        if (!(nearestEntity instanceof PlayerEntity) && nearestEntity != null && (CPhysics.boxCollisionTest(nearestEntity, this))) {
            hitEntity = true;
        }
    }
    
    /**
     * Gets the velocity of the bullet
     * @return the bullet's velocity
     */
    @Override
    public Vector2f getVelocity() {
        return v;
    }
    
    /**
     * Checks if the bullet is out of the window's bounds.
     * @return if the bullet is out of the window's bounds.
     */
    public boolean isOutOfBounds() {
        return pos.x > GameScreen.getBounds().x || pos.x < -GameScreen.getBounds().x || 
               pos.y > GameScreen.getBounds().y || pos.y < -GameScreen.getBounds().y;
    }
    
    /**
     * If bullet should be removed.
     * @return if the bullet is out of bounds or hit an enemy
     */
    @Override
    public boolean toBeRemoved() {
        return isOutOfBounds() || hitEntity;
    }
    
    /**
     *
     */
    @Override
    public void handleRemoval() {
    }
    
    @Override
    public Vector2f getPos() {
        return bulletSprite.getPosition();
    }
    
    @Override
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    /**
     *
     * @return
     */
    @Override
    public float getSize() {
        return bulletSprite.getScale().x;
    }

    /**
     *
     * @param v
     */
    @Override
    public void setVelocity(Vector2f v) {
        this.v = v;
    }
    
    /**
     *
     * @return
     */
    @Override
    public float getRotation() {
        return bulletSprite.getRotation();
    }
    
    @Override
    public String toString() {
        return "Bullet";
    }
}
