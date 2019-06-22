import static general_support.LinearAlgebra.*;

import general_support.LinearAlgebra;
import general_support.Vector;
import simulation.SimulationImpl;

import javax.sound.sampled.Line;

public class Main {
    public static void main(String[] args) {
//        SimulationImpl simulation = new SimulationImpl(10, 60);
//        simulation.addLaunch("choo choo", 1000);
        Vector v1 = new Vector(0, 0, 1);
        Vector v2 = new Vector(0, 1, 0);
        double angle = v1.angleBetween(v2);
        System.out.println(v1.toString());
        System.out.println(v2.toString());
        double[][] rotationMatrix = matrixForRotation(v1, v2, angle);
        v1 = multiplyMatrixByVector(rotationMatrix, v1);
        System.out.println(v1.toString());
    }
}
