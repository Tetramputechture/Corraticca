/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.util.Random;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * An entity representing an enemy, that:
 * - Lowers the player's health if touched by the player.
 * - Dies after a certain number of bullets hit it.
 * @author Nick
 */
public class EnemyEntity extends Entity {
    
    private float x;
    private float y;
    private float vx;
    private float vy;
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
        Random r = new Random();
        x = r.nextInt(Window.getWidth());
        y = r.nextInt(Window.getHeight());
        vx = r.nextInt(150) + 100;
        vy = r.nextInt(150) + 100;
        if (r.nextDouble() < 0.5) {
            x = 0;
        } else {
            y = 0;
        }
        enemySprite.setPosition(x, y);
        
        // set angle based on player position
        double angle = Math.atan2(GameScreen.getCurrentPlayer().getPos().y - enemySprite.getPosition().y, 
                                  GameScreen.getCurrentPlayer().getPos().x - enemySprite.getPosition().x);
        
        angle *= (180/Math.PI);
        
        if(angle < 0) {
            angle = 360 + angle;
        }
        
        enemySprite.setRotation(90 + (float)angle);
        
        health = 3;
    }

    /**
     * Updates the enemy's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {
       
        x += vx * dt;
        y -= vy * dt;
        
        if (x > Window.getWidth() || x < 0) {
            vx = -vx;
        }
        if (y > Window.getHeight() || y < 0) {
            vy = -vy;
        }
        
        enemySprite.setPosition(x, y);
        
    }
    
    /**
     * If the enemy should be removed.
     * @return if the enemy has no health or intersected with the player, and therefore should be removed.
     */
    @Override
    public boolean remove() {
        return health == 0 || intersectsWithPlayer() || intersectsWithBullet();
    }
    
    public boolean intersectsWithPlayer() {
        if (enemySprite.getGlobalBounds().contains(GameScreen.getCurrentPlayer().getPos())) {
            GameScreen.getCurrentPlayer().changeHealth(-1);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean intersectsWithBullet() {
        for (Entity e : GameScreen.getEntities()) {
            if (e.toString().equals("Bullet") && 
                    enemySprite.getGlobalBounds().contains(e.getPos())) {
                return true;   
            }
        }
        return false;
    }
    
    @Override
    public Vector2f getPos() {
        return enemySprite.getPosition();
    }
    
    @Override
    public String toString() {
        return "Enemy";
    }
    
}

