package simulation;

import simulation.interfaces.PlayPause;
import simulation.interfaces.ShipLaunched;
import simulation.universe.Universe;

public interface Simulation extends PlayPause, ShipLaunched {
    /**
     *
     * @return current time
     */
    long timePassedS();

    /**
     * add launch
     */
    void addLaunch(String name, double mass, long time);

    void setUniverseRunnerMinFrameTime(long minFrameTimeNs);

    double timeStep();

    Universe universe();
}
