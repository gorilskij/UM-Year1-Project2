package simulation;

import simulation.universe.Universe;

public interface Simulation extends PlayPause {
    /**
     *
     * @return current time
     */
    long timePassedS();

    Universe universe();
}
