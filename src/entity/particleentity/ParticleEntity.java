package coratticca.entity.particleentity;

import coratticca.entity.Entity;
import coratticca.util.RandomUtils;
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

    private Vector2 acceleration;

    private float lifespan;
    
    private float decayRate;
    
    private float radius;
    
    private Color color;

    public ParticleEntity(Vector2 position) {
        super(position);
        velocity = Vector2.Zero;
        acceleration = Vector2.Zero;
        lifespan = 255;
        decayRate = 2;
        radius = 2;
        color = Color.WHITE;
    }
    
    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }
    
    public Vector2 getAcceleraion() {
        return acceleration;
    }
    
    public void setDecayRate(float decayRate) {
        this.decayRate = decayRate;
    }
    
    public float getDecayRate() {
        return decayRate;
    }
    
    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }
    
    public void setColor(Color color) {     
        this.color = color;       
    }
    
    public boolean isDead() {
        return lifespan < 0.0;
    }

    public void update() {
        velocity = velocity.add(acceleration);
        position = position.add(velocity);

        lifespan -= decayRate;
    }

    @Override
    public void draw(RenderTarget rt, RenderStates rs) {
        CircleShape c = new CircleShape(radius);
        color = new Color(color.r, color.g, color.b, (int) lifespan);
        c.setFillColor(color);
        c.setPosition(position.toVector2f());
        rt.draw(c);
    }

}
