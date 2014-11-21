/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.Audio;
import coratticca.Utils.CPhysics;
import coratticca.Utils.CRandom;
import coratticca.Utils.CVector;
import coratticca.Utils.Camera;
import java.util.Random;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * An entity representing an enemy, that:
 * - Lowers the player's health if touched by the player.
 * - Dies after a certain number of bullets hit it.
 * @author Nick
 */
public final class EnemyShipEntity extends Entity {
    
    private final Sprite enemySprite;
    
    private Vector2f pos;
    private Vector2f v;
    
    private Vector2f collidingBulletVelocity;
    
    private final float size;
    private final float inverseSize;
    private final float sizeScalar;
    private static final float sizeScalarCoefficient = 4.5f;
    
    private int health;
    
    private boolean hitPlayer;
    
    private final Random rand = new Random();

    /**
     * Sets rotation and position of the enemy.
     * @param s the sprite of the enemy.
     */
    public EnemyShipEntity(Sprite s) {
        // init sprite
        super(s);
        enemySprite = s;
        
        // set position at a random point on the bounds of the window
        Vector2f upperBound = Vector2f.add(Camera.getView().getCenter(), Camera.getView().getSize());
        Vector2f lowerBound = Vector2f.sub(Camera.getView().getCenter(), Camera.getView().getSize());
        int tx = CRandom.randInt((int)lowerBound.x, (int)upperBound.x);
        int ty = CRandom.randInt((int)lowerBound.y, (int)upperBound.y);
        if (rand.nextDouble() < 0.5) {
            tx = (int)lowerBound.x;
        } else {
            ty = (int)lowerBound.y;
        }
        pos = new Vector2f(tx, ty);
        enemySprite.setPosition(pos);
        
        int r = rand.nextInt(256);
        int g = rand.nextInt(106) + 125;
        int b = rand.nextInt(106) + 150;
        
        enemySprite.setColor(new Color(r, g, b));
        
        size = (float)(Math.sqrt(r + 1) / 10) + 0.5f;
        inverseSize = 1/size;
        enemySprite.setScale(size, size);
        
        health = (int)(size*3);
        
        // how the enemy's velocity should be changed based on size
        sizeScalar = inverseSize * sizeScalarCoefficient;
    }

    /**
     * Updates the enemy's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {
        
        facePlayer();
        
        followPlayer();
        
        // no bullet until one has hit
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
       
        pos = Vector2f.add(pos, v);
        pos = Vector2f.add(pos, collidingBulletVelocity);
        
        enemySprite.setPosition(pos); 
    }
    
    /**
     *
     */
    public void facePlayer() {
        
        // set angle based on player position
        double angle = Math.atan2(GameScreen.getCurrentPlayer().getPos().y - enemySprite.getPosition().y, 
                                  GameScreen.getCurrentPlayer().getPos().x - enemySprite.getPosition().x);
        
        angle *= (180/Math.PI);
        
        if(angle < 0) {
            angle = 360 + angle;
        }
        
        enemySprite.setRotation(90 + (float)angle);
    }
    
    /**
     *
     */
    public void followPlayer() {
        
        // get vector between enemy and player
        Vector2f d = Vector2f.sub(GameScreen.getCurrentPlayer().getPos(), pos);
        
        v = CVector.normalize(v);
        v = Vector2f.mul(v, sizeScalar);
    }
    
    /**
     * If the enemy should be removed.
     * @return if the enemy has no health or intersected with the player, and therefore should be removed.
     */
    @Override
    public boolean toBeRemoved() {
        if (health <= 0) {
            if (!hitPlayer) {
                GameScreen.killEnemy();
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     *
     */
    @Override
    public void handleRemoval() {
        spawnDeathParticle();
    }
    
    /**
     * Spawns a new death particle for the enemy.
     */
    public void spawnDeathParticle() {
        Sprite t = enemySprite;
        ParticleEntity deathParticle = new ParticleEntity(t);
        deathParticle.setPos(t.getPosition());
        deathParticle.setRotation(t.getRotation());
        v = hitPlayer ? Vector2f.mul(v, 0.1f) : Vector2f.neg(v);
        deathParticle.setVelocity(v);
        GameScreen.addEntity(deathParticle); 
    }
    
    /**
     * If the enemy intersects with the player.
     * @return true if enemy intersects with player, false otherwise
     */
    public boolean intersectsWithPlayer() {
        return (enemySprite.getGlobalBounds().contains(GameScreen.getCurrentPlayer().getPos()));
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
                    CPhysics.boxCollisionTest(this, e)) { 
                collidingBulletVelocity = Vector2f.add(e.getVelocity(), v);
                ((BulletEntity)e).setEntityHit(true);
                return true;
            }
        }
        return false;
    }
    
    /**
     *
     * @return
     */
    public boolean intersectsWithAsteroid() {
        return GameScreen.getEntities().stream().anyMatch((e) -> (e instanceof AsteroidEntity &&
                CPhysics.boxCollisionTest(this, e)));
    }
    
    /**
     *
     */
    public void handleAsteroidIntersection() {
        health = 0;
    }
    
    /**
     * Handles intersection with bullets.
     */
    public void handleBulletIntersection() {
        if (Math.random() < 0.5) {
            Audio.playSound("sounds/enemyHit.wav", inverseSize);
        } else {
            Audio.playSound("sounds/enemyHit2.wav", inverseSize);
        }
        health--;
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
     * @return
     */
    @Override
    public float getSize() {
        return size;
    }

    /**
     *
     * @param v
     */
    @Override
    public void setVelocity(Vector2f v) {
        this.v = v;
    }
    
    /**
     *
     * @return
     */
    @Override
    public float getRotation() {
        return enemySprite.getRotation();
    }
}

