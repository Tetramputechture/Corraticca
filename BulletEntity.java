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
    private final int vx;
    private final int vy;
    private final Sprite bulletSprite;

    public BulletEntity(Sprite s) {
        super(s);
        bulletSprite = s;
        
        x = PlayerEntity.getPos().x - PlayerEntity.getSize().x/3.0f;
        y = PlayerEntity.getPos().y + PlayerEntity.getSize().y/3.0f;
        vx = (int) (20 * Math.sin(Math.toRadians(PlayerEntity.getAngle())));
        vy = (int) (20 * Math.cos(Math.toRadians(PlayerEntity.getAngle())));
        bulletSprite.setRotation(PlayerEntity.getAngle());
        bulletSprite.setPosition(x, y);
    }

    @Override
    public void update(float dt) {
        x += vx * dt;
        y -= vy * dt;

        bulletSprite.setPosition(x, y);
    }
    
}
