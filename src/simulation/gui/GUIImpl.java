package simulation.gui;

import simulation.Simulation;
import simulation.universe.Universe;

public final class GUIImpl implements GUI {
    private final Simulation simulation; // use this to send start/stop signals

    public GUIImpl(Simulation simulation) {
        this.simulation = simulation;
    }

    public void iterateGraphics(Universe universe) {
        // TODO: implement
    }
}