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
        Random rand = new Random();
        x = rand.nextInt(Window.getWidth());
        y = rand.nextInt(Window.getHeight());
        if (rand.nextDouble() < 0.5) {
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
        
        int r = rand.nextInt(256);
        while (r > 100) {
            if (Math.random() > 0.1) {
                r = rand.nextInt(256);
            } else {
                break;
            }
        }
        int g = rand.nextInt(206) + 25;
        int b = rand.nextInt(206) + 50;
        
        enemySprite.setColor(new Color(r, g, b));
        
        int size = (int)Math.sqrt(r + 1) / 3;
        enemySprite.setScale(size, size);
        
        health = 1;
    }

    /**
     * Updates the enemy's position based on frametime.
     * @param dt the difference in time to be updated by.
     */
    @Override
    public void update(float dt) {
        
        Vector2f top = new Vector2f(GameScreen.getCurrentPlayer().getPos().x - x,
                                    GameScreen.getCurrentPlayer().getPos().y - y);
        
        double length = Math.sqrt(top.x*top.x + top.y*top.y);
        double normX = top.x/length;
        double normY = top.y/length;
       
        x += normX * 160 * dt;
        y += normY * 160 * dt;
        
        enemySprite.setPosition(x, y);
        
        for (Entity e : GameScreen.getEntities()) {
            if (e instanceof BulletEntity &&
                    enemySprite.getGlobalBounds().contains(e.getPos())) {   
                health--;
                GameScreen.killEnemy();
                ((BulletEntity)e).setToBeRemoved(true);
                break;
            }
        }
        
    }
    
    /**
     * If the enemy should be removed.
     * @return if the enemy has no health or intersected with the player, and therefore should be removed.
     */
    @Override
    public boolean toBeRemoved() {
        if (health == 0 || intersectsWithPlayer()) {
            Random r = new Random();
            double randomValue = 0.5 + (1 - 0.5) * r.nextDouble();
            Audio.playSound("enemydeath.wav", (float)randomValue );
            return true;
        } else {
            return false;
        }
    }
    
    public boolean intersectsWithPlayer() {
        if (enemySprite.getGlobalBounds().contains(GameScreen.getCurrentPlayer().getPos())) {
            GameScreen.getCurrentPlayer().changeHealth(-1);
            return true;
        } 
        return false;
    }
    
    @Override
    public Vector2f getPos() {
        return enemySprite.getPosition();
    }
    
    @Override
    public Sprite getSprite() {
        return enemySprite;
    }
    
    @Override
    public String toString() {
        return "Enemy";
    }
    
}

