package coratticca.entity.gameentity;

import coratticca.entitygrid.EntityGrid;
import coratticca.physics.CollisionHandler;
import coratticca.screen.GameScreen;
import coratticca.util.PrecacheUtils;
import coratticca.util.RandomUtils;
import coratticca.util.SpriteUtils;
import coratticca.vector.Vector2;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

/**
 * Asteroid entity. - Moves around at a set velocity. - Breaks up into smaller
 * asteroids when hit by bullets. - Damages the player.
 *
 * @author Nick
 */
public final class AsteroidEntity extends GameEntity {

    private final float speed;
    private final float maxSpeed = 50;

    private static final float sizeScalarCoefficient = 1;

    // to make collisions feel more natural, add a little to the bounds
    private final int boundOffset;

    private final int size;
    private int health;
    
    private GameEntity nearestEntity;

    /**
     * Sets the sprite, position, and size of the asteroid.
     *
     * @param pos the position of the asteroid.
     * @param size the size of the asteroid.
     */
    public AsteroidEntity(Vector2 pos, int size) {
        super(pos);
        initSprite();

        // set random angle
        sprite.setRotation(RandomUtils.randInt(0, 360));

        sprite.setScale(size, size);
        boundOffset = (int) getSize().x / 2;
        this.size = size;
        this.mass = size * 10000;

        // set speed based on size
        speed = sizeScalarCoefficient * 1 / size;

        // set velocity in random direction
        velocity = RandomUtils.randVector(-2, 2, -2, 2).scl(speed);

        // set health directly proprotional to size
        health = size;
    }

    @Override
    public void initSprite() {
        Texture t;
        if (Math.random() < 0.5) {
            t = PrecacheUtils.getAsteroidTextureA();
        } else {
            t = PrecacheUtils.getAsteroidTextureB();
        }
        Sprite s = new Sprite(t);
        SpriteUtils.setOriginAtCenter(s, t);

        sprite = s;
        sprite.setPosition(position.toVector2f());
    }

    @Override
    public void update(float dt) {
        // limit asteroid velocity to max speed
        velocity = velocity.clamp(0, maxSpeed);

        position = position.add(velocity);

        sprite.setPosition(position.toVector2f());
    }

    @Override
    public void detectAndHandleCollisions(EntityGrid grid, CollisionHandler physics, float dt) {

        nearestEntity = grid.getNearest(this);

        if (physics.boxCollisionTest(this, nearestEntity)) {

            if (nearestEntity instanceof PlayerEntity) {
                handlePlayerIntersection((PlayerEntity) nearestEntity);
            } else if (nearestEntity instanceof BulletEntity) {
                handleBulletIntersection((BulletEntity) nearestEntity, physics);
            } else if (nearestEntity instanceof AsteroidEntity) {
                handleAsteroidIntersection((AsteroidEntity) nearestEntity, physics);
            }
        }

        if (isOutOfBounds(grid)) {
            handleOutOfBounds(grid);
        }
    }

    @Override
    public boolean isOutOfBounds(EntityGrid grid) {
        float gx = grid.getSize().x;
        float gy = grid.getSize().y;

        return (position.x > gx - boundOffset || position.x < -gx + boundOffset
                || position.y > gy - boundOffset || position.y < -gy + boundOffset);
    }

    @Override
    public void handleOutOfBounds(EntityGrid grid) {
        float gx = grid.getSize().x;
        float gy = grid.getSize().y;

        boolean leftOOB = position.x < -gx + boundOffset;
        boolean rightOOB = position.x > gx - boundOffset;
        boolean upOOB = position.y < -gy + boundOffset;
        boolean downOOB = position.y > gy - boundOffset;

        if (leftOOB || rightOOB) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }
        if (upOOB || downOOB) {
            velocity = new Vector2(velocity.x, -velocity.y);
        }
    }

    /**
     * Handles intersection with player.
     *
     * @param p the player that is handled.
     */
    public void handlePlayerIntersection(PlayerEntity p) {
        health = 0;
        p.changeHealth(-1);
    }

    /**
     * Handles intersection with bullets.
     *
     * @param b the bullet that is handled.
     * @param p the physics object to handle the collision.
     */
    public void handleBulletIntersection(BulletEntity b, CollisionHandler p) {
        health--;
    }

    /**
     * Handles intersection with asteroids.
     *
     * @param a the asteroid that is handled.
     * @param p the physics object to handle the collision.
     */
    public void handleAsteroidIntersection(AsteroidEntity a, CollisionHandler p) {
        p.handleCollision(this, a);
    }

    @Override
    public boolean toBeRemoved(EntityGrid grid) {
        return health == 0;
    }

    @Override
    public void handleRemoval(GameScreen g) {
        // if size is 1: disappear (particle effect?)
        // if size is 2: explode into 2 size 1 asteroids
        // if size is 3: explode into 3 size 2 asteroids
        // if size is 4: explode into 4 size 3 asteroids
        if (size == 1) {
            // display death particle
        } else {
            for (int i = 0; i < size; i++) {
                g.addEntity(new AsteroidEntity(new Vector2(position.x + 20 + i * 5, position.y + 20 + i * 5), size - 1));
            }
        }
    }

    @Override
    public String toString() {
        return "Asteroid";
    }
}
