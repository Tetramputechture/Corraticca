/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.particlesystem;

import coratticca.entity.particleentity.ParticleEntity;
import coratticca.vector.Vector2;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

/**
 *
 * @author Nick
 */
public class ParticleSystem implements Drawable {
    
    /**
     * List holding all the ParticleEntities in this ParticleSystem
     */
    private final ArrayList<ParticleEntity> particles;
    
    /**
     * The velocity of the particles in this ParticleSystem.
     */
    private Vector2 particleVelocity;

    /**
     * The acceleration of the particles in this ParticleSystem.
     */
    private Vector2 particleAcceleration;
    
    /**
     * The decay rate of the particles in this ParticleSystem.
     */
    private float particleDecayRate;
    
    /**
     * The color of the particles in this ParticleSystem.
     */
    private Color particleColor;

    /**
     * Where each ParticleEntity begins.
     */
    private Vector2 origin;
    
    public ParticleSystem(Vector2 origin) {
        this.origin = origin;
        particleVelocity = Vector2.Zero;
        particleAcceleration = Vector2.Zero;
        particleDecayRate = 2.0f;
        particleColor = Color.WHITE;
        particles = new ArrayList<>();
    }
    
    public void addParticle() {
        ParticleEntity p = new ParticleEntity(origin);
        p.setAcceleration(particleAcceleration);
        p.setVelocity(particleVelocity);
        p.setDecayRate(particleDecayRate);
        p.setColor(particleColor);
        particles.add(p);
    }
    
    public void clearParticles() {
        particles.clear();
    }
    
    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }
    
    public Vector2 getOrigin() {
        return origin;
    }
    
    public void setParticleVelocity(Vector2 particleVelocity) {
        this.particleVelocity = particleVelocity;
    }
    
    public Vector2 getParticleVelocity() {
        return particleVelocity;
    }

    
    public void setParticleAcceleration(Vector2 particleAcceleration) {
        this.particleAcceleration = particleAcceleration;
    }
    
    public Vector2 getParticleAcceleration() {
        return particleAcceleration;
    }

    public void setParticleDecayRate(float particleDecayRate) {
        this.particleDecayRate = particleDecayRate;
    }
        
    public float getParticleDecayRate() {
        return particleDecayRate;
    }
    
    public void setParticleColor(Color particleColor) {
        this.particleColor = particleColor;
    }

    public Color getParticleColor() {
        return particleColor;
    }
      
    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        Iterator<ParticleEntity> it = particles.iterator();
        while (it.hasNext()) {
            ParticleEntity p = it.next();
            p.update();
            rt.draw(p);
            if (p.isDead()) {
                it.remove();
            }
        }
    }
    
}
