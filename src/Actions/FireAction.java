/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Entities.BulletEntity;
import coratticca.Entities.PlayerEntity;
import coratticca.Utils.Screen.GameScreen;
import coratticca.Utils.Window;
import org.jsfml.system.Vector2f;

/** 
 * Action to create a bullet entity.
 * @author Nick
 */
public class FireAction extends Action {
    
    private final int fireSpeed;
    
    private final float playerVelocityScalar;
    
    public FireAction() {
        fireSpeed = 500;
        playerVelocityScalar = 40;
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

        w.getAudioHandler().playSound("sounds/firesound.wav", 1);
        
        PlayerEntity player = g.getCurrentPlayer();
        float playerRotation = player.getRotation();
        
        float angle = (float)(Math.toRadians(playerRotation));
        float sin = (float)Math.sin(angle);
        float cos = (float)Math.cos(angle);
        
        // set position and angle based off current player sprite
        Vector2f pos = Vector2f.add(player.getPos(), new Vector2f(sin, cos));
        BulletEntity b = new BulletEntity(g, pos);
        
        Vector2f forward = new Vector2f(sin, -cos);
        forward = Vector2f.mul(forward, fireSpeed);
        Vector2f inheritedVelocity = Vector2f.mul(player.getVelocity(), playerVelocityScalar);
        b.setVelocity(Vector2f.add(forward, inheritedVelocity));
        
        b.setRotation(playerRotation);
        g.addEntity(b); 
        g.fireShot();
    }

    @Override
    public String getName() {
        return "FIRE";
    }
}
