package simulation.universe;

import body.SpaceShip;
import body.interfaces.*;
import data.Constants;
import general_support.Integrator;
import general_support.LeapFrog;
import general_support.Vector;
import data.BodyFactory;

import java.util.ArrayList;
import java.util.List;

public final class UniverseImpl implements Universe {

    private final List<Body> allBodies = new ArrayList<>();
    private final List<Moving> spaceShips = new ArrayList<>();
    private final List<Moving> movingBodies = new ArrayList<>();
    private final List<Body> attractors = new ArrayList<>();
    private Stable star = null;
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

        if (body instanceof Stable) {
            star = (Stable) body;
        }

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
    public void addLaunch(SpaceShip ship) {
        // TODO: implement
    }

    @Override
    public void iteratePhysics(double timeStep) {

        for (Body body : allBodies) {
            Vector acceleration = Vector.ZERO;
            for (Body attractor : attractors) {
                if (body == attractor) continue;

                Vector vectorToAttractor = body.position().vectorTo(attractor.position());
                double distance = vectorToAttractor.magnitude();
                Vector directionToAttractor = vectorToAttractor.direction();

                double accelerationMagnitude = Constants.G * attractor.mass() / Math.pow(distance, 2);
                acceleration.plus(directionToAttractor.times(accelerationMagnitude));

            }
        }

    }
}
