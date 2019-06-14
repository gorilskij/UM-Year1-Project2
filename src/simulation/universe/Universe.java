package simulation.universe;

import body.SpaceShip;
import body.interfaces.Body;

import java.util.List;

public interface Universe {

    /**
     *
     * @param body add an object to the modeled universe
     */
    void addBody(Body body);

    /**
     *
     * @param ship space ship to launch
     */
    void addLaunch(SpaceShip ship);

    /**
     *
     * @param timeStep time step at which to iterate
     */
    void iteratePhysics(double timeStep);

    /**
     *
     * @return return list of current celestial objects in universe
     */
    List<Body> bodies();
}
