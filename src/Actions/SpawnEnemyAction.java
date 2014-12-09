/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Entities.EnemyShipEntity;
import coratticca.Utils.CSprite;
import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.CPrecache;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

/**
 * The action to spawn an enemy.
 * @author Nick
 */
public class SpawnEnemyAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "SPAWN_ENEMY";

    @Override
    public void execute() {
        Texture t = CPrecache.getEnemyTexture();
        Sprite s = new Sprite(t);
        CSprite.setOriginAtCenter(s, t);
        GameScreen.addEntity(new EnemyShipEntity(s));
    }
    
}
