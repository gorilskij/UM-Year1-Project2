package general_support;

public class LinearAlgebra {

    public static Vector rotation(Vector v, double[][] m) {
        double[] var = {v.x, v.y, v.z};
        double[] res = new double[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res[i] += m[i][j] * var[j];
            }
        }
        return new Vector(res[0], res[1], res[2]);
    }

    // instantiate X rotating linear transformation
    public static double[][] instRotTrX(double angle) {
        // angle in degrees
        angle = Math.toRadians(angle);
        return new double[][] {
                {1, 0, 0},
                {0, Math.cos(angle), -Math.sin(angle)},
                {0, Math.sin(angle), Math.cos(angle)}
        };
    }

    // instantiate Y rotating linear transformation
    public static double[][] instRotTrY(double angle) {
        angle = Math.toRadians(angle);
        return new double[][] {
                {Math.cos(angle), 0, Math.sin(angle)},
                {0, 1, 0},
                {-Math.sin(angle), 0, Math.cos(angle)}
        };
    }

    // instantiate Z rotating linear transformation
    public static double[][] instRotTrZ(double angle) {
        angle = Math.toRadians(angle);
        return new double[][] {
                {Math.cos(angle), -Math.sin(angle), 0},
                {Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 1}
        };
    }
}
