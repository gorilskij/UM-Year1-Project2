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

    private final GUI gui;
    private final GUIRunner guiRunner;
    private final Universe universe;

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
        timeStep = initialTimeStep;

        timer = new Timer();

        universe = UniverseImpl.newSolarSystem(this);
        gui = new GUIImpl(this, universe);

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