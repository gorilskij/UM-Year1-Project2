package simulation.gui;

import simulation.Simulation;
import simulation.gui.window.Window;
import simulation.gui.window.WindowImpl;
import simulation.universe.Universe;

public final class GUIImpl implements GUI {
    private final Window window;

    public GUIImpl(Simulation simulation) {
        window = new WindowImpl(simulation);
    }

    public void iterateGraphics(Universe universe) {
        System.out.println("iterate graphics");
    }

    public void play() {
        window.play();
    }

    public void pause() {
        window.pause();
    }
}
