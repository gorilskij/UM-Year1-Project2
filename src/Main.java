import simulation.SimulationImpl;

public class Main {
    public static void main(String[] args) {
        SimulationImpl simulation = new SimulationImpl(100, 60);
        simulation.addLaunch("choo choo", 1000, 6879100);
    }
}
