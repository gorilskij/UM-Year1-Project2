package simulation.runner;

import simulation.Timer;
import simulation.universe.Universe;

public class UniverseRunner extends BaseRunner {
    private final Universe universe;
    private double timeStep;

    private final Timer timer;

    public void setTimeStep(double timeStep) {
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
        universe.iterateTrailers();
        timer.iterate(timeStep);
    }
}
