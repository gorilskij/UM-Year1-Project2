package simulation.universe;

import body.SpaceShip;
import body.interfaces.Body;

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
}
