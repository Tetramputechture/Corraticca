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
    
    private final RandomUtils random = new RandomUtils();

    public ParticleEntity(Vector2 position) {
        super(position);
        velocity = Vector2.Zero;
        acceleration = new Vector2(0, 0.05f);

        lifespan = random.randInt(200, 255);
    }
    
    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }
    
    public Vector2 getAcceleraion() {
        return acceleration;
    }

    public boolean isDead() {
        return lifespan < 0.0;
    }

    public void update() {
        velocity = velocity.add(acceleration);
        position = position.add(velocity);

        lifespan -= 2.0;
    }

    @Override
    public void draw(RenderTarget rt, RenderStates rs) {
        CircleShape c = new CircleShape(2);
        c.setFillColor(new Color(62, 108, 213, (int) lifespan));
        c.setPosition(position.toVector2f());
        rt.draw(c);
    }

}
