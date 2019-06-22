import general_support.LinearAlgebra;
import simulation.SimulationImpl;

public class Main {
    public static void main(String[] args) {
        SimulationImpl simulation = new SimulationImpl(10);
        simulation.addLaunch("choo choo", 1000);
//        double[][] m1 = new double[][] {{1, 2, 3}, {4, 5, 6}};
//        double[][] m2 = new double[][] {{3, 4, 5}, {6, 7, 8}};
//        double[][] res = LinearAlgebra.addMatrices(m1, m2);
//        System.out.println(LinearAlgebra.toString(res));
    }
}
