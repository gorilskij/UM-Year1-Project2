package simulation;

import body.SpaceShip;
import controllers.Controller;
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
    void addLaunch(SpaceShip spaceShip, long time, Controller ...controllers);

    void setUniverseRunnerMinFrameTime(long minFrameTimeNs);

    double timeStep();

    Universe universe();
}
