package simulation;

import simulation.gui.GUI;
import simulation.gui.GUIImpl;
import simulation.runner.GUIRunner;
import simulation.runner.UniverseRunner;
import simulation.universe.UniverseImpl;
import simulation.universe.Universe;

public final class SimulationImpl implements Simulation {
    private final TimeHandler timeHandler;

    public long timePassedS() {
        return timeHandler.timePassedS();
    }

    private final Universe universe;
    private final GUI gui;

    private final UniverseRunner universeRunner;
    private final GUIRunner guiRunner;

    public SimulationImpl(int initialTimeStep) {
        timeHandler = new TimeHandler(initialTimeStep);

        universe = UniverseImpl.newSolarSystem();
        gui = new GUIImpl(this);

        universeRunner = new UniverseRunner(universe, timeHandler.timeStep());
        guiRunner = new GUIRunner(gui, universe);

        guiRunner.start();
    }

    public void startPhysics() {
        universeRunner.start();
        guiRunner.setRunningFPS();
    }

    public void stopPhysics() {
        universeRunner.stop();
        guiRunner.setRestingFPS();
    }
}