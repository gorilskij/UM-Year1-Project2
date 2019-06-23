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
    void addLaunch(String name, double mass, long time);

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

    List<Body> allBodies();

    List<SpaceShip> spaceShips();

    Body getBodyByName(String name);

    Simulation simulation();
}
