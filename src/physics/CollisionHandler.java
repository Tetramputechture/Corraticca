package coratticca.physics;

import coratticca.entity.gameentity.GameEntity;
import coratticca.vector.Vector2;

/**
 * Handles the game's physics.
 *
 * @author Nick
 */
public class CollisionHandler {

    /**
     * Converts bounds of two entites to axis-aligned bounding boxes and checks
     * for intersection.
     *
     * @param a the first entity to epsilonEquals.
     * @param b the second entity to epsilonEquals.
     * @return if the two entities intersect or not.
     */
    public boolean boxCollisionTest(GameEntity a, GameEntity b) {
        if (a == null || b == null) {
            return false;
        }
        return a.getBounds().intersectsAABB(b.getBounds());
    }

    /**
     * Simulates elastic collision between two entities.
     *
     * @param a the first entity to simulate.
     * @param b the second entity to simulate.
     */
    public void handleCollision(GameEntity a, GameEntity b) {
        
        if (a == null || b == null) {
            return;
        }

        // make a few variables to make equations easier to read
        Vector2 aPos = a.getPosition();
        Vector2 bPos = b.getPosition();

        Vector2 aV = a.getVelocity();
        Vector2 bV = b.getVelocity();

        float aMass = a.getMass();
        float bMass = b.getMass();

        // no mass makes no sense in collisions
        if (aMass == 0 || bMass == 0) {
            return;
        }

        Vector2 rv = bV.sub(aV);
        Vector2 norm = bPos.sub(aPos);
        float velAlongNormal = rv.dot(norm);

        // do not resolve if velocities are separating
        if (velAlongNormal > 0.001) {
            return;
        }

        // find unit normal vector
        Vector2 uN = norm.normalize();

        // find unit tanget vector
        Vector2 uT = new Vector2(-uN.y, uN.x);

        // get normal and tangential components of both velocity vectors 
        // before the collision
        float aVn = uN.dot(aV);
        float aVt = uT.dot(aV);

        float bVn = uN.dot(bV);
        float bVt = uT.dot(bV);

        // get normal and tangential components of both velocity vectors 
        // after the collision
        // tangential component after collision is the same as before collision
        float aVnPrime = (aVn * (aMass - bMass) + 2f * bMass * bVn) / (aMass + bMass);

        float bVnPrime = (bVn * (bMass - aMass) + 2f * aMass * aVn) / (aMass + bMass);

        // convert normal and tangential components into vectors
        Vector2 avn = uN.scl(aVnPrime);
        Vector2 avt = uT.scl(aVt);

        Vector2 bvn = uN.scl(bVnPrime);
        Vector2 bvt = uT.scl(bVt);

        // set entity velocites
        a.setVelocity(avn.add(avt));
        b.setVelocity(bvn.add(bvt));
    }
}
