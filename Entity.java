/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Sprite;

/**
 *
 * @author Nick
 */
public class Entity {
    
    private final Sprite sprite;
    
    public Entity(Sprite s) {
        sprite = s;
    }
    
    public void draw() {
        Window.getWindow().draw(sprite);
    }
    
    
    
}
