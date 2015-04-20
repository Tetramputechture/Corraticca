package coratticca.util;

import org.jsfml.system.Vector2f;

/**
 * Various useful functions for vectors.
 * @author Nick
 */
public class Vector {
    
    /**
     * Finds the magnitude (length) of a vector.
     * @param v the vector.
     * @return the length of the vector.
     */
    public static float length(Vector2f v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    /**
     * Finds the dot product of two vectors.
     * @param a the first vector.
     * @param b the second vector.
     * @return the dot product of the first and second vectors.
     */
    public static float dot(Vector2f a, Vector2f b) {
        return a.x * b.x + a.y * b.y;
    }

    /**
     * Normalizes a vector in place.
     * @param v the vector to be normalized.
     * @return the normalized vector.
     */
    public static Vector2f normalize(Vector2f v) {
        float mag = length(v);
        if (mag > 0) {
            return Vector2f.div(v, mag);
        } else {
            return Vector2f.ZERO;
        }
    }
}
