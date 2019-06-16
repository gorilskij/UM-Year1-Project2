package simulation.universe;

import body.SpaceShip;
import body.interfaces.*;
import data.Constants;
import general_support.integrator.Integrator;
import general_support.integrator.LeapFrog;
import general_support.Vector;
import data.BodyFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class UniverseImpl implements Universe {
    private final List<Body> allBodies = new ArrayList<>();
    private final List<Attractive> attractors = new ArrayList<>();
    private final List<Moving> movingBodies = new ArrayList<>();
    private final List<Moving> spaceShips = new ArrayList<>();

    private Integrator integrator = new LeapFrog();

    public List<Body> allBodies() {
        return allBodies;
    }

    public Body getBodyByName(String name) {
        for (Body body : allBodies)
            if (body.name().equals(name))
                return body;

        throw new IllegalStateException("body with name \"" + name + "\" not in universe");
    }

    private UniverseImpl() {} // shouldn't be used outside class

    public static Universe newSolarSystem() {
        Universe universe = new UniverseImpl();

        for (Body body : BodyFactory.createSolarSystem())
            universe.addBody(body);

        // fix relative positions
        Moving moon = (Moving) universe.getBodyByName("moon");
        Moving earth = (Moving) universe.getBodyByName("earth");

        Moving titan = (Moving) universe.getBodyByName("titan");
        Moving saturn = (Moving) universe.getBodyByName("saturn");

        moon.setPosition(moon.position().plus(earth.position()));
        titan.setPosition(titan.position().plus(saturn.position()));

        return universe;
    }

    @Override
    public void addBody(Body body) {

        allBodies.add(body);

        if (body instanceof Attractive)
            attractors.add((Attractive) body);

        if (body instanceof Moving)
            movingBodies.add((Moving) body);

        if (body instanceof SpaceShip)
            spaceShips.add((SpaceShip) body);
    }

    @Override
    public void addLaunch() {
        // TODO: implement
        SpaceShip fr = new SpaceShip("fr", Color.WHITE, 962);
        SpaceShip sr = new SpaceShip("sr", Color.WHITE, 1871);
        SpaceShip tr = new SpaceShip( "tr", Color.WHITE, 1933);
    }

    @Override
    public void iteratePhysics(int timeStep) {
        for (Moving body : movingBodies) {
            Vector acceleration = Vector.ZERO;
            for (Attractive attractor : attractors) {
                if (body == attractor) continue;

                Vector vectorToAttractor = body.position().vectorTo(attractor.position());
                double distance = vectorToAttractor.magnitude();
                Vector directionToAttractor = vectorToAttractor.direction();

                double accelerationMagnitude = Constants.G * attractor.mass() / Math.pow(distance, 2);
                acceleration = acceleration.plus(directionToAttractor.times(accelerationMagnitude));

            }
            integrator.integrate(body.position(), body.velocity(), acceleration, timeStep);
        }
    }

    @Override
    public void getCurrentData() {
        for (Moving body : movingBodies) {
            System.out.println("body.name: " + body.name());
            System.out.println("body.velocity: " + body.velocity());
            System.out.println("body.position: " + body.position());
        }
    }
}
