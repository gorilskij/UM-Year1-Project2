package simulation.universe;

import body.SpaceShip;
import body.interfaces.Body;
import simulation.Simulation;

import java.util.List;

public interface Universe {

    /**
     *
     * @param body add an object to the modeled universe
     */
    void addBody(Body body);

    /**
     *
     */
    void addLaunch(String name, double mass);

    /**
     *
     * @param timeStep time step at which to iterate
     */
    void iterateCelestials(double timeStep);

    /**
     *
     * @param timeStep time step at which to iterate
     */
    void iterateShips(double timeStep);

    /**
     *
     * print all the current data about universe
     */
    void getCurrentData();

    List<Body> allBodies();

    List<SpaceShip> spaceShips();

    Body getBodyByName(String name);

    Simulation simulation();
}
