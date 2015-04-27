package coratticca.vector;

import coratticca.util.NumberUtils;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * A class that manages 2D vectors of float values.
 *
 * @author Nick
 */
public class Vector2 {

    public final float x;

    public final float y;

    public static final Vector2 Zero = new Vector2(0, 0);
    
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2(Vector2i v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2f toVector2f() {
        return new Vector2f(x, y);
    }

    public Vector2i toVector2i() {
        return new Vector2i((int) x, (int) y);
    }

    public Vector2 add(float x, float y) {
        return new Vector2(this.x + x, this.y + y);
    }

    public Vector2 add(Vector2 v) {
        return add(v.x, v.y);
    }

    public Vector2 sub(float x, float y) {
        return add(-x, -y);
    }

    public Vector2 sub(Vector2 v) {
        return sub(v.x, v.y);
    }

    public float dot(float x, float y) {
        return this.x * x + this.y * y;
    }

    public float dot(Vector2 v) {
        return dot(v.x, v.y);
    }

    public Vector2 scl(float scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public Vector2 div(float divisor) {
        if (divisor == 0) {
            return Zero;
        }
        return scl(1 / divisor);
    }

    public Vector2 sclAdd(Vector2 v, float scalar) {
        return add(v.scl(scalar));
    }

    public float len() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 normalize() {
        return div(len());
    }

    public Vector2 setLength(float len) {
        return normalize().scl(len);
    }

    public Vector2 clamp(float min, float max) {
        float len = len();
        if (len > max) {
            return setLength(max);
        } else if (len < min) {
            return setLength(min);
        }
        // length is already between bounds
        return this;
    }

    public float dst(float x, float y) {
        // if two vectors are equal, distance is 0
        if (Vector2.this.epsilonEquals(x, y, 0.00000001f)) {
            return 0;
        }
        return sub(x, y).len();
    }

    public float dst(Vector2 v) {
        return dst(v.x, v.y);
    }

    public float angle() {
        return (float) Math.atan2(y, x);
    }

    public float angle(Vector2 v) {
        if (len() == 0 || v.len() == 0) {
            return 0;
        }
        return (float) Math.acos(dot(v) / (len() * v.len()));
    }
    
    public boolean epsilonEquals(float x, float y, float epsilon) {
        return Math.abs(this.x-x) < epsilon && Math.abs(this.y-y) < epsilon;
    }
    
    public boolean epsilonEquals(Vector2 v, float epsilon) {
        return epsilonEquals(v.x, v.y, epsilon);
    }

    public boolean isZero() {
        return epsilonEquals(Zero, 0.00000001f);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Vector: <");
        sb.append(NumberUtils.roundTo3(x));
        sb.append(", ");
        sb.append(NumberUtils.roundTo3(y));
        sb.append(">");
        return sb.toString();
    }

}
