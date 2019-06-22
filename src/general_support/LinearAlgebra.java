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

    public static double[][] addMatrixes(double[][] m1, double[][] m2) {
        assert m1.length == m2.length : "not equal column lengths";
        assert m1[0].length == m2[0].length : "not equal row lengths";
        double[][] res = new double[m1.length][m1.length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                res[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return res;
    }

    public static double[][] multiplyMatrixes(double[][] m1, double[][] m2) {
        assert m1[0].length == m2.length : "can not multiply in those dimensions";
        double[][] res = new double[m1.length][m2[0].length];
        for (int i = 0; i < m1[0].length; i++) {
            for (int j = 0; j < m2.length; j++) {
                for (int k = 0; k < m2[0].length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
}
