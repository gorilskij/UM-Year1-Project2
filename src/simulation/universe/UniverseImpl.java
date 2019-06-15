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
    private final List<Moving> spaceShips = new ArrayList<>();
    private final List<Moving> movingBodies = new ArrayList<>();
    private final List<Body> attractors = new ArrayList<>();
    public Integrator integrator = new LeapFrog();

    private UniverseImpl() {} // shouldn't be used outside class

    public static Universe newSolarSystem() {
        Universe universe = new UniverseImpl();

        for (Body body : BodyFactory.createSolarSystem())
            universe.addBody(body);

        return universe;
    }

    @Override
    public void addBody(Body body) {

        allBodies.add(body);

        if (body instanceof SpaceShip) {
            spaceShips.add((Moving) body);
        } else {
            attractors.add(body);
        }

        if (body instanceof Moving) {
            movingBodies.add((Moving) body);
        }
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
            for (Body attractor : attractors) {
                if (body == attractor) continue;

                Vector vectorToAttractor = body.position().vectorTo(attractor.position());
                double distance = vectorToAttractor.magnitude();
                Vector directionToAttractor = vectorToAttractor.direction();

                double accelerationMagnitude = Constants.G * attractor.mass() / Math.pow(distance, 2);
                acceleration.plus(directionToAttractor.times(accelerationMagnitude));

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
