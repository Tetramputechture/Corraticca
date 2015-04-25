package coratticca.entity;

import coratticca.entitygrid.EntityGrid;
import org.jsfml.graphics.Drawable;
import coratticca.vector.Vector2;

/**
 * Abstract class to handle all Entities. Entities are glorified sprites that
 * are easy to handle.
 *
 * @author Nick
 */
public abstract class Entity implements Drawable {

    /**
     * The position of this Entity.
     */
    protected Vector2 position;

    /**
     * The velocity of this Entity.
     */
    protected Vector2 velocity;

    /**
     * Sets the entity's sprite.
     *
     * @param position the position of this Entity.
     */
    public Entity(Vector2 position) {
        this.position = position;
    }

    /**
     * Gets the position of the entity relative to the current window.
     *
     * @return the position of the entity as a float vector
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the position of the entity.
     *
     * @param position the position to be set.
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * What to do when this Entity is out of bounds of the grid.
     *
     * @param grid the grid that this Entity is out of bounds of.
     */
    public void handleOutOfBounds(EntityGrid grid) {

    }

    /**
     * Gets the velocity of an entity.
     *
     * @return
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity of an entity.
     *
     * @param v the velocity to be set.
     */
    public void setVelocity(Vector2 v) {
        this.velocity = v;
    }
}
