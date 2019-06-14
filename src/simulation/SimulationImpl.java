package simulation;

import body.interfaces.Body;
import simulation.gui.GUI;
import simulation.gui.GUIImpl;
import simulation.universe.UniverseImpl;
import simulation.universe.Universe;

import java.util.List;

public final class SimulationImpl implements Simulation {
    private double timeStep;

    private final Universe universe;
    private final GUI gui;

    public SimulationImpl(double timeStep){
        this.timeStep = timeStep;

        this.universe = UniverseImpl.newSolarSystem();
        this.gui = new GUIImpl(this);
    }

    @Override
    public void addBodies(Body... bodies) {
        for (Body body : bodies)
            universe.addBody(body);
    }

    @Override
    public void iterate(Universe universe) {
        universe.iteratePhysics(this.timeStep);
    }

    @Override
    public double timeStep() {
        return this.timeStep;
    }

    @Override
    public void bodies() {
        for (Body body : universe.bodies())
            System.out.println(body.name());
    }
    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
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