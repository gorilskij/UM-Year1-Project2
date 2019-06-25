package simulation;

import body.SpaceShip;
import controllers.Controller;
import simulation.gui.GUI;
import simulation.gui.GUIImpl;
import simulation.runner.GUIRunner;
import simulation.runner.UniverseRunner;
import simulation.universe.Universe;
import simulation.universe.UniverseImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


public final class SimulationImpl implements Simulation {
    private final Timer timer;
    private double timeStep;

    public long timePassedS() {
        return timer.timePassedS();
    }

    private final UniverseRunner universeRunner;

    private GUI gui;
    private final GUIRunner guiRunner;
    private Universe universe;

    public Universe universe() {
        return universe;
    }

    public double timeStep() {
        return timeStep;
    }

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
        universeRunner.setTimeStep(timeStep);
    }

    public SimulationImpl(int initialTimeStep, int guiFPS) {
        this(initialTimeStep, guiFPS, null);
    }

    public SimulationImpl(int initialTimeStep, int guiFPS, Simulation simulation) {
        if (simulation != null)
            simulation.pause();

        timeStep = initialTimeStep;

        timer = new Timer();

        universe = UniverseImpl.newSolarSystem(this);

        if (simulation == null)
            gui = new GUIImpl(this, universe);
        else {
            gui = ((SimulationImpl) simulation).gui;
            gui.setSimulation(this);
        }

        universeRunner = new UniverseRunner(timer, universe, initialTimeStep);
        guiRunner = new GUIRunner(guiFPS, gui, universe);

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

    public void addLaunch(SpaceShip spaceShip, long time, Controller ...controllers) {
        universe.addLaunch(spaceShip, time, controllers);
    }

    public void setUniverseRunnerMinFrameTime(long minFrameTimeNs) {
        universeRunner.setMinFrameTime(minFrameTimeNs);
    }
}