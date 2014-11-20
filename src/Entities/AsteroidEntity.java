/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Utils.CMath;
import static coratticca.Utils.CMath.randFloat;
import coratticca.Utils.Camera;
import coratticca.Utils.Screen.GameScreen;
import java.util.Random;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public final class AsteroidEntity extends Entity {
    
    private final Sprite asteroidSprite;
    
    private Vector2f pos;
    private Vector2f v;
    private final float speed;
    private final float maxSpeed = 50;
    
    private final float size;
    private static final float sizeScalarCoefficient = 1;
    
    private int health;
    
    private boolean hitPlayer;
   
    private Vector2f collidingBulletVelocity;
    
    private AsteroidEntity collidingAsteroid;
    
    //private AsteroidEntity previousCollidingAsteroid;
    
    private final Random rand = new Random();
    
    public AsteroidEntity(Sprite s) {
        super(s);
        asteroidSprite = s;
        
        // set position at a random point on the bounds of the window
        // MAKE SURE ASTEROID DOES NOT SPAWN WITHIN ANOTHER ASTEROID
        setRandomPos();
        
        // set random angle
        asteroidSprite.setRotation(CMath.randInt(0, 360));
        
        size = CMath.randInt(1, 4);
        asteroidSprite.setScale(size, size);
        
        // set speed based on size
        speed = sizeScalarCoefficient * 1/size;
        
        // set velocity in random direction
        setRandVelocity();
        
        health = (int)size;
           
        // no bullet until one has hit
        collidingBulletVelocity = Vector2f.ZERO;
                
    }
    
    public void setRandomPos() {
        Vector2f upperBound = Vector2f.add(Camera.getView().getCenter(), Camera.getView().getSize());
        Vector2f lowerBound = Vector2f.sub(Camera.getView().getCenter(), Camera.getView().getSize());
        int tx = CMath.randInt((int)lowerBound.x, (int)upperBound.x);
        int ty = CMath.randInt((int)lowerBound.y, (int)upperBound.y);
        if (rand.nextDouble() < 0.5) {
            tx = (int)lowerBound.x;
        } else {
            ty = (int)lowerBound.y;
        }
        pos = new Vector2f(tx, ty);
        asteroidSprite.setPosition(pos);
        if (intersectsWithAsteroid()) {
            setRandomPos();
        }
    }

    @Override
    public void update(float dt) {
        
        collidingBulletVelocity = Vector2f.ZERO;
        
        if (intersectsWithPlayer()) {
            handlePlayerIntersection();
        }
        
        if (intersectsWithBullet()) {
            handleBulletIntersection();
            collidingBulletVelocity = Vector2f.mul(collidingBulletVelocity, dt);
        }
        
        if (intersectsWithAsteroid()) {
            handleAsteroidIntersection();
        }
        
        v = Vector2f.add(v, collidingBulletVelocity);
        collidingBulletVelocity = Vector2f.ZERO;
        
        // limit asteroid velocity to max speed
        float s = CMath.length(v);
        if (s > maxSpeed) {
            v = Vector2f.div(v, s);
            v = Vector2f.mul(v, maxSpeed);
        }
        
        pos = Vector2f.add(pos, v);
        
        asteroidSprite.setPosition(pos);
    }
    
    public boolean isOutOfBounds() {
        return (pos.x > GameScreen.getBounds().x || pos.x < -GameScreen.getBounds().x ||
                pos.y > GameScreen.getBounds().y || pos.y < -GameScreen.getBounds().y);
    }
    
    /**
     * If the asteroid intersects with the player.
     * @return true if asteroid intersects with player, false otherwise
     */
    public boolean intersectsWithPlayer() {
        return (asteroidSprite.getGlobalBounds().contains(GameScreen.getCurrentPlayer().getPos()));
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
        for (Entity e : GameScreen.getEntities()) {
            if (e instanceof BulletEntity &&
                    asteroidSprite.getGlobalBounds().intersection(e.getBounds()) != null) { 
                collidingBulletVelocity = Vector2f.div(e.getVelocity(), size*2);
                collidingBulletVelocity = Vector2f.add(collidingBulletVelocity, v);
                ((BulletEntity)e).setEntityHit(true);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Handles intersection with bullets.
     */
    public void handleBulletIntersection() {
        health--;
    }
    
    public boolean intersectsWithAsteroid() {
        for (Entity e : GameScreen.getEntities()) {
            if (e instanceof AsteroidEntity &&
                    asteroidSprite.getGlobalBounds().intersection(e.getBounds()) != null &&
                    !this.equals(e)) {
                collidingAsteroid = (AsteroidEntity)e;
                return true;
            }
        }
        return false;
    }
    
    public void handleAsteroidIntersection() {
        CMath.handleCollision(this, collidingAsteroid);
    }

    @Override
    public boolean toBeRemoved() {
        return health == 0 || isOutOfBounds();
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
        return v;
    }
    
    @Override
    public void setVelocity(Vector2f v) {
        this.v = v;
    }
    
    private void setRandVelocity() {
        v = new Vector2f(CMath.randFloat(-2, 2), CMath.randFloat(-2, 2));
        v = Vector2f.mul(v, speed);
    }
    
    @Override
    public float getSize() {
        return size;
    }

    @Override
    public float getRotation() {
        return asteroidSprite.getRotation();
    }
}
