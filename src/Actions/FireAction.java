/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Utils.Audio;
import coratticca.Entities.BulletEntity;
import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.CSprite;
import coratticca.Utils.CPrecache;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

/** 
 * Action to create a bullet entity.
 * @author Nick
 */
public class FireAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "FIRE";
    
    /**
     * Makes a bullet entity and adds it to the game screen.
     */
    @Override
    public void execute() {
        Texture t = CPrecache.getBulletTexture();
        Sprite s = new Sprite(t);
        CSprite.setOriginAtCenter(s, t);

        Audio.playSound("sounds/firesound.wav", 1);
        GameScreen.addEntity(new BulletEntity(s)); 
        GameScreen.fireShot();
    }

    @Override
    public String toString() {
        return NAME;
    }
}
