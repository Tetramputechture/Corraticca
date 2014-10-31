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
public class BulletEntity extends Entity {
    
    private float x;
    private float y;
    private int vx;
    private int vy;
    private final Sprite bulletSprite;

    public BulletEntity(Sprite s) {
        super(s);
        bulletSprite = s;
        
        x = (float) (PlayerEntity.getPos().x + Math.cos(Math.toRadians(PlayerEntity.getAngle())));
        y = (float) (PlayerEntity.getPos().y + Math.sin(Math.toRadians(PlayerEntity.getAngle())));
        vx = (int) (180 * Math.sin(Math.toRadians(PlayerEntity.getAngle())));
        vy = (int) (180 * Math.cos(Math.toRadians(PlayerEntity.getAngle())));
        bulletSprite.setRotation(PlayerEntity.getAngle());
        bulletSprite.setPosition(x, y);
    }

    @Override
    public void update(float dt) {
        x += vx * dt;
        y -= vy * dt;
        
        if (x > Window.getWidth() || x < 0) {
            vx = -vx;
        }
        if (y > Window.getHeight() || y < 0) {
            vy = -vy;
        }

        bulletSprite.setPosition(x, y);
    }
    
}
