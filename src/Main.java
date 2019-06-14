import simulation.Simulation;
import simulation.SimulationImpl;

public class Main {
    public static void main(String[] args) {
        double timeStep = 10;
        Simulation simulation = new SimulationImpl(timeStep);
        simulation.getData();
    }
}