package coratticca.util;

import coratticca.physics.AABB;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import coratticca.vector.Vector2;

/**
 * Useful functions for sprites.
 *
 * @author Nick
 */
public class SpriteUtils {

    public static void setOriginAtCenter(Sprite s, Texture t) {
        s.setOrigin((new Vector2(t.getSize()).scl(0.5f)).toVector2f());
    }

    public static AABB globalBoundsToAABB(Sprite s) {

        // make new bounding boxes, a little smaller than the sprites bounding boxes
        // this stops objects from colliding before intersecting
        float adjustmentScalar = 2.5f;

        FloatRect bounds = s.getGlobalBounds();

        // convert bounds to aabb
        Vector2 center = new Vector2((bounds.left + bounds.width / 2),
                bounds.top + bounds.height / 2);

        Vector2 radius = new Vector2(bounds.width / adjustmentScalar, bounds.height / adjustmentScalar);

        return new AABB(center, radius);
    }

}
