package simulation;

import body.interfaces.Body;
import simulation.gui.GUI;
import simulation.gui.GUIImpl;
import simulation.universe.UniverseImpl;
import simulation.universe.Universe;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class SimulationImpl implements Simulation {
    private double timeStep;

    private final Universe universe;
    private final GUI gui;

    Calendar startDate;
    public long startMillis;
    Calendar currentDate;

    @Override
    public long currentMillis() {
        return currentDate.getTimeInMillis();
    }

    @Override
    public long secondPassed() {
        return TimeUnit.MILLISECONDS.toSeconds(currentMillis() - startMillis);
    }

    public SimulationImpl(double timeStep){
        this.timeStep = timeStep;

        this.universe = UniverseImpl.newSolarSystem();
        this.gui = new GUIImpl(this);

        startDate = Calendar.getInstance();
        startDate.set(Calendar.YEAR, 2019);
        startDate.set(Calendar.MONTH, Calendar.JANUARY);
        startDate.set(Calendar.DAY_OF_MONTH, 1);
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);

        startMillis = startDate.getTimeInMillis();

        currentDate = (Calendar) startDate.clone();
    }

    @Override
    public void addBodies(Body... bodies) {
        for (Body body : bodies)
            universe.addBody(body);
    }

    @Override
    public void iterate() {
        this.universe.iteratePhysics(this.timeStep);
    }

    @Override
    public double timeStep() {
        return this.timeStep;
    }

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

    @Override
    public void getData() {
        this.universe.getCurrentData();
    }

    @Override
    public void graphicsStart() {
        // TODO: implement
    }

    @Override
    public void graphicsStop() {
        // TODO: implement
    }
}