package simulation.runner;

import simulation.universe.Universe;

public class UniverseRunner extends BaseRunner {
    private final Universe universe;
    private int timeStep;

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public UniverseRunner(Universe universe, int timeStep) {
        super(0);
        this.universe = universe;
        this.timeStep = timeStep;
    }

    @Override
    public void doFrame() {
        universe.iteratePhysics(timeStep);
    }
}
