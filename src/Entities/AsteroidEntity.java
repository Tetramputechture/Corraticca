/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Utils.CPhysics;
import coratticca.Utils.CPrecache;
import coratticca.Utils.CRandom;
import coratticca.Utils.CSprite;
import coratticca.Utils.CVector;
import coratticca.Utils.Grid;
import coratticca.Utils.Screen.GameScreen;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Asteroid entity. - Moves around at a set velocity. - Breaks up into smaller
 * asteroids when hit by bullets. - Damages the player.
 *
 * @author Nick
 */
public final class AsteroidEntity extends Entity {

    private final float speed;
    private final float maxSpeed = 50;

    private int size;
    private static final float sizeScalarCoefficient = 1;

    // to make collisions feel more natural, add a little to the bounds
    private final int boundOffset = 7;

    private int health;

    private Vector2f collidingBulletVelocity;
    private Entity nearestEntity;

    /**
     * Sets the sprite, position, and size of the asteroid.
     *
     * @param pos the position of the asteroid.
     * @param size the size of the asteroid.
     */
    public AsteroidEntity(Vector2f pos, int size) {
        super(pos);
        initSprite();

        // for some random calculations
        CRandom rand = new CRandom();

        // set random angle
        sprite.setRotation(rand.randInt(0, 360));

        sprite.setScale(size, size);
        this.size = size;

        // set speed based on size
        speed = sizeScalarCoefficient * 1 / size;

        // set velocity in random direction
        velocity = Vector2f.mul(rand.randVector(-2, 2, -2, 2), speed);

        // set health directly proprotional to size
        health = size;

        // no bullet until one has hit
        collidingBulletVelocity = Vector2f.ZERO;
    }

    @Override
    public void initSprite() {
        Texture t;
        if (Math.random() < 0.5) {
            t = CPrecache.getAsteroidTextureA();
        } else {
            t = CPrecache.getAsteroidTextureB();
        }
        Sprite s = new Sprite(t);
        CSprite.setOriginAtCenter(s, t);

        sprite = s;
        sprite.setPosition(pos);
    }

    @Override
    public void update(float dt) {

        velocity = Vector2f.add(velocity, collidingBulletVelocity);
        collidingBulletVelocity = Vector2f.ZERO;

        // limit asteroid velocity to max speed
        float s = CVector.length(velocity);
        if (s > maxSpeed) {
            velocity = Vector2f.div(velocity, s);
            velocity = Vector2f.mul(velocity, maxSpeed);
        }

        pos = Vector2f.add(pos, velocity);

        sprite.setPosition(pos);
    }

    @Override
    public void detectAndHandleCollisions(Grid grid, CPhysics physics, float dt) {

        nearestEntity = grid.getNearest(this);
        
//        if (nearestEntity == null) {
//            return;
//        }

        if (physics.boxCollisionTest(this, nearestEntity)) {

            if (nearestEntity instanceof PlayerEntity) {
                handlePlayerIntersection((PlayerEntity) nearestEntity);

            } else if (nearestEntity instanceof BulletEntity) {
                handleBulletIntersection((BulletEntity) nearestEntity);
                collidingBulletVelocity = Vector2f.mul(collidingBulletVelocity, dt);

            } else if (nearestEntity instanceof AsteroidEntity) {
                handleAsteroidIntersection((AsteroidEntity) nearestEntity, physics);

            }
        } else if (isOutOfBounds(grid)) {
            handleOutOfBounds(grid);
        }
    }

    @Override
    public boolean isOutOfBounds(Grid grid) {
        float gx = grid.getSize().x;
        float gy = grid.getSize().y;
        return (pos.x > gx - boundOffset || pos.x < -gx + boundOffset
                || pos.y > gy - boundOffset || pos.y < -gy + boundOffset);
    }

    @Override
    public void handleOutOfBounds(Grid grid) {
        float gx = grid.getSize().x;
        float gy = grid.getSize().y;
        if (pos.x > gx - boundOffset || pos.x < -gx + boundOffset) {
            velocity = new Vector2f(-velocity.x, velocity.y);
        }
        if (pos.y > gy - boundOffset || pos.y < -gy + boundOffset) {
            velocity = new Vector2f(velocity.x, -velocity.y);
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
     */
    public void handleBulletIntersection(BulletEntity b) {
        collidingBulletVelocity = Vector2f.div(b.getVelocity(), size * 3);
        collidingBulletVelocity = Vector2f.add(collidingBulletVelocity, velocity);
        health--;
    }

    /**
     * Handles intersection with asteroids.
     *
     * @param a the asteroid that is handled.
     * @param p the physics object to handle the collision.
     */
    public void handleAsteroidIntersection(AsteroidEntity a, CPhysics p) {
        p.handleCollision(this, a);
    }

    @Override
    public boolean toBeRemoved(Grid grid) {
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
                g.addEntity(new AsteroidEntity(new Vector2f(pos.x + 20 + i * 5, pos.y + 20 + i * 5), size));
            }
        }
    }

    @Override
    public float getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Asteroid";
    }
}
