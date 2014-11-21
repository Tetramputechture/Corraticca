/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import java.util.Random;
import org.jsfml.system.Vector2f;

/**
 * Various useful random functions.
 * @author Nick
 */
public class CRandom {
    
    private static final Random rand = new Random();

    /**
     * Returns a random integer between two bounds.
     * @param min the lower bound.
     * @param max the upper bound.
     * @return a random integer between min and max.
     */
    public static int randInt(int min, int max) {
        int randomNum = rand.nextInt(java.lang.Math.abs((max - min) + 1)) + min;
        return randomNum;
    }

    /**
     * Returns a random float between two bounds.
     * @param min the lower bound.
     * @param max the upper bound.
     * @return a random float between min and max.
     */
    public static float randFloat(float min, float max) {
        double randomNum = min + (max - min) * rand.nextDouble();
        return (float) randomNum;
    }
    
    /**
     * Returns a random vector between component bounds.
     * @param xLowerBound the lower bound of the x component.
     * @param xUpperBound the upper bound of the x component.
     * @param yLowerBound the lower bound of the y component.
     * @param yUpperBound the upper bound of the y component.
     * @return a random vector with components between the specified bounds.
     */
    public static Vector2f randVector(float xLowerBound, float xUpperBound,
                                      float yLowerBound, float yUpperBound) {
        return new Vector2f(randFloat(xLowerBound, xUpperBound), randFloat(yLowerBound, yUpperBound));
    }
    
    /**
     * Returns a random vector on the edge of the view.
     * @return a random vector on the edge of the game view.
     */
    public static Vector2f getRandomEdgeVector() {
        Vector2f upperBound = Vector2f.add(Camera.getView().getCenter(), Camera.getView().getSize());
        Vector2f lowerBound = Vector2f.sub(Camera.getView().getCenter(), Camera.getView().getSize());
        int tx = CRandom.randInt((int)lowerBound.x+20, (int)upperBound.x-20);
        int ty = CRandom.randInt((int)lowerBound.y+20, (int)upperBound.y-20);
        if (rand.nextDouble() < 0.5) {
            tx = (int)lowerBound.x;
        } else {
            ty = (int)lowerBound.y;
        }
        return new Vector2f(tx, ty);
    }
}
