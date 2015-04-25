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
     * Where each ParticleEntity begins.
     */
    private Vector2 origin;
    
    public ParticleSystem(Vector2 origin) {
        this.origin = origin;
        particles = new ArrayList<>();
    }
    
    public void addParticle() {
        particles.add(new ParticleEntity(origin));
    }
    
    public void clearParticles() {
        particles.clear();
    }
    
    public void setOrigin(Vector2 origin) {
        this.origin = origin;
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
