/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public class ParticleEntity extends Entity {
    
    private final Sprite particleSprite;
    private Vector2f pos;
    private Vector2f v;
    private final float decelRate;
    private final float fadeRate;
    
    public ParticleEntity(Sprite s) {
        super(s);
        particleSprite = s;
        
        decelRate = 0.5f;
        fadeRate = 300;
        v = Vector2f.ZERO;
    }

    @Override
    public void update(float dt) {
        
        pos = Vector2f.add(pos, new Vector2f(v.x + (v.x * decelRate) * dt, v.y + (v.y * decelRate) * dt));
        
        particleSprite.setPosition(pos);
        
        int r = particleSprite.getColor().r;
        int g = particleSprite.getColor().g;
        int b = particleSprite.getColor().b;
        int a = particleSprite.getColor().a;
        
        a -= fadeRate * dt;
        
        particleSprite.setColor(new Color(r, g, b, a));
    }

    @Override
    public boolean toBeRemoved() {
        return particleSprite.getColor().a <= 0;
    }

    @Override
    public Vector2f getPos() {
        return pos;
    }

    @Override
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    
    public void setRotation(float angle) {
        particleSprite.setRotation(angle);
    }
    
    public void setVelocity(Vector2f v) {
        this.v = v;
    }
    
}
