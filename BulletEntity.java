/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Sprite;

/**
 * An entity representing a bullet, what the player shoots.
 * @author Nick
 */
public class BulletEntity extends Entity {
    
    private float x;
    private float y;
    private final int vx;
    private final int vy;
    private final Sprite bulletSprite;
    private final boolean removable;

    /**
     * Sets rotation and position of bullet.
     * @param s the sprite of the bullet.
     */
    public BulletEntity(Sprite s) {
        // init sprite
        super(s);
        bulletSprite = s;
        
        // must be deleted when out of bounds
        removable = true;
        
        // set position and angle based off current player sprite
        x = (float) (PlayerEntity.getPos().x + Math.cos(Math.toRadians(PlayerEntity.getAngle())));
        y = (float) (PlayerEntity.getPos().y + Math.sin(Math.toRadians(PlayerEntity.getAngle())));
        vx = (int) (220 * Math.sin(Math.toRadians(PlayerEntity.getAngle())));
        vy = (int) (220 * Math.cos(Math.toRadians(PlayerEntity.getAngle())));
        bulletSprite.setRotation(PlayerEntity.getAngle());
        bulletSprite.setPosition(x, y);
    }

    /**
     * Updates the bullet's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {
       
        x += vx * dt;
        y -= vy * dt;
        
        bulletSprite.setPosition(x, y);
    }
    
    /**
     * If bullet is out of bounds.
     * @return if the bullet is out of bounds .
     */
    @Override
    public boolean isOutOfBounds() {
        return (x > Window.getWidth() || x < 0 || y > Window.getHeight() || y < 0);
    }
    
    /**
     * If bullet is removable.
     * @return if bullet is removable.
     */
    @Override
    public boolean isRemovable() {
        return removable;
    }
    
}
