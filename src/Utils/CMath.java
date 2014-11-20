/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Entities.Entity;
import java.util.Random;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public class CMath {

    private static final Random rand = new Random();

    public static int randInt(int min, int max) {
        int randomNum = rand.nextInt(java.lang.Math.abs((max - min) + 1)) + min;
        return randomNum;
    }

    public static float randFloat(int min, int max) {
        double randomNum = min + (max - min) * rand.nextDouble();
        return (float) randomNum;
    }

    public static float length(Vector2f v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public static float dot(Vector2f a, Vector2f b) {
        return a.x * b.x + a.y * b.y;
    }

    public static Vector2f normalize(Vector2f v) {
        float mag = length(v);
        if (mag > 0) {
            return Vector2f.div(v, mag);
        } else {
            return Vector2f.ZERO;
        }
    }

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
        float velAlongNormal = CMath.dot(rv, norm);
        
        // Do not resolve if velocities are separating
        if(velAlongNormal > 0) {
            return;
        }

        // find unit normal vector
        Vector2f uN = normalize(norm);

        // find unit tanget vector
        Vector2f uT = new Vector2f(-uN.y, uN.x);

        // get normal and tangential components of both velocity vectors 
        // before the collision
        float aVn = dot(uN, aV);
        float aVt = dot(uT, aV);

        float bVn = dot(uN, bV);
        float bVt = dot(uT, bV);

        // get normal and tangential components of both velocity vectors 
        // after the collision
        float aVnPrime = (aVn * (aMass - bMass) + 2f * bMass * bVn) / (aMass + bMass);
        float aVtPrime = aVt;

        float bVnPrime = (bVn * (bMass - aMass) + 2f * aMass * aVn) / (aMass + bMass);
        float bVtPrime = bVt;

        // convert normal and tangential components into vectors
        Vector2f avn = Vector2f.mul(uN, aVnPrime);
        Vector2f avt = Vector2f.mul(uT, aVtPrime);

        Vector2f bvn = Vector2f.mul(uN, bVnPrime);
        Vector2f bvt = Vector2f.mul(uT, bVtPrime);

        a.setVelocity(Vector2f.add(avn, avt));
        b.setVelocity(Vector2f.add(bvn, bvt));
    }
}
