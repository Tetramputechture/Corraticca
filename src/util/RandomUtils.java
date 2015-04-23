package coratticca.util;

import coratticca.camera.Camera;
import coratticca.vector.Vector2;
import java.util.Random;

/**
 * Various useful random functions.
 *
 * @author Nick
 */
public class RandomUtils {

    private final Random rand;

    public RandomUtils() {
        rand = new Random();
    }

    /**
     * Returns a random integer between two bounds.
     *
     * @param min the lower bound.
     * @param max the upper bound.
     * @return a random integer between min and max.
     */
    public int randInt(int min, int max) {
        int randomNum = rand.nextInt(java.lang.Math.abs((max - min) + 1)) + min;
        return randomNum;
    }

    /**
     * Returns a random float between two bounds.
     *
     * @param min the lower bound.
     * @param max the upper bound.
     * @return a random float between min and max.
     */
    public float randFloat(float min, float max) {
        double randomNum = min + (max - min) * rand.nextDouble();
        return (float) randomNum;
    }

    /**
     * Returns a random vector between component bounds.
     *
     * @param xLowerBound the lower bound of the x component.
     * @param xUpperBound the upper bound of the x component.
     * @param yLowerBound the lower bound of the y component.
     * @param yUpperBound the upper bound of the y component.
     * @return a random vector with components between the specified bounds.
     */
    public Vector2 randVector(float xLowerBound, float xUpperBound,
            float yLowerBound, float yUpperBound) {
        return new Vector2(randFloat(xLowerBound, xUpperBound), randFloat(yLowerBound, yUpperBound));
    }

    /**
     * Returns a random vector on the edge of the view.
     *
     * @param c the Camera that specifies the view.
     * @return a random vector on the edge of the game view.
     */
    public Vector2 getRandomEdgeVector(Camera c) {
        Vector2 cCenter = new Vector2(c.getView().getCenter());
        Vector2 cSize = new Vector2(c.getView().getSize());

        Vector2 upperBound = cCenter.add(cSize);
        Vector2 lowerBound = cCenter.sub(cSize);

        int tx = randInt((int) lowerBound.x + 20, (int) upperBound.x - 20);
        int ty = randInt((int) lowerBound.y + 20, (int) upperBound.y - 20);

        if (rand.nextDouble() < 0.5) {
            tx = (int) lowerBound.x;
        } else {
            ty = (int) lowerBound.y;
        }

        return new Vector2(tx, ty);
    }
}
