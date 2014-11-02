/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Sprite;

/**
 * @author Nick
 * This handles the creation of a 'bullet', what the player shoots.
 */
public class BulletEntity extends Entity {
    
    private float x;
    private float y;
    private final int vx;
    private final int vy;
    private final Sprite bulletSprite;

    public BulletEntity(Sprite s) {
        // init sprite
        super(s);
        bulletSprite = s;
        
        // set position and angle based off current player sprite
        x = (float) (PlayerEntity.getPos().x + Math.cos(Math.toRadians(PlayerEntity.getAngle())));
        y = (float) (PlayerEntity.getPos().y + Math.sin(Math.toRadians(PlayerEntity.getAngle())));
        vx = (int) (220 * Math.sin(Math.toRadians(PlayerEntity.getAngle())));
        vy = (int) (220 * Math.cos(Math.toRadians(PlayerEntity.getAngle())));
        bulletSprite.setRotation(PlayerEntity.getAngle());
        bulletSprite.setPosition(x, y);
    }

    @Override
    public void update(float dt) {
        
        // move based on time
        x += vx * dt;
        y -= vy * dt;
        
        // bounce off walls
//        if (x > Window.getWidth() || x < 0) {
//            vx = -vx;
//        }
//        if (y > Window.getHeight() || y < 0) {
//            vy = -vy;
//        }

        bulletSprite.setPosition(x, y);
    }
    
}
