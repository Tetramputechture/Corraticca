/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Actions.SpawnAsteroidAction;
import coratticca.Utils.CPrecache;
import coratticca.Utils.CRandom;
import coratticca.Utils.CSprite;
import coratticca.Utils.CVector;
import coratticca.Utils.Screen.GameScreen;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Asteroid entity. 
 * - Moves around at a set velocity.
 * - Breaks up into smaller asteroids when hit by bullets.
 * - Damages the player.
 * @author Nick
 */
public final class AsteroidEntity extends Entity {
    
    private final float speed;
    private final float maxSpeed = 50;
    
    private int size;
    private static final float sizeScalarCoefficient = 1;
    
    // to make collisions feel more natural, add a little to the bounds
    private final int boundOffset = 10;
    
    private int health;
   
    private Vector2f collidingBulletVelocity;
    
    private AsteroidEntity collidingAsteroid;
    
    private Entity nearestEntity;
    
    /**
     * Sets the sprite, position, and size of the asteroid.
     * @param g the game for this asteroid to be drawn on.
     * @param pos the position of the asteroid. 
     * @param size the size of the asteroid. 
     */
    public AsteroidEntity(GameScreen g, Vector2f pos, int size) {
        super(g, pos);
        
        // for some random calculations
        CRandom rand = new CRandom();
        
        // set random angle
        super.sprite.setRotation(rand.randInt(0, 360));
        
        super.sprite.setScale(size, size);
        this.size = size;
        
        // set speed based on size
        speed = sizeScalarCoefficient * 1/size;
        
        // set velocity in random direction
        super.velocity = Vector2f.mul(rand.randVector(-2, 2, -2, 2), speed);
        
        // set health directly proprotional to size
        health = size;
           
        // no bullet until one has hit
        collidingBulletVelocity = Vector2f.ZERO;
    }
    
    @Override
    public Sprite initSprite() {
        Texture t;
        if (Math.random() < 0.5) {
            t = CPrecache.getAsteroidTextureA();
        } else {
            t = CPrecache.getAsteroidTextureB();
        }
        Sprite s = new Sprite(t);
        CSprite.setOriginAtCenter(s, t);
        
        return s;
    }

    @Override
    public void update(float dt) {
        
        super.velocity = Vector2f.add(super.velocity, collidingBulletVelocity);
        collidingBulletVelocity = Vector2f.ZERO;
        
        // limit asteroid velocity to max speed
        float s = CVector.length(super.velocity);
        if (s > maxSpeed) {
            super.velocity = Vector2f.div(super.velocity, s);
            super.velocity = Vector2f.mul(super.velocity, maxSpeed);
        }
        
        super.pos = Vector2f.add(pos, super.velocity);
        
        super.sprite.setPosition(pos);
    }
    
    @Override
    public void detectCollisions(float dt) {
        
        // get nearest entity
        nearestEntity = game.getGrid().getNearest(this);
        
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
    @Override
    public boolean isOutOfBounds() {
        float gx = game.getBounds().x;
        float gy = game.getBounds().y;
        return (pos.x > gx - boundOffset || pos.x < -gx + boundOffset ||
                pos.y > gy - boundOffset || pos.y < -gy + boundOffset);
    }
    
    /**
     * Handles out of bounds collision. 
     */
    public void handleOutOfBounds() {
        float gx = game.getBounds().x;
        float gy = game.getBounds().y;
        if (pos.x > gx - boundOffset || pos.x < -gx + boundOffset) {
            super.velocity = new Vector2f(-super.velocity.x, super.velocity.y);
        }
        if (pos.y > gy - boundOffset || pos.y < -gy + boundOffset) {
            super.velocity = new Vector2f(super.velocity.x, -super.velocity.y);
        }
    }
    
    /**
     * If the asteroid intersects with the player.
     * @return true if asteroid intersects with player, false otherwise
     */
    public boolean intersectsWithPlayer() {
        return (game.getPhysicsHandler().boxCollisionTest(this, game.getCurrentPlayer()));
    }
    
    /**
     * Handles intersection with player.
     */
    public void handlePlayerIntersection() {
        health = 0;
        game.getCurrentPlayer().changeHealth(-1);
    }
    
    /**
     * If the enemy intersects with a bullet.
     * @return true if enemy intersects with player, false otherwise.
     */
    public boolean intersectsWithBullet() {    
        if (game.getPhysicsHandler().boxCollisionTest(this, nearestEntity)) { 
            collidingBulletVelocity = Vector2f.div(nearestEntity.getVelocity(), size*3);
            collidingBulletVelocity = Vector2f.add(collidingBulletVelocity, super.velocity);
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
        
        if (game.getPhysicsHandler().boxCollisionTest(this, nearestEntity) &&
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
        game.getPhysicsHandler().handleCollision(this, collidingAsteroid);
    }

    @Override
    public boolean toBeRemoved() {
        if (health == 0) {
            game.scorePoint();
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
                new SpawnAsteroidAction(new Vector2f(pos.x+20+i*5, pos.y+20+i*5), size - 1).execute(game.getWindow());
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

    @Override
    public Vector2f getVelocity() {
        return super.velocity;
    }

    @Override
    public void setVelocity(Vector2f velocity) {
        super.velocity = velocity;
    }
   
    @Override
    public float getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public float getRotation() {
        return super.sprite.getRotation();
    }
    
    @Override
    public String toString() {
        return "Asteroid";
    }
}
