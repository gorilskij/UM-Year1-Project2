import general_support.Vector;
import simulation.SimulationImpl;

public class Main {
    public static void main(String[] args) {
        SimulationImpl simulation = new SimulationImpl(100, 60);
        simulation.addLaunch("choo choo", 1000, 6879100);

//        System.out.println(new Vector(3.1e-01, -9.5e-01, 6.8e-03).angleBetween(new Vector(3.1e-01, -9.5e-01, 6.8e-03)));
    }
}
