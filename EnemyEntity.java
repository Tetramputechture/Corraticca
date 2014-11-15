/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

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
    
    private Vector2f pos;
    private Vector2f norm;
    private Vector2f collidingBulletVelocity;
    private BulletEntity collidingBullet;
    private final float size;
    private final float sizeScalar;
    private static final float sizeScalarCoefficient = 3.5f;
    private final Sprite enemySprite;
    private int health;

    /**
     * Sets rotation and position of the enemy.
     * @param s the sprite of the enemy.
     */
    public EnemyEntity(Sprite s) {
        // init sprite
        super(s);
        enemySprite = s;
        
        // set position at a random point on the bounds of the window
        Random rand = new Random();
        int tx = rand.nextInt(Window.getWidth());
        int ty = rand.nextInt(Window.getHeight());
        if (rand.nextDouble() < 0.5) {
            tx = 0;
        } else {
            ty = 0;
        }
        pos = new Vector2f(tx, ty);
        enemySprite.setPosition(tx, ty);
        
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
        enemySprite.setScale(size, size);
        
        health = (int)(size*3);
        
        sizeScalar = (1/size) * sizeScalarCoefficient;
    }

    /**
     * Updates the enemy's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {
        
        // set angle based on player position
        double angle = Math.atan2(GameScreen.getCurrentPlayer().getPos().y - enemySprite.getPosition().y, 
                                  GameScreen.getCurrentPlayer().getPos().x - enemySprite.getPosition().x);
        
        angle *= (180/Math.PI);
        
        if(angle < 0) {
            angle = 360 + angle;
        }
        
        enemySprite.setRotation(90 + (float)angle);
        
        // normalize the vector betweeen the enemy and the player
        // get enemy to follow the player
        Vector2f d = Vector2f.sub(GameScreen.getCurrentPlayer().getPos(), pos);
        
        float length = (float)Math.sqrt(d.x*d.x + d.y*d.y);
        if (length > 0) {
            norm = Vector2f.div(d, length);
        }
        
        // no bullet until one has hit
        collidingBulletVelocity = Vector2f.ZERO;
        
        if (intersectsWithPlayer()) {
            handlePlayerIntersection();
        }
        
        if (intersectsWithBullet()) {
            handleBulletIntersection(collidingBullet);
        }   
       
        float tx = pos.x + norm.x * sizeScalar + (collidingBulletVelocity.x * dt);
        float ty = pos.y + norm.y * sizeScalar + (collidingBulletVelocity.y * dt);
        pos = new Vector2f(tx, ty);
        
        enemySprite.setPosition(pos); 
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
    
    public void spawnDeathParticle() {
        Sprite t = enemySprite;
        ParticleEntity deathParticle = new ParticleEntity(t);
        deathParticle.setPos(t.getPosition());
        deathParticle.setRotation(t.getRotation());
        deathParticle.setVelocity(new Vector2f(-norm.x, -norm.y));
        GameScreen.addEntity(deathParticle); 
    }
    
    public boolean intersectsWithPlayer() {
        return (enemySprite.getGlobalBounds().contains(GameScreen.getCurrentPlayer().getPos()));
    }
    
    public void handlePlayerIntersection() {
        health = 0;
        GameScreen.getCurrentPlayer().changeHealth(-1);
    }
    
    private boolean intersectsWithBullet() {    
        for (Entity e : GameScreen.getEntities()) {
            if (e instanceof BulletEntity &&
                    enemySprite.getGlobalBounds().contains(e.getPos())) { 
                collidingBullet = (BulletEntity)e;
                return true;
            }
        }
        return false;
    }
    
    public void handleBulletIntersection(BulletEntity b) {
        collidingBulletVelocity = Vector2f.add(b.getVelocity(), norm);
        if (Math.random() < 0.5) {
            Audio.playSound("enemyHit.wav", 1/size);
        } else {
            Audio.playSound("enemyHit2.wav", 1/size);
        }
        b.enemyHit(true);
        health--;
    }
    
    @Override
    public Vector2f getPos() {
        return enemySprite.getPosition();
    }
    
    @Override
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    
    @Override
    public String toString() {
        return "Enemy";
    }
}

