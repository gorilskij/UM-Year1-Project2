package general_support;

import java.awt.*;
import java.util.Locale;

public final class Vector {
    public final double x, y, z;

    public static final Vector ZERO = new Vector(0, 0, 0);

    public Vector(double x, double y, double z) {
        // TODO: maybe remove for performance
        if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
            throw new IllegalStateException("tried to construct a vector with NaN: " + x + ", " + y + ", " + z);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector direction() {
        double mag = magnitude();
        if (mag == 0)
            throw new ArithmeticException("no direction for zero vector");
        return new Vector(
                x / mag,
                y / mag,
                z / mag
        );
    }

    public Vector times(double constant) {
        return new Vector(
                x * constant,
                y * constant,
                z * constant
        );
    }

    public Vector plus(Vector other) {
        return new Vector(
                x + other.x,
                y + other.y,
                z + other.z
        );
    }

    public Vector minus(Vector other) {
        return new Vector(
                x - other.x,
                y - other.y,
                z - other.z
        );
    }

    public Vector inverse() {
        return new Vector(-x, -y, -z);
    }

    public Vector vectorTo(Vector other) {
        assert other != null;
        return new Vector(
                other.x - x,
                other.y - y,
                other.z - z
        );
    }

    public double distanceTo(Vector other) {
        return Math.sqrt(
                Math.pow(x - other.x, 2)
                        + Math.pow(y - other.y, 2)
                        + Math.pow(z - other.z, 2)
        );
    }

    public String toStringPrecise() {
        return String.format(
                Locale.US,
                "(%f, %f, %f)",
                x, y, z
        );
    }

    public String toString() {
        return String.format(
                Locale.US,
                "(%.1e, %.1e, %.1e)",
                x, y, z
        );
    }

    public Vector averageWith(Vector other) {
        return new Vector(
                (x + other.x)/2,
                (y + other.y)/2,
                (z + other.z)/2
        );
    }

    public Point.Double toXYPoint() {
        return new Point.Double(x, y);
    }

    public Point.Double toXYPoint(double scale) {
        return new Point.Double(x * scale, y * scale);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector) {
            return (x == ((Vector) obj).x && y == ((Vector) obj).y && z == ((Vector) obj).z);
        } else {
            return false;
        }
    }

    public double dotProductWith(Vector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vector componentInDirectionOf(Vector other) {
        double dotProduct = dotProductWith(other);
        double square = Math.pow(other.magnitude(), 2);
        double magnitude = dotProduct / square;
        return other.times(magnitude);
    }
}
