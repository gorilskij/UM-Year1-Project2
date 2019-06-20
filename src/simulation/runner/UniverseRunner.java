package simulation.runner;

import simulation.Timer;
import simulation.universe.Universe;

public class UniverseRunner extends BaseRunner {
    private final Universe universe;
    private int timeStep;

    private final Timer timer;

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public UniverseRunner(Timer timer, Universe universe, int timeStep) {
        super(0);
        this.universe = universe;
        this.timeStep = timeStep;

        this.timer = timer;
    }

    @Override
    public void doFrame() {
        universe.iterateCelestials(timeStep);
        universe.iterateShips(timeStep);
        timer.iterate(timeStep);
    }
}
