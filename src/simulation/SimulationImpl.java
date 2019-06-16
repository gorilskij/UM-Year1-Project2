package simulation;

import simulation.gui.GUI;
import simulation.gui.GUIImpl;
import simulation.runner.GUIRunner;
import simulation.runner.UniverseRunner;
import simulation.universe.UniverseImpl;
import simulation.universe.Universe;

public final class SimulationImpl implements Simulation {
    private final Timer timer;
    private int timeStep;

    public long timePassedS() {
        return timer.timePassedS();
    }

    private final UniverseRunner universeRunner;

    private final GUI gui;
    private final GUIRunner guiRunner;

    public SimulationImpl(int initialTimeStep) {
        timer = new Timer();
        timeStep = initialTimeStep;

        Universe universe = UniverseImpl.newSolarSystem(this);
        gui = new GUIImpl(this, universe);

        universeRunner = new UniverseRunner(timer, universe, timeStep);
        guiRunner = new GUIRunner(gui, universe);

        pause();
    }

    public void play() {
        universeRunner.play();
        guiRunner.play();
    }

    public void pause() {
        universeRunner.pause();
        guiRunner.pause();
    }

    public void shipLaunched() {
        gui.shipLaunched();
    }
}