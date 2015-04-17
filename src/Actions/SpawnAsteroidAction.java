/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Entities.AsteroidEntity;
import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.Window;
import org.jsfml.system.Vector2f;

/**
 * Spawns a new Asteroid.
 * @author Nick
 */
public class SpawnAsteroidAction extends Action {
    
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
    public void execute(Window w) {
        GameScreen g;
        // double check if the current screen is a gamescreen
        if (w.getCurrentScreen() instanceof GameScreen) {
            g = (GameScreen) w.getCurrentScreen();
        } else {
            return;
        }
        
        g.addEntity(new AsteroidEntity(g, pos, size));
    }
    
    @Override
    public String getName() {
        return "SPAWN_ASTEROID";
    }
}
