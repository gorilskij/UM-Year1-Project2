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

    private final UniverseRunner universeRunner;
    private final GUIRunner guiRunner;

    public SimulationImpl(int initialTimeStep) {
        timeHandler = new TimeHandler(initialTimeStep) {
            @Override
            public void setTimeStep(int timeStep) {
                super.setTimeStep(timeStep);
                universeRunner.setTimeStep(timeStep);
            }
        };

        universe = UniverseImpl.newSolarSystem();
        GUI gui = new GUIImpl(this, universe);

        universeRunner = new UniverseRunner(universe, timeHandler.timeStep());
        guiRunner = new GUIRunner(gui, universe);

        guiRunner.play();
    }

    public void play() {
        universeRunner.play();
        guiRunner.play();
    }

    public void pause() {
        universeRunner.pause();
        guiRunner.pause();
    }
}