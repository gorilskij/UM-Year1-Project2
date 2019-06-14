package simulation.runner;

import simulation.gui.GUI;
import simulation.universe.Universe;

public class GUIRunner extends BaseRunner {
    private final GUI gui;
    private final Universe universe;

    public GUIRunner(GUI gui, Universe universe) {
        // every 1/30th of a second
        super((long) ((1.0 / 30) * 1e9));
        this.gui = gui;
        this.universe = universe;
    }

    @Override
    public void doFrame() {
        gui.iterateGraphics(universe);
    }
}
