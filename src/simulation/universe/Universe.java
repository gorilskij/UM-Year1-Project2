package simulation.universe;

import body.Exceptions.SpaceShipException;
import body.SpaceShip;
import body.interfaces.Body;
import body.interfaces.Trailing;
import controllers.Controller;
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
    void addLaunch(SpaceShip spaceShip, long time, Controller ...controllers);

    /**
     *
     * @param timeStep time step at which to iterate
     */
    void iterateCelestials(double timeStep);

    void iterateTrailers();

    /**
     *
     * @param timeStep time step at which to iterate
     */
    void iterateShips(double timeStep);

    List<Body> allBodies();

    List<SpaceShip> spaceShips();

    List<Trailing> trailingBodies();

    Body getBodyByName(String name);

    Simulation simulation();
}
