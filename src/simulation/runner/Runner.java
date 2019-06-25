package simulation.runner;

import body.Exceptions.SpaceShipException;
import simulation.interfaces.PlayPause;

public interface Runner extends PlayPause {
    void restartIfRunning();
    void setMinFrameTime(long minFrameTimeNs);

    void doFrame();
}
