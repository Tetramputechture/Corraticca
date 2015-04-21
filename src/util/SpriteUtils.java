package coratticca.util;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Useful functions for sprites.
 * @author Nick
 */
public class SpriteUtils {
    
    public static void setOriginAtCenter(Sprite s, Texture t) {
        s.setOrigin(Vector2f.div(new Vector2f(t.getSize()), 2));
    }
    
    public static AABB globalBoundsToAABB(Sprite s) {
        
        // make new bounding boxes, s little smaller than the sprites bounding boxes
        // this stops objects from colliding before intersecting
        
        float adjustmentScalar = 2.5f;
        
        FloatRect bounds = s.getGlobalBounds();
        
        // convert bounds to aabb
        Vector2f c = new Vector2f((bounds.left + bounds.width/2), 
                                            bounds.top + bounds.height/2);
        
        Vector2f r = new Vector2f(bounds.width/adjustmentScalar, bounds.height/adjustmentScalar);
                
        return new AABB(c, r);
    }
    
}