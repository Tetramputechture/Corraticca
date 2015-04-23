package coratticca.entity.gameentity;

import coratticca.entity.Entity;
import coratticca.physics.AABB;
import coratticca.entitygrid.EntityGrid;
import coratticca.physics.CollisionHandler;
import coratticca.util.SpriteUtils;
import coratticca.screen.GameScreen;
import coratticca.vector.Vector2;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;

/**
 *
 * @author Nick
 */
public abstract class GameEntity extends Entity {
    
    /**
     * This GameGameEntity's sprite.
     */
    protected Sprite sprite;

    public GameEntity(Vector2 pos) {
        super(pos);
    }
    
    /**
     * Initializes the Sprite of the current GameEntity.
     */
    public abstract void initSprite();
     
    /**
     * Gets the sprite of this GameEntity.
     *
     * @return this GameEntity's sprite.
     */
    public Sprite getSprite() {
        return sprite;
    }
    
    /**
     * Returns the AABB of this GameEntity.
     *
     * @return
     */
    public AABB getBounds() {
        return SpriteUtils.globalBoundsToAABB(sprite);
    }
    
     /**
     * Detects collisions with any other entities on the screen.
     *
     * @param grid the grid to check collision against.
     * @param physics the physics handler to handle the collisions.
     * @param dt the difference in frametime from last update.
     */
    public abstract void detectAndHandleCollisions(EntityGrid grid, CollisionHandler physics, float dt);
    
    /**
     * If this GameEntity should be removed from the grid.
     *
     * @param grid the grid for the GameEntity to be removed from.
     * @return iff the GameEntity should be removed from the EntityGrid.
     */
    public abstract boolean toBeRemoved(EntityGrid grid);

    /**
     * Handles this GameEntity's removal from a game.
     *
     * @param g the game for the GameEntity to be removed from.
     */
    public void handleRemoval(GameScreen g) {

    }
    
    /**
     * If this GameEntity is out of an entity grid bounds or not.
     *
     * @param grid the EntityGrid whose bounds to check against.
     * @return if this GameEntity is out of the game bounds.
     */
    public boolean isOutOfBounds(EntityGrid grid) {
        Vector2 bounds = grid.getSize();
        return position.x > bounds.x || position.x < -bounds.x
                || position.y > bounds.y || position.y < -bounds.y;
    }

    /**
     * Gets the size of this GameEntity.
     *
     * @return this GameEntity's size.
     */
    public float getSize() {
        return sprite.getScale().x;
    }

    /**
     * Sets the rotation of this GameEntity.
     *
     * @param degrees the angle to rotate, in degrees
     */
    public void setRotation(float degrees) {
        sprite.rotate(degrees);
    }

    /**
     * Gets the rotation of this GameEntity.
     *
     * @return this GameEntity's rotation, in degrees
     */
    public float getRotation() {
        return sprite.getRotation();
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        target.draw(sprite);
    }
    
}
