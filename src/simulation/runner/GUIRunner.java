package simulation.runner;

import simulation.gui.GUI;
import simulation.universe.Universe;

public class GUIRunner extends BaseRunner {
    private final GUI gui;
    private final Universe universe;

    public GUIRunner(GUI gui, Universe universe) {
        // every 1/30th of a second
        super(0); // 0 is a placeholder

        this.gui = gui;
        this.universe = universe;
    }

    @Override
    public void doFrame() {
        gui.iterateGraphics(universe);
    }

    @Override
    public void play() {
        super.play();
        gui.play();
    }

    @Override
    public void pause() {
        super.pause();
        gui.pause();
    }
}