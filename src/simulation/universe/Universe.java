package simulation.universe;

import body.interfaces.Body;

public interface Universe {

    /**
     *
     * @param body add an object to the modeled universe
     */
    void addBody(Body body);

    /**
     *
     */
    void addLaunch();

    /**
     *
     * @param timeStep time step at which to iterate
     */
    void iteratePhysics(int timeStep);

    /**
     *
     * print all the current data about universe
     */
    void getCurrentData();
}
