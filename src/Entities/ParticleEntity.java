/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Entities;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * A particle entity that fades out over time. 
 * @author Nick
 */
public final class ParticleEntity extends Entity {
    
    private final Sprite particleSprite;
    
    private Vector2f pos;
    private Vector2f v;
    
    private static final float decelRate = 0.5f;
    
    private static final float fadeRate = 300;
    
    /**
     * The constructor, initializes the sprite.
     * @param s the sprite of the particle.
     */
    public ParticleEntity(Sprite s) {
        super(s);
        particleSprite = s;

        pos = Vector2f.ZERO;
        v = Vector2f.ZERO;
    }
    
    /**
     * Sets the rotation of the particle.
     * @param angle the angle to be set.
     */
    public void setRotation(float angle) {
        particleSprite.setRotation(angle);
    }
    
    /**
     * Sets the velocity of the particle.
     * @param v the velocity to be set.
     */
    @Override
    public void setVelocity(Vector2f v) {
        this.v = v;
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

    @Override
    public Vector2f getVelocity() {
        return v;
    }

    @Override
    public float getSize() {
        return particleSprite.getScale().x;
    }
    
    @Override
    public float getRotation() {
        return particleSprite.getRotation();
    }
}
