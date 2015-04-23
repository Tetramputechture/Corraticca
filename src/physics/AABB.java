package coratticca.physics;

import coratticca.vector.Vector2;

/**
 * An axis aligned bounding box with center and radius.
 *
 * @author Nick
 */
public class AABB {

    private final Vector2 center;
    private final Vector2 radius;

    /**
     * Constructs a new AABB with specified center and radius.
     *
     * @param center the center of the AABB
     * @param radius the radius of the AABB
     */
    public AABB(Vector2 center, Vector2 radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * If this AABB contains a specified point.
     *
     * @param point the point to be checked.
     * @return if the point is within this AABB.
     */
    public boolean containsPoint(Vector2 point) {
        float minX = center.x - radius.x;
        float maxX = center.x + radius.x;

        float minY = center.y - radius.y;
        float maxY = center.y + radius.y;

        return (minX < point.x && point.x < maxX
                && minY < point.y && point.y < maxY);
    }

    /**
     * If this AABB intersects another AABB
     *
     * @param other the AABB to be checked.
     * @return if this AABB intersects the specified AABB
     */
    public boolean intersectsAABB(AABB other) {
        Vector2 otherCenter = other.center;
        Vector2 otherRadius = other.radius;
        boolean a = Math.abs(center.x - otherCenter.x) <= (radius.x + otherRadius.x);
        boolean b = Math.abs(center.y - otherCenter.y) <= (radius.y + otherRadius.y);

        return a && b;
    }

    /**
     * Gets the center of this AABB.
     *
     * @return the center of this AABB.
     */
    public Vector2 getCenter() {
        return center;
    }

    /**
     * Gets the radius of this AABB.
     *
     * @return the radius of this AABB.
     */
    public Vector2 getRadius() {
        return radius;
    }
}
