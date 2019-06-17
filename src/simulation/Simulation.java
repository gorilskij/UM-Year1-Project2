package simulation;

import simulation.interfaces.PlayPause;
import simulation.interfaces.ShipLaunched;

public interface Simulation extends PlayPause, ShipLaunched {
    /**
     *
     * @return current time
     */
    long timePassedS();

    /**
     * add launch
     */
    void addLaunch();
}
