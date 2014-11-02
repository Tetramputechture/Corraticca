/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Sprite;

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
     * Gets if the entity is removable or not.
     * @return if the entity is removable.
     */
    public abstract boolean isRemovable();

    /**
     * Gets if the entity is out of bounds of the window.
     * @return if the entity is out of the window bounds.
     */
    public abstract boolean isOutOfBounds();

}
