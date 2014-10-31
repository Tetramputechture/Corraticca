/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;

/**
 *
 * @author Nick
 */
public class BulletEntity extends Entity {
    
    private final int vx = 20;
    private final int vy = 20;
    private final Clock clock;
    private float dt;
    private float x;
    private float y;
    private final Sprite bulletSprite;

    public BulletEntity(Sprite s) {
        super(s);
        bulletSprite = s;
        clock = new Clock();
        dt = 0;
        bulletSprite.setRotation(PlayerEntity.getAngle());
    }

    @Override
    public void update() {
        dt = clock.getElapsedTime().asSeconds();
        
        x = PlayerEntity.getPos().x + vx * dt;
        y = PlayerEntity.getPos().y + vy * dt;
        
        bulletSprite.setPosition(x, y);
    }
    
}
