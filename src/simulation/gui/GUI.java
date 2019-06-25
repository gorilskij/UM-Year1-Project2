package simulation.gui;

import simulation.Simulation;
import simulation.interfaces.PlayPause;
import simulation.interfaces.ShipLaunched;
import simulation.universe.Universe;

public interface GUI extends PlayPause, ShipLaunched {
    void iterateGraphics(Universe universe);
    void setSimulation(Simulation simulation);
}
