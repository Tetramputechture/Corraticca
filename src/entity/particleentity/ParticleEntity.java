package coratticca.entity.particleentity;

import coratticca.entity.Entity;
import coratticca.vector.Vector2;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;

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
    
    private Sprite sprite;

    public ParticleEntity(Vector2 position) {
        super(position);
        velocity = Vector2.Zero;
        acceleration = Vector2.Zero;
        lifespan = 255;
        decayRate = 2;
        radius = 2;
        color = Color.WHITE;
        sprite = null;
    }
    
    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }
    
    public Vector2 getAcceleraion() {
        return acceleration;
    }
    
    public void setLifespan(float lifespan) {
        this.lifespan = lifespan;
    }
    
    public float getLifespan() {
        return lifespan;
    }
    
    public void setDecayRate(float decayRate) {
        this.decayRate = decayRate;
    }
    
    public float getDecayRate() {
        return decayRate;
    }
    
    public void setRadius(float radius) {
        this.radius = radius;
        if (sprite != null) {
            sprite.setScale(new Vector2(radius, radius).toVector2f());
        }
    }

    public float getRadius() {
        return radius;
    }
    
    public void setColor(Color color) {     
        this.color = color;      
    }
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public Sprite getSprite() {
        return sprite;
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
        color = new Color(color.r, color.g, color.b, (int) lifespan);
        if (sprite == null) {
            CircleShape c = new CircleShape(radius);
            c.setFillColor(color);
            c.setPosition(position.toVector2f());
            rt.draw(c);
        } else {
            sprite.setColor(color);
            sprite.setPosition(position.toVector2f());
            rt.draw(sprite);
        }
    }

}
