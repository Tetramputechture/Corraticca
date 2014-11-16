/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.Audio;
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
public class EnemyEntity extends Entity {
    
    private final Sprite enemySprite;
    
    private Vector2f pos;
    private Vector2f norm;
    
    private BulletEntity collidingBullet;
    private Vector2f collidingBulletVelocity;
    
    private final float size;
    private final float inverseSize;
    private final float sizeScalar;
    private static final float sizeScalarCoefficient = 3.5f;
    
    private int health;
    
    private boolean hitPlayer;
    
    private Random rand = new Random();

    /**
     * Sets rotation and position of the enemy.
     * @param s the sprite of the enemy.
     */
    public EnemyEntity(Sprite s) {
        // init sprite
        super(s);
        enemySprite = s;
        
        // set position at a random point on the bounds of the window
        Vector2f upperBound = Vector2f.add(Camera.getView().getCenter(), Camera.getView().getSize());
        Vector2f lowerBound = Vector2f.sub(Camera.getView().getCenter(), Camera.getView().getSize());
        int tx = randInt((int)lowerBound.x, (int)upperBound.x);
        int ty = randInt((int)lowerBound.y, (int)upperBound.y);
        if (rand.nextDouble() < 0.5) {
            tx = (int)lowerBound.x;
        } else {
            ty = (int)lowerBound.y;
        }
        pos = new Vector2f(tx, ty);
        enemySprite.setPosition(pos);
        
        int r = rand.nextInt(256);
        while (r > 100) {
            if (Math.random() > 0.05) {
                r = rand.nextInt(256);
            } else {
                break;
            }
        }
        int g = rand.nextInt(206) + 25;
        int b = rand.nextInt(206) + 50;
        
        enemySprite.setColor(new Color(r, g, b));
        
        size = (float)(Math.sqrt(r + 1) / 4) + 0.5f;
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
            handleBulletIntersection(collidingBullet);
            collidingBulletVelocity = Vector2f.mul(collidingBulletVelocity, dt);
        }
       
        pos = Vector2f.add(pos, norm);
        pos = Vector2f.add(pos, collidingBulletVelocity);
        
        enemySprite.setPosition(pos); 
    }
    
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
    
    public void followPlayer() {
        
        // get vector between enemy and player
        Vector2f d = Vector2f.sub(GameScreen.getCurrentPlayer().getPos(), pos);
        
        float length = (float)Math.sqrt(d.x*d.x + d.y*d.y);
        if (length > 0) {
            norm = Vector2f.div(d, length);
        }
        norm = Vector2f.mul(norm, sizeScalar);
    }
    
    /**
     * If the enemy should be removed.
     * @return if the enemy has no health or intersected with the player, and therefore should be removed.
     */
    @Override
    public boolean toBeRemoved() {
        if (health == 0) {
            GameScreen.killEnemy();
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Spawns a new death particle for the enemy.
     */
    public void spawnDeathParticle() {
        Sprite t = enemySprite;
        ParticleEntity deathParticle = new ParticleEntity(t);
        deathParticle.setPos(t.getPosition());
        deathParticle.setRotation(t.getRotation());
        norm = hitPlayer ? Vector2f.mul(norm, 0.1f) : Vector2f.neg(norm);
        deathParticle.setVelocity(norm);
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
                    enemySprite.getGlobalBounds().contains(e.getPos())) { 
                collidingBullet = (BulletEntity)e;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Handles intersection with bullets.
     * @param b the bullet the enemy intersected.
     */
    public void handleBulletIntersection(BulletEntity b) {
        collidingBulletVelocity = Vector2f.add(b.getVelocity(), norm);
        if (Math.random() < 0.5) {
            Audio.playSound("sounds/enemyHit.wav", inverseSize);
        } else {
            Audio.playSound("sounds/enemyHit2.wav", inverseSize);
        }
        b.setEnemyHit(true);
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
    
    private int randInt(int min, int max) {

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt(Math.abs((max - min) + 1)) + min;

        return randomNum;
        
    }
}

