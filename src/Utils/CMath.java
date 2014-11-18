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
        return (float)randomNum;
    }
    
    public static float dot(Vector2f a, Vector2f b) {
        return a.x*b.x + a.y*b.y;
    }
    
    public static boolean intersect(FloatRect a, FloatRect b) {
        return !(a.left <= b.left + b.width &&
                a.left + a.width >= b.left &&
                a.top <= b.top + b.height &&
                a.top + a.height >= b.top);
    }
    
    public static void handleElasticCollisions(Entity a, Entity b) {
        // make a few variables to make equations easier to read
        Vector2f aPos = a.getPos();
        Vector2f bPos = b.getPos();
        
        Vector2f aV = a.getVelocity();
        Vector2f bV = b.getVelocity();
        
        float aMass = a.getSize();
        float bMass = b.getSize();
        
        // find unit normal vector
        Vector2f uN = Vector2f.sub(bPos, aPos);
        float normMag = (float)Math.sqrt(uN.x*uN.x + uN.y*uN.y);
        if (normMag > 0) {
            uN = Vector2f.div(uN, normMag);
        }
        
        // find unit tanget vector
        Vector2f uT = new Vector2f(-uN.y, uN.x);
        
        // get normal and tangential components of both velocity vectors 
        // before the collision
        float aVn = CMath.dot(uN, aV);
        float aVt = CMath.dot(uT, aV);
        
        float bVn = CMath.dot(uN, bV);
        float bVt = CMath.dot(uT, bV);
        
        // get normal and tangential components of both velocity vectors 
        // after the collision
        float aVN = (aVn*(aMass - bMass) + 2*bMass*bVn)/(aMass + bMass);
        float aVT = aVt;
        
        float bVN = (bVn*(bMass - aMass) + 2*aMass*aVn)/(aMass + bMass);
        float bVT = bVt;
        
        // convert normal and tangential components into vectors
        Vector2f avn = Vector2f.mul(uN, aVN);
        Vector2f avt = Vector2f.mul(uT, aVT);
        
        Vector2f bvn = Vector2f.mul(uN, bVN);
        Vector2f bvt = Vector2f.mul(uN, bVT);
        
        a.setVelocity(Vector2f.add(avn, avt));
        b.setVelocity(Vector2f.add(bvn, bvt));
    }
}
