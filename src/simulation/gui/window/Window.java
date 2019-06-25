package simulation.gui.window;

import body.interfaces.Body;
import simulation.Simulation;
import simulation.interfaces.PlayPause;
import simulation.interfaces.ShipLaunched;

public interface Window extends PlayPause, ShipLaunched {
    void paint();
    void setCenterBody(Body body);
    void setSimulation(Simulation simulation);
}
