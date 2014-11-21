/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import coratticca.Utils.Window;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * Abstract class to handle all Entities.
 * Entities are glorified sprites that are easy to handle.
 * @author Nick
 */
public abstract class Entity {
    
    private final Sprite sprite;

    /**
     * Sets the entity's sprite.
     * @param s the sprite to be set to.
     */
    public Entity(Sprite s) {
        sprite = s;
    }

    /**
     * Draws the sprite to the window.
     */
    public void draw() {
        Window.getWindow().draw(sprite);
    }

    /**
     * Updates the entity based on time.
     * @param dt the difference in frametime from last update.
     */
    public abstract void update(float dt);

    /**
     * Gets if the entity should be removed.
     * @return if the entity needs to be removed from the game's entity list.
     */
    public abstract boolean toBeRemoved();
    
    /**
     * Handles the removal of an entity.
     */
    public abstract void handleRemoval();
    
    
    /**
     * Gets the position of the entity relative to the current window.
     * @return the position of the entity as a float vector
     */
    public abstract Vector2f getPos();
    
    /**
     * Sets the position of the entity.
     * @param pos the position to be set.
     */
    public abstract void setPos(Vector2f pos);
    
    /**
     * Gets the bounds of an entity.
     * @return
     */
    public FloatRect getBounds() {
        return sprite.getGlobalBounds();
    }

    /**
     * Gets the velocity of an entity. 
     * @return
     */
    public abstract Vector2f getVelocity();
    
    /**
     * Sets the velocity of an entity.
     * @param v the velocity to be set.
     */
    public abstract void setVelocity(Vector2f v);

    /**
     * Gets the size of an entity.
     * @return the entitiy's size.
     */
    public abstract float getSize();
    
    /**
     * Gets the rotation of an entity.
     * @return the entity's rotation.
     */
    public abstract float getRotation();
}
