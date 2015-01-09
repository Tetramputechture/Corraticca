/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Actions.SpawnAsteroidAction;
import coratticca.Utils.CPhysics;
import coratticca.Utils.CRandom;
import coratticca.Utils.CVector;
import coratticca.Utils.Screen.GameScreen;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * Asteroid entity. 
 * - Moves around at a set velocity.
 * - Breaks up into smaller asteroids when hit by bullets.
 * - Damages the player.
 * @author Nick
 */
public final class AsteroidEntity extends Entity {
    
    private final Sprite asteroidSprite;
    
    private Vector2f pos;
    private Vector2f v;
    private final float speed;
    private final float maxSpeed = 50;
    
    private int size;
    private static final float sizeScalarCoefficient = 1;
    
    // to make collisions feel more natural, add a little to the bounds
    private int boundOffset = 10;
    
    private int health;
    
    private boolean hitPlayer;
   
    private Vector2f collidingBulletVelocity;
    
    private AsteroidEntity collidingAsteroid;
    
    private Entity nearestEntity;
    
    /**
     * Sets the sprite, position, and size of the asteroid.
     * @param s the sprite of the asteroid.
     * @param pos the position of the asteroid. 
     * @param size the size of the asteroid. 
     */
    public AsteroidEntity(Sprite s, Vector2f pos, int size) {
        super(s);
        asteroidSprite = s;
        
        asteroidSprite.setPosition(pos);
        this.pos = pos;
        
        // set random angle
        asteroidSprite.setRotation(CRandom.randInt(0, 360));
        
        asteroidSprite.setScale(size, size);
        this.size = size;
        
        // set speed based on size
        speed = sizeScalarCoefficient * 1/size;
        
        // set velocity in random direction
        setRandVelocity();
        
        // set health directly proprotional to size
        health = size;
           
        // no bullet until one has hit
        collidingBulletVelocity = Vector2f.ZERO;
                
    }

    @Override
    public void update(float dt) {
        
        v = Vector2f.add(v, collidingBulletVelocity);
        collidingBulletVelocity = Vector2f.ZERO;
        
        // limit asteroid velocity to max speed
        float s = CVector.length(v);
        if (s > maxSpeed) {
            v = Vector2f.div(v, s);
            v = Vector2f.mul(v, maxSpeed);
        }
        
        pos = Vector2f.add(pos, v);
        
        asteroidSprite.setPosition(pos);
    }
    
    @Override
    public void detectCollisions(float dt) {
        
        // get nearest entity
        nearestEntity = GameScreen.getGrid().getNearest(this);
        
        if (nearestEntity instanceof PlayerEntity) {
            if (intersectsWithPlayer()) {
                handlePlayerIntersection();
            }
        } else if (nearestEntity instanceof BulletEntity) {
            if (intersectsWithBullet()) {
                handleBulletIntersection();
                collidingBulletVelocity = Vector2f.mul(collidingBulletVelocity, dt);
            }
        } else if (nearestEntity instanceof AsteroidEntity) {
            if (intersectsWithAsteroid()) {
                handleAsteroidIntersection();
            }
        } else if (isOutOfBounds()) {
            handleOutOfBounds();
        }
        
    }
    
    /**
     * Checks if the asteroid is out of bounds of the map. 
     * @return
     */
    public boolean isOutOfBounds() {
        float gx = GameScreen.getBounds().x;
        float gy = GameScreen.getBounds().y;
        return (pos.x > gx - boundOffset || pos.x < -gx + boundOffset ||
                pos.y > gy - boundOffset || pos.y < -gy + boundOffset);
    }
    
    /**
     * Handles out of bounds collision. 
     */
    public void handleOutOfBounds() {
        float gx = GameScreen.getBounds().x;
        float gy = GameScreen.getBounds().y;
        if (pos.x > gx - boundOffset || pos.x < -gx + boundOffset) {
            v = new Vector2f(-v.x, v.y);
        }
        if (pos.y > gy - boundOffset || pos.y < -gy + boundOffset) {
            v = new Vector2f(v.x, -v.y);
        }
    }
    
    /**
     * If the asteroid intersects with the player.
     * @return true if asteroid intersects with player, false otherwise
     */
    public boolean intersectsWithPlayer() {
        return (CPhysics.boxCollisionTest(this, GameScreen.getCurrentPlayer()));
    }
    
    /**
     * Handles intersection with player.
     */
    public void handlePlayerIntersection() {
        hitPlayer = true;
        health = 0;
        GameScreen.getCurrentPlayer().changeHealth(-1);
    }
    
    /**
     * If the enemy intersects with a bullet.
     * @return true if enemy intersects with player, false otherwise.
     */
    public boolean intersectsWithBullet() {    
        if (CPhysics.boxCollisionTest(this, nearestEntity)) { 
            collidingBulletVelocity = Vector2f.div(nearestEntity.getVelocity(), size*3);
            collidingBulletVelocity = Vector2f.add(collidingBulletVelocity, v);
            return true;
        }
        
        return false;
    }
    
    /**
     * Handles intersection with bullets.
     */
    public void handleBulletIntersection() {
        health--;
    }
    
    /**
     * Checks if this asteroid intersects with another asteroid.
     * @return true, if the asteroid intersects with another asteroid.
     */
    public boolean intersectsWithAsteroid() {
        
        if (CPhysics.boxCollisionTest(this, nearestEntity) &&
                !this.equals(nearestEntity)) {
            collidingAsteroid = (AsteroidEntity)nearestEntity;
            return true;
        }
        
        return false;
    }
    
    /**
     * Handles elastic collision between two asteroids.
     */
    public void handleAsteroidIntersection() {
        CPhysics.handleCollision(this, collidingAsteroid);
    }

    @Override
    public boolean toBeRemoved() {
        if (health == 0) {
            GameScreen.scorePoint();
            return true;
        }
        return false;
    }
   
    @Override 
    public void handleRemoval() {
        // if size is 1: disappear (particle effect?)
        // if size is 2: explode into 2 size 1 asteroids
        // if size is 3: explode into 3 size 2 asteroids
        // if size is 4: explode into 4 size 3 asteroids
        if (size == 1) {
            // display death particle
        } else {
            for (int i = 0; i < size; i++) {
                new SpawnAsteroidAction(new Vector2f(pos.x+20+i*5, pos.y+20+i*5), size - 1).execute();
            }  
        } 
    }

    @Override
    public Vector2f getPos() {
        return pos;
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
    public Vector2f getVelocity() {
        return v;
    }
    
    /**
     *
     * @param v
     */
    @Override
    public void setVelocity(Vector2f v) {
        this.v = v;
    }
    
    private void setRandVelocity() {
        v = Vector2f.mul(CRandom.randVector(-2, 2, -2, 2), speed);
    }
    
    /**
     *
     * @return
     */
    @Override
    public float getSize() {
        return size;
    }
    
    /**
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     *
     * @return
     */
    @Override
    public float getRotation() {
        return asteroidSprite.getRotation();
    }
    
    @Override
    public String toString() {
        return "Asteroid";
    }
}
