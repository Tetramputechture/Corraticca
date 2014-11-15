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
    
    private Vector2f pos;
    private final Vector2f v;
    private final int speed;
    private static final float force = 1000;
    private final Sprite bulletSprite;
    private boolean hitEnemy;

    /**
     * Sets rotation and position of bullet.
     * @param s the sprite of the bullet.
     */
    public BulletEntity(Sprite s) {
        // init sprite
        super(s);
        bulletSprite = s;
        
        speed = 300;
        
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
     * Updates the bullet's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {
        pos = Vector2f.add(pos, new Vector2f(v.x * dt, v.y * dt));
        bulletSprite.setPosition(pos);
    }
    
    /**
     * If bullet should be removed
     * @return if the bullet is out of bounds, and therefore should be removed
     */
    @Override
    public boolean toBeRemoved() {
        return isOutOfBounds() || hitEnemy;
    }
    
    public boolean isOutOfBounds() {
        return pos.x > Window.getWidth() || pos.x < 0 || 
                pos.y > Window.getHeight() || pos.y < 0;
    }
    
    public void enemyHit(boolean b) {
        this.hitEnemy = b;
    }
    
    @Override
    public Vector2f getPos() {
        return bulletSprite.getPosition();
    }
    
    @Override
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    
    public static float getForce() {
        return force;
    }
    
    public Vector2f getVelocity() {
        return v;
    }
    
    @Override
    public String toString() {
        return "Bullet";
    }
    
}
