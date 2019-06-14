package simulation;

import body.interfaces.Body;
import simulation.universe.Universe;

import java.util.List;


public interface Simulation {
    /**
     * initialization
     * @param bodies celestial objects of the universe
     */
    void addBodies(Body ...bodies);

    /**
     *
     * @return current time
     */
    long currentMillis();

    /**
     *
     * @return how many seconds have passed
     */
    long secondPassed();

    /**
     * physics processes go here
     */
    void iterate();

    /**
     * if we need to use change in time
     * @return time step
     */
    double timeStep();
    void setTimeStep(double timeStep);

    /**
     * print all the current data for the universe
     */
    void getData();

    void graphicsStart();
    void graphicsStop();
}