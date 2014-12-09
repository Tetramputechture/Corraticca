/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Entities.AsteroidEntity;
import coratticca.Utils.CSprite;
import coratticca.Utils.CPrecache;
import coratticca.Utils.Screen.GameScreen;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Spawns a new Asteroid.
 * @author Nick
 */
public class SpawnAsteroidAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "SPAWN_ASTEROID";
    
    private final Vector2f pos;
    private final int size;

    /**
     * Initializes the asteroid's sprite, and sets its position and size.
     * @param pos the position of the asteroid.
     * @param size the size of the asteroid.
     */
    public SpawnAsteroidAction(Vector2f pos, int size) {
        this.pos = pos;
        this.size = size;
    }
    
    @Override
    public void execute() {
        Texture t;
        if (Math.random() < 0.5) {
            t = CPrecache.getAsteroidTextureA();
        } else {
            t = CPrecache.getAsteroidTextureB();
        }
        Sprite s = new Sprite(t);
        CSprite.setOriginAtCenter(s, t);
        
        GameScreen.addEntity(new AsteroidEntity(s, pos, size));
    }
    
}
