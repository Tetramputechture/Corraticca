/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

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
     * Gets the position of the entity relative to the current window.
     * @return the position of the entity as a float vector
     */
    public abstract Vector2f getPos();
    
    public abstract void setPos(Vector2f pos);

}
