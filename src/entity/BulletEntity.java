package coratticca.entity;

import coratticca.util.CPhysics;
import coratticca.util.CPrecache;
import coratticca.util.CSprite;
import coratticca.util.Grid;
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

    public BulletEntity(Vector2f pos) {
        super(pos);       
        initSprite();
    }
    
    @Override
    public void initSprite() {
        Texture t = CPrecache.getBulletTexture();
        Sprite s = new Sprite(t);
        CSprite.setOriginAtCenter(s, t);
        
        sprite = s;
        sprite.setPosition(pos);
    }
    
    /**
     * Updates the bullet's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {   
       pos = Vector2f.add(pos, Vector2f.mul(velocity, dt));
       sprite.setPosition(pos);
    }
    
    @Override
    public void detectAndHandleCollisions(Grid grid, CPhysics physics, float dt) {
        nearestEntity = grid.getNearest(this);
        if (!(nearestEntity instanceof PlayerEntity) && nearestEntity != null && (physics.boxCollisionTest(nearestEntity, this))) {
            hitEntity = true;
        }
    }
    
    @Override
    public boolean toBeRemoved(Grid grid) {
        return isOutOfBounds(grid) || hitEntity;
    }
    
    @Override
    public String toString() {
        return "Bullet";
    }
}
