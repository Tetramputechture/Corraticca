/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Sprite;

/**
 * @author Nick
 * Abstract class to handle all Entities.
 * Entities are glorified sprites that are easy to handle.
 */
public abstract class Entity {
    
    private final Sprite sprite;
    
    public Entity(Sprite s) {
        sprite = s;
    }
    
    public void draw() {
        Window.getWindow().draw(sprite);
    }
    
    public abstract void update(float dt);

    boolean isRemovable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean isOutOfBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
