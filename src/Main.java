import body.Planet;
import body.SpaceShip;
import controllers.LaunchController;
import controllers.PIDController;
import simulation.SimulationImpl;
import simulation.universe.Universe;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SimulationImpl simulation = new SimulationImpl(100, 60);
        Universe universe = simulation.universe();

        SpaceShip choochoo = new SpaceShip("choo choo", Color.WHITE, 1000, simulation);

        LaunchController launch = new LaunchController(
                universe,
                choochoo,
                (Planet) universe.getBodyByName("earth")
        );
        PIDController pid1 = new PIDController(
                universe,
                choochoo,
                universe.getBodyByName("titan"),
                1E-11, 5E-17, 1E-4, 7E10
        );
        PIDController pid2 = new PIDController(
                universe,
                choochoo,
                universe.getBodyByName("titan"),
                pid1.getErrors(),
                4E-14, 0, 0.0005, 0
        );

        simulation.addLaunch(choochoo, 6879100,
                launch,
                pid1,
                pid2
        );
    }
}
