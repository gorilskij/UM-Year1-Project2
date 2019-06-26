import simulation.SimulationImpl;

public class Main {
    public static void main(String[] args) {
        SimulationImpl simulation = new SimulationImpl(100, 60);
        //simulation.addLaunch("choo choo", "earth", 1000, 6879100);
        simulation.addLaunch("ooch ooch", "titan", 1000, 204456200);
    }
}
