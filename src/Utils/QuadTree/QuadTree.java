/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils.QuadTree;

import java.util.ArrayList;
import java.util.List;
import org.jsfml.system.Vector2f;

/**
 * A Quad Tree class to partition the game screen by recursively subdividing it
 * into four quadrants.
 *
 * @author Nick
 */
public class QuadTree {

    // how many elements can be stored in this quad tree node
    private final int QT_NODE_CAPACITY = 4;

    // axis-aligned bounding box stored as a center with half-dimensions
    private final AABB boundary;

    // points in this quad tree node
    private final List<Vector2f> points = new ArrayList<>(QT_NODE_CAPACITY);

    // children
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    /**
     * Constructs a QuadTree with the specified boundary.
     *
     * @param boundary the boundary of the QuadTree.
     */
    public QuadTree(AABB boundary) {
        this.boundary = boundary;
    }

    /**
     * Inserts a point into this QuadTree.
     *
     * @param p the point to be inserted.
     * @return true if the point was inserted, false if not.
     */
    public boolean insert(Vector2f p) {
        // ignore objects that do not belong in this quad tree
        if (!boundary.containsPoint(p)) {
            return false;
        }

        // if there is space in this quad tree, add the object here
        if (points.size() < QT_NODE_CAPACITY) {
            points.add(p);
            return true;
        }

        // if there is no space, subdivide and add the point to whichever node
        // will accept it
        if (northWest == null) {
            subDivide();
        }

        if (northWest.insert(p)) {
            return true;
        }
        if (northEast.insert(p)) {
            return true;
        }
        if (southWest.insert(p)) {
            return true;
        }
        if (southEast.insert(p)) {
            return true;
        }

        return false;
    }

    /**
     * creates four children that fully divide this QuadTree.
     */
    public void subDivide() {
        float cX = boundary.getCenter().x;
        float cY = boundary.getCenter().y;

        float rX = boundary.getRadius().x;
        float rY = boundary.getRadius().y;

        Vector2f quarterR = Vector2f.div(boundary.getRadius(), 4);

        AABB topLeft = new AABB(new Vector2f(cX - rX, cY - rY),
                quarterR);
        AABB topRight = new AABB(new Vector2f(cX + rX, cY - rY),
                quarterR);
        AABB bottomLeft = new AABB(new Vector2f(cX - rX, cY + rY),
                quarterR);
        AABB bottomRight = new AABB(new Vector2f(cX + rX, cY + rY),
                quarterR);

        northWest = new QuadTree(topLeft);
        northEast = new QuadTree(topRight);
        southWest = new QuadTree(bottomLeft);
        southEast = new QuadTree(bottomRight);
    }

    /**
     * Returns in the points in the specified range of this QuadTree.
     * @param range the bounding box to be checked.
     * @return a List of points within the specified range.
     */
    public List<Vector2f> queryRange(AABB range) {

        // prepare result list
        List<Vector2f> pointsInRange = new ArrayList<>();

        // abort if range does not intersect this quad 
        if (!boundary.intersectsAABB(range)) {
            return pointsInRange; // empty list
        }

        // check objects at this quad level
        for (Vector2f p : points) {
            if (range.containsPoint(p)) {
                pointsInRange.add(p);
            }
        }

        // terminate if no children
        if (northWest == null) {
            return pointsInRange;
        }

        // otherwise, add points from the children
        pointsInRange.addAll(northWest.queryRange(range));
        pointsInRange.addAll(northEast.queryRange(range));
        pointsInRange.addAll(southWest.queryRange(range));
        pointsInRange.addAll(southEast.queryRange(range));

        return pointsInRange;
    }
}
