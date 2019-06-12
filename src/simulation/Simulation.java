package simulation;

import body.interfaces.Body;
import simulation.universe.BaseUniverse;
import simulation.universe.Universe;


public interface Simulation {
    /**
     * initialization
     * @param bodies celestial objects of the universe
     */
    void init(Body ...bodies);

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


}

class Environment implements Simulation {

    public Universe universe = null;
    private double timeStep;


    Environment(double timeStep){
        this.timeStep = timeStep;
    }

    @Override
    public void init(Body... bodies) {
        this.universe = new BaseUniverse();
        for (Body body : bodies)
            universe.addBody(body);
    }

    @Override
    public void iterate(Universe universe) {
        // TODO: implement
    }

    @Override
    public double timeStep() {
        return this.timeStep;
    }
}
