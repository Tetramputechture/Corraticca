package coratticca.entity.gameentity;

import coratticca.physics.CollisionHandler;
import coratticca.util.PrecacheUtils;
import coratticca.util.SpriteUtils;
import coratticca.entitygrid.EntityGrid;
import coratticca.particlesystem.ParticleSystem;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import coratticca.vector.Vector2;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

/**
 * An entity representing a bullet, what the player shoots.
 *
 * @author Nick
 */
public final class BulletEntity extends GameEntity {

    private GameEntity nearestEntity;

    private boolean hitEntity;
    
    private final ParticleSystem moveParticleSystem;

    public BulletEntity(Vector2 pos) {
        super(pos);
        initSprite();
        moveParticleSystem = new ParticleSystem(position);
        mass = 15;
    }
    
    public ParticleSystem getMoveParticleSystem() {
        return moveParticleSystem;
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
        moveParticleSystem.setOrigin(position);
        moveParticleSystem.addParticle();
        moveParticleSystem.setParticleDecayRate(3);
        moveParticleSystem.setParticleLifespan(200);
        sprite.setPosition(position.toVector2f());
    }
    
    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        rt.draw(sprite);
        rt.draw(moveParticleSystem);
    }

    @Override
    public void detectAndHandleCollisions(EntityGrid grid, CollisionHandler physics, float dt) {
        nearestEntity = grid.getNearest(this);
        if (nearestEntity instanceof AsteroidEntity && physics.boxCollisionTest(this, nearestEntity)) {
            hitEntity = true;
            physics.handleCollision(this, nearestEntity);
        }
    }

    @Override
    public boolean toBeRemoved(EntityGrid grid) {
        return hitEntity || isOutOfBounds(grid);
    }

    @Override
    public String toString() {
        return "Bullet";
    }
}
