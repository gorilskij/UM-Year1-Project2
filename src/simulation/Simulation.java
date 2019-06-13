package simulation;

import body.interfaces.Body;
import simulation.universe.Universe;


public interface Simulation {
    /**
     * initialization
     * @param bodies celestial objects of the universe
     */
    void addBodies(Body ...bodies);

    /**
     * physics processes go here
     * @param universe in our case solar-system
     */
    void iterate(Universe universe);

    /**
     * if we need to use change in time
     * @return time step
     */
    double timeStep();
    void setTimeStep(double timeStep);

    void graphicsStart();
    void graphicsStop();
}