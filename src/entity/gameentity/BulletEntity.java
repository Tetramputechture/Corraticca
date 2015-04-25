package coratticca.entity.gameentity;

import coratticca.physics.CollisionHandler;
import coratticca.util.PrecacheUtils;
import coratticca.util.SpriteUtils;
import coratticca.entitygrid.EntityGrid;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import coratticca.vector.Vector2;

/**
 * An entity representing a bullet, what the player shoots.
 *
 * @author Nick
 */
public final class BulletEntity extends GameEntity {

    private GameEntity nearestEntity;

    private boolean hitEntity;

    public BulletEntity(Vector2 pos) {
        super(pos);
        initSprite();
    }

    @Override
    public void initSprite() {
        Texture t = PrecacheUtils.getBulletTexture();
        Sprite s = new Sprite(t);
        SpriteUtils.setOriginAtCenter(s, t);

        sprite = s;
        sprite.setPosition(position.toVector2f());
    }

    /**
     * Updates the bullet's position based on frametime.
     *
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {
        position = position.sclAdd(velocity, dt);
        sprite.setPosition(position.toVector2f());
    }

    @Override
    public void detectAndHandleCollisions(EntityGrid grid, CollisionHandler physics, float dt) {
        nearestEntity = grid.getNearest(this);
        if (!(nearestEntity instanceof PlayerEntity) && physics.boxCollisionTest(nearestEntity, this)) {
            hitEntity = true;
        }
    }

    @Override
    public boolean toBeRemoved(EntityGrid grid) {
        return isOutOfBounds(grid) || hitEntity;
    }

    @Override
    public String toString() {
        return "Bullet";
    }
}
