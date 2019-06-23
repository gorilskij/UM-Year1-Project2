
import general_support.LinearAlgebra;
import general_support.Vector;
import simulation.SimulationImpl;

public class Main {
    public static void main(String[] args) {
//        SimulationImpl simulation = new SimulationImpl(10, 60);
//        simulation.addLaunch("choo choo", 1000);
        Vector v1 = new Vector(1, 0, 0);
        Vector v2 = new Vector(0, 1, 0);
        double angle = v1.angleBetween(v2);
        double angleDeg = Math.toDegrees(angle);
        Vector res = new Vector(v1.x, v1.y, v1.z);
        for (int i = 0; i < angleDeg; i++) {
            res = LinearAlgebra.rotateTo(res, v2, angleDeg/90);
            System.out.println(res.angleBetween(v2));
        }
        System.out.println(v1.toString() + "v1");
        System.out.println(v2.toString() + "v2");
        System.out.println(res.toString() + "res");
    }
}
