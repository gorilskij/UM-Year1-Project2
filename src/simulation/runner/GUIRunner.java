package simulation.runner;

import simulation.gui.GUI;
import simulation.universe.Universe;

public class GUIRunner extends BaseRunner {
    private final GUI gui;
    private final Universe universe;

    public GUIRunner(int fps, GUI gui, Universe universe) {
        super((long) (1e9 / fps));

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