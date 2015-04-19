/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.entity;

import coratticca.util.CSprite;
import coratticca.util.AABB;
import coratticca.util.CPhysics;
import coratticca.util.Grid;
import coratticca.util.screen.GameScreen;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * Abstract class to handle all Entities.
 * Entities are glorified sprites that are easy to handle.
 * @author Nick
 */
public abstract class Entity implements Drawable {
    
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
     * Sets the entity's sprite.
     * 
     * @param pos the position of this Entity.
     */
    public Entity(Vector2f pos) {
        this.pos = pos;
    }
    
    /**
     * Initializes the Sprite of the current Entity.
     */
    public abstract void initSprite();

    /**
     * Updates the entity based on time.
     * @param dt the difference in frametime from last update.
     */
    public abstract void update(float dt);
    
    /**
     * Detects collisions with any other entities on the screen.
     * @param grid the grid to check collision against.
     * @param physics the physics handler to handle the collisions.
     * @param dt the difference in frametime from last update.
     */
    public abstract void detectAndHandleCollisions(Grid grid, CPhysics physics, float dt);

    /**
     * If bullet should be removed from the grid.
     * @param grid the grid for the entity to be removed from.
     * @return if the bullet is out of bounds or hit an enemy
     */
    public abstract boolean toBeRemoved(Grid grid);
    
    /**
     * Handles this entity's removal from a game.
     * @param g the game for the entity to be removed from.
     */
    public void handleRemoval(GameScreen g) {
        
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
     * If this Entity is out of an entity grid bounds or not.
     * @param grid the Grid whose bounds to check against.
     * @return if this Entity is out of the game bounds.
     */
    public boolean isOutOfBounds(Grid grid) {
        Vector2f bounds = grid.getSize();
        return pos.x > bounds.x || pos.x < -bounds.x || 
               pos.y > bounds.y || pos.y < -bounds.y;
    }
    
    /**
     * What to do when this Entity is out of bounds of the grid.
     * @param grid the grid that this Entity is out of bounds of.
     */
    public void handleOutOfBounds(Grid grid) {
        
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
    
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        target.draw(sprite);
    }
}
