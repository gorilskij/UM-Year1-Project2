package simulation.runner;

import simulation.gui.GUI;

public class GUIRunner extends BaseRunner {
    private final GUI gui;

    public GUIRunner(GUI gui) {
        // every 1/30th of a second
        super((long) ((1.0 / 30) * 1e9));
        this.gui = gui;
    }

    @Override
    public void doFrame() {
        gui.iterateGraphics();
    }
}
