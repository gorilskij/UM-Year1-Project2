package simulation.runner;

import simulation.universe.Universe;

public class UniverseRunner extends BaseRunner {
    private final Universe universe;
    private double timeStep;

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

    public UniverseRunner(Universe universe, double timeStep) {
        this.universe = universe;
        this.timeStep = timeStep;
    }

    @Override
    public void doFrame() {
        universe.iteratePhysics(timeStep);
    }
}
