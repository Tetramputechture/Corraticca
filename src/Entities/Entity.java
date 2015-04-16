/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Utils.CSprite;
import coratticca.Utils.AABB;
import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.Window;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * Abstract class to handle all Entities.
 * Entities are glorified sprites that are easy to handle.
 * @author Nick
 */
public abstract class Entity {
    
    /**
     * This Entity's sprite.
     */
    protected Sprite sprite;
    
    /**
     * The position of this Entity.
     */
    protected Vector2f pos;
    
    /**
     * The velocity of this Entity.
     */
    protected Vector2f velocity;
    
    /**
     * The game screen for this Entity to be drawn on.
     */
    protected final GameScreen game;

    /**
     * Sets the entity's sprite.
     * 
     * @param game the game for this Entity to be drawn on.
     * @param pos the position of this Entity.
     */
    public Entity(GameScreen game, Vector2f pos) {
        this.game = game;
        this.pos = pos;
        sprite = initSprite();
        sprite.setPosition(pos);
    }
    
    /**
     * Gets the Sprite of the current Entity.
     * @return the Entity's sprite.
     */
    public abstract Sprite initSprite();

    /**
     * Draws the sprite to the window.
     */
    public void draw() {
        game.getWindow().getRenderWindow().draw(sprite);
    }

    /**
     * Updates the entity based on time.
     * @param dt the difference in frametime from last update.
     */
    public abstract void update(float dt);
    
    /**
     * Detects collisions with any other entities on the screen.
     * @param dt the difference in frametime from last update.
     */
    public abstract void detectCollisions(float dt);

    /**
     * Gets if the entity should be removed.
     * @return if the entity needs to be removed from the game's entity list.
     */
    public abstract boolean toBeRemoved();
    
    public void handleRemoval() {
        
    }
    
    /**
     * Gets the window that this Entity is parented to.
     * @return the Window this entity is drawn on.
     */
    public Window getWindow() {
        return game.getWindow();
    }
    
    /**
     * Gets the sprite of this Entity.
     * @return this Entity's sprite.
     */
    public Sprite getSprite() {
        return sprite;
    }
    
    /**
     * Gets the position of the entity relative to the current window.
     * @return the position of the entity as a float vector
     */
    public Vector2f getPos() {
        return pos;
    }
    
    /**
     * Sets the position of the entity.
     * @param pos the position to be set.
     */
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    
    /**
     * Gets the bounds of an entity, as an AABB.
     * @return
     */
    public AABB getBounds() {
        return CSprite.globalBoundsToAABB(sprite);
    }
    
    /**
     * If this Entity is out of window bounds or not.
     * @return if this Entity is out of the game bounds.
     */
    public boolean isOutOfBounds() {
        Vector2f bounds = game.getBounds();
        return pos.x > bounds.x || pos.x < -bounds.x || 
               pos.y > bounds.y || pos.y < -bounds.y;
    }

    /**
     * Gets the velocity of an entity. 
     * @return
     */
    public Vector2f getVelocity() {
        return velocity;
    }
    
    /**
     * Sets the velocity of an entity.
     * @param v the velocity to be set.
     */
    public void setVelocity(Vector2f v) {
        this.velocity = v;
    }

    /**
     * Gets the size of an entity.
     * @return the entitity's size.
     */
    public float getSize() {
        return sprite.getScale().x;
    }
    
    /**
     * Sets the rotation of an entity.
     * 
     * @param degrees the angle to rotate, in degrees
     */
    public void setRotation(float degrees) {
        sprite.rotate(degrees);
    }
    /**
     * Gets the rotation of an entity.
     * @return the entity's rotation, in degrees
     */
    public float getRotation() {
        return sprite.getRotation();
    }
}
