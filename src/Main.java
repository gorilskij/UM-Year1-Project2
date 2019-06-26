import simulation.SimulationImpl;

public class Main {
    public static void main(String[] args) {
        SimulationImpl simulation = new SimulationImpl(100, 60);
        //simulation.addLaunch("Поехали!", 2000, 3000000);
        //simulation.addLaunch("Поехали!", 3000, 0);
        simulation.addLaunch("Поехали!", 1000, 66000000);
    }
}
