/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public class SpawnEnemyAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "SPAWN_ENEMY";
    
    private Texture enemyTexture;
    
    /**
     * Set's the bullet's sprite.
     */
    public SpawnEnemyAction() {
        
        enemyTexture = new Texture();
        String enemyTextureFile = "enemy1.png";
       
        try {
            this.enemyTexture.loadFromFile(Paths.get(enemyTextureFile));
        } catch (IOException ex) {
            Logger.getLogger(PlayerEntity.class.getName()).log(java.util.logging.Level.SEVERE, 
                    String.format("Unable to load file %s!\n", enemyTextureFile), 
                    ex);
        }
        
        enemyTexture.setSmooth(true);
       
    }

    @Override
    public void execute() {
        
        System.out.println("Enemy spawned!");
        
        Sprite enemySprite = new Sprite(enemyTexture);
        enemySprite.setOrigin(Vector2f.div(new Vector2f(enemyTexture.getSize()), 2));
        
        Random c = new Random();
        int r = (int)(Math.random()*256);
        int g = (int)(Math.random()*206) + 50;
        int b = (int)(Math.random()*256);
        
        enemySprite.setColor(new Color(r, g, b));
        
        GameScreen.addEntity(new EnemyEntity(enemySprite));
    }
    
}
