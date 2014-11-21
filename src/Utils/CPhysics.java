/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Entities.Entity;
import coratticca.Utils.QuadTree.AABB;
import org.jsfml.system.Vector2f;

/**
 * Handles the game's physics.
 * @author Nick
 */
public class CPhysics {
    
    /**
     * Converts bounds of two entites to axis-aligned bounding boxes and checks for intersection.
     * @param a the first entity to compare.
     * @param b the second entity to compare.
     * @return if the two entities intersect or not.
     */
    public static boolean boxCollisionTest(Entity a, Entity b) {
        
        // make new bounding boxes, a little smaller than the sprites bounding boxes
        // this stops objects from colliding before intersecting
        
        // convert bounds to aabb
        Vector2f aCenter = new Vector2f((a.getBounds().left + a.getBounds().width/2), 
                                            a.getBounds().top + a.getBounds().height/2);
        
        Vector2f aHalfSize = new Vector2f(a.getBounds().width/3, a.getBounds().height/3);
                
        Vector2f bCenter = new Vector2f((b.getBounds().left + b.getBounds().width/2), 
                                            b.getBounds().top + b.getBounds().height/2);
        
        Vector2f bHalfSize = new Vector2f(b.getBounds().width/3, b.getBounds().height/3);
        
        AABB aBounds = new AABB(aCenter, aHalfSize);
        AABB bBounds = new AABB(bCenter, bHalfSize);

        return aBounds.intersectsAABB(bBounds);
    }

    /**
     * Simulates elastic collision between two entities. 
     * @param a the first entity to simulate.
     * @param b the second entity to simulate.
     */
    public static void handleCollision(Entity a, Entity b) {
        // make a few variables to make equations easier to read
        Vector2f aPos = a.getPos();
        Vector2f bPos = b.getPos();

        Vector2f aV = a.getVelocity();
        Vector2f bV = b.getVelocity();

        float aMass = a.getSize();
        float bMass = b.getSize();

        // no mass makes no sense in collisions
        if (aMass == 0 || bMass == 0) {
            return;
        }
        
        Vector2f rv = Vector2f.sub(bV, aV);
        Vector2f norm = Vector2f.sub(bPos, aPos);
        float velAlongNormal = CVector.dot(rv, norm);
        
        // do not resolve if velocities are separating
        if(velAlongNormal > 0) {
            return;
        }

        // find unit normal vector
        Vector2f uN = CVector.normalize(norm);

        // find unit tanget vector
        Vector2f uT = new Vector2f(-uN.y, uN.x);

        // get normal and tangential components of both velocity vectors 
        // before the collision
        float aVn = CVector.dot(uN, aV);
        float aVt = CVector.dot(uT, aV);

        float bVn = CVector.dot(uN, bV);
        float bVt = CVector.dot(uT, bV);

        // get normal and tangential components of both velocity vectors 
        // after the collision
        // tangential component after collision is the same as before collision
        float aVnPrime = (aVn * (aMass - bMass) + 2f * bMass * bVn) / (aMass + bMass);

        float bVnPrime = (bVn * (bMass - aMass) + 2f * aMass * aVn) / (aMass + bMass);

        // convert normal and tangential components into vectors
        Vector2f avn = Vector2f.mul(uN, aVnPrime);
        Vector2f avt = Vector2f.mul(uT, aVt);

        Vector2f bvn = Vector2f.mul(uN, bVnPrime);
        Vector2f bvt = Vector2f.mul(uT, bVt);

        a.setVelocity(Vector2f.add(avn, avt));
        b.setVelocity(Vector2f.add(bvn, bvt));
    }
}
