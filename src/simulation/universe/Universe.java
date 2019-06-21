package simulation.universe;

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
     */
    void addLaunch(String name, double mass);

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

    List<Body> allBodies();

    Body getBodyByName(String name);
}
