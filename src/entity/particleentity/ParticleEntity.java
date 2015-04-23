package coratticca.entity.particleentity;

import coratticca.entity.Entity;
import coratticca.vector.Vector2;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

/**
 * A ParticleEntity that has a position, velocity, acceleration, and lifespan.
 *
 * @author Nick
 */
public class ParticleEntity extends Entity {
    
    private final Vector2 acceleration;
    
    private float lifespan;
    
    public ParticleEntity(Vector2 position) {
        super(position);
        velocity = new Vector2();
        acceleration = new Vector2();
        
        lifespan = 255;
    }
    
    public boolean isDead() {
        return lifespan < 0.0;
    }
    
    @Override
    public void update(float dt) {
        velocity = velocity.add(acceleration);
        position = position.add(velocity);
        
        lifespan -= 2.0;
    }

    @Override
    public void draw(RenderTarget rt, RenderStates rs) {
        CircleShape c = new CircleShape(5);
        c.setFillColor(new Color(255, 255, 255, (int)lifespan));
        rt.draw(c);
    }
    

}
