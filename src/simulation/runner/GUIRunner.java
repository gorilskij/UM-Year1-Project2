package simulation.runner;

import simulation.PlayPause;
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

        play();
    }

    @Override
    public void doFrame() {
        gui.iterateGraphics(universe);
    }

    private void setFPS(int fps) {
        setMinFrameTime((long) (1e9 * (1.0 / fps)));
    }

    public void play() {
        setFPS(50);
        gui.play();
    }

    public void pause() {
        setFPS(10);
        gui.pause();
    }
}