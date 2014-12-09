/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import org.jsfml.system.Vector2f;

/**
 * An axis aligned bounding box with center and radius.
 * @author Nick
 */
public class AABB {
    
    private final Vector2f center;
    private final Vector2f radius;
    
    /**
     * Constructs a new AABB with specified center and radius.
     * @param center the center of the AABB
     * @param radius the radius of the AABB
     */
    public AABB(Vector2f center, Vector2f radius) {
        this.center = center;
        this.radius = radius;
    }
    
    /**
     * If this AABB contains a specified point.
     * @param point the point to be checked.
     * @return if the point is within this AABB.
     */
    public boolean containsPoint(Vector2f point) {
        float minX = center.x - radius.x;
        float maxX = center.x + radius.x;
        
        float minY = center.y - radius.y;
        float maxY = center.y + radius.y;
        
        return (minX < point.x && point.x < maxX &&
                minY < point.y && point.y < maxY);
    }
    
    /**
     * If this AABB intersects another AABB
     * @param other the AABB to be checked.
     * @return if this AABB intersects the specified AABB
     */
    public boolean intersectsAABB(AABB other) {
        boolean a = Math.abs(center.x - other.getCenter().x) <= (radius.x + other.getRadius().x);
        boolean b = Math.abs(center.y - other.getCenter().y) <= (radius.y + other.getRadius().y);

        return a && b;
    }
    
    /**
     * Gets the center of this AABB.
     * @return the center of this AABB.
     */
    public Vector2f getCenter() {
        return center;
    }
    
    /**
     * Gets the radius of this AABB.
     * @return the radius of this AABB.
     */
    public Vector2f getRadius() {
        return radius;
    }
}
