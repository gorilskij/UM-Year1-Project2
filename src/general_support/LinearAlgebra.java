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
        double radAngle = Math.toRadians(angle);
        return new double[][] {
                {1, 0, 0},
                {0, Math.cos(radAngle), -Math.sin(radAngle)},
                {0, Math.sin(radAngle), Math.cos(radAngle)}
        };
    }

    // instantiate Y rotating linear transformation
    public static double[][] instRotTrY(double angle) {
        double radAngle = Math.toRadians(angle);
        return new double[][] {
                {Math.cos(radAngle), 0, Math.sin(radAngle)},
                {0, 1, 0},
                {-Math.sin(radAngle), 0, Math.cos(radAngle)}
        };
    }

    // instantiate Z rotating linear transformation
    public static double[][] instRotTrZ(double angle) {
        double radAngle = Math.toRadians(angle);
        return new double[][] {
                {Math.cos(radAngle), -Math.sin(radAngle), 0},
                {Math.sin(radAngle), Math.cos(radAngle), 0},
                {0, 0, 1}
        };
    }

    public static double[][] addMatrices(double[][] m1, double[][] m2) {
        assert m1.length == m2.length : "not equal column lengths";
        assert m1[0].length == m2[0].length : "not equal2 row lengths";
        double[][] res = new double[m1.length][m1[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                res[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return res;
    }

    public static double[][] multiplyMatrices(double[][] m1, double[][] m2) {
        assert m1[0].length == m2.length : "can not multiply in those dimensions";
        double[][] res = new double[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m1[0].length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

    public static Vector multiplyMatrixByVector(double[][] m1, Vector v1) {
        assert v1.length == m1[0].length : "can not multiply in those dimensions";
        double[] v = v1.toArray();
        double x = 0;
        double y = 0;
        double z = 0;
        return new Vector(x, y, z);
    }

    public static String toString(double[][] m) {
        String res = "";
        for (double[] row : m) {
            for (double col : row) {
                res += col + " ";
            }
            res += "\n";

        }
        return res;
    }

    public static double[][] multiplyMatrixByConstant(double[][] m, double c) {
        double[][] res = m.clone();
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] *= c;
            }
        }
        return res;
    }

    public static double[][] matrixForRotation(Vector v1, Vector v2, double angle) {
        double[][] res = new double[3][3];
        Vector crossProduct = v1.cross(v2);
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1.0;
        }
        double cos = Math.cos(angle);
        double[][] crossMatrix = new double[][] {
                {0.0, -crossProduct.z, crossProduct.y},
                {crossProduct.z, 0.0, - crossProduct.x},
                {-crossProduct.y, crossProduct.x, 0.0}
        };
        res = addMatrices(res, addMatrices(crossMatrix, multiplyMatrixByConstant(multiplyMatrices(crossMatrix, crossMatrix), 1/(1 +cos))));
        return res;
    }


}
