package simulation;

import body.interfaces.Body;
import simulation.universe.Universe;


public interface Simulation {
    /**
     *
     * @param bodies celestial objects of the universe
     */
    void init(Body ...bodies);

    /**
     *
     * @param universe in our case solar-system
     */
    void iterate(Universe universe);

    /**
     *
     * @return current timeline value
     */
    double time();

    /**
     * if we need to use change in time
     * @return time step
     */
    double timeStep();


}

class SimImplementation {

}
