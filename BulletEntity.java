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
    
    private float x;
    private float y;
    private final float vx;
    private final float vy;
    private final Sprite bulletSprite;

    /**
     * Sets rotation and position of bullet.
     * @param s the sprite of the bullet.
     */
    public BulletEntity(Sprite s) {
        // init sprite
        super(s);
        bulletSprite = s;
        
        // set position and angle based off current player sprite
        x = (float) (GameScreen.getCurrentPlayer().getPos().x + Math.cos(Math.toRadians(GameScreen.getCurrentPlayer().getAngle())));
        y = (float) (GameScreen.getCurrentPlayer().getPos().y + Math.sin(Math.toRadians(GameScreen.getCurrentPlayer().getAngle())));
        vx = (float) (300 * Math.sin(Math.toRadians(GameScreen.getCurrentPlayer().getAngle())));
        vy = (float) (300 * Math.cos(Math.toRadians(GameScreen.getCurrentPlayer().getAngle())));
        bulletSprite.setRotation(GameScreen.getCurrentPlayer().getAngle());
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
     * If bullet should be removed
     * @return if the bullet is out of bounds, and therefore should be removed
     */
    @Override
    public boolean remove() {
        return (x > Window.getWidth() || x < 0 || y > Window.getHeight() || y < 0);
    }
    
    @Override
    public Vector2f getPos() {
        return bulletSprite.getPosition();
    }
    
    @Override
    public String toString() {
        return "Bullet";
    }

}
