import simulation.SimulationImpl;

public class Main {
    public static void main(String[] args) {
        SimulationImpl simulation = new SimulationImpl(1);
        simulation.addLaunch("choo choo", 1000);
    }
}
