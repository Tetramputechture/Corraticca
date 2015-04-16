/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Utils.CPrecache;
import coratticca.Utils.CSprite;
import coratticca.Utils.Screen.GameScreen;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * An entity representing a bullet, what the player shoots.
 * @author Nick
 */
public final class BulletEntity extends Entity {
    
    private Entity nearestEntity;
    
    private boolean hitEntity;

    public BulletEntity(GameScreen g, Vector2f pos) {
        super(g, pos);
    }
    
    @Override
    public Sprite initSprite() {
        Texture t = CPrecache.getBulletTexture();
        Sprite s = new Sprite(t);
        CSprite.setOriginAtCenter(s, t);
        
        return s;
    }
    
    /**
     * Updates the bullet's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {   
        pos = Vector2f.add(pos, Vector2f.mul(super.velocity, dt));
        super.sprite.setPosition(pos);
    }
    
    @Override
    public void detectCollisions(float dt) {
        nearestEntity = game.getGrid().getNearest(this);
        if (!(nearestEntity instanceof PlayerEntity) && nearestEntity != null && (game.getPhysicsHandler().boxCollisionTest(nearestEntity, this))) {
            hitEntity = true;
        }
    }
    
    /**
     * If bullet should be removed.
     * @return if the bullet is out of bounds or hit an enemy
     */
    @Override
    public boolean toBeRemoved() {
        return isOutOfBounds() || hitEntity;
    }
    
    @Override
    public String toString() {
        return "Bullet";
    }
}
