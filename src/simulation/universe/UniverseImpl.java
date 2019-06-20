package simulation.universe;

import body.spaceship.SpaceShip;
import body.interfaces.*;
import body.spaceship.steering.Action;
import data.Constants;
import general_support.integrator.Integrator;
import general_support.Vector;
import data.BodyFactory;
import general_support.integrator.LeapFrog;
import simulation.interfaces.ShipLaunched;

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

    private final ShipLaunched shipListener;

    private UniverseImpl(ShipLaunched shipListener) {
        this.shipListener = shipListener;
    }

    public void addShip(SpaceShip ss) {
        Round earth = (Round) getBodyByName("earth");
        ss.setPosition(getBodyByName("sun")
                .position()
                .vectorTo(earth
                        .position())
                .direction()
                .times(earth
                        .radius())
                .plus(earth.position()));

        ss.setVelocity(((Moving) earth).velocity());
    }

    public static Universe newSolarSystem(ShipLaunched shipListener) {
        Universe universe = new UniverseImpl(shipListener);

        for (Body body : BodyFactory.createSolarSystem())
            universe.addBody(body);

        // fix relative positions and velocities
        // moon
        Moving moon = (Moving) universe.getBodyByName("moon");
        Moving earth = (Moving) universe.getBodyByName("earth");
        moon.setPosition(moon.position().plus(earth.position()));
        moon.setVelocity(moon.velocity().plus(earth.velocity()));

        // titan
        Moving titan = (Moving) universe.getBodyByName("titan");
        Moving saturn = (Moving) universe.getBodyByName("saturn");
        titan.setPosition(titan.position().plus(saturn.position()));
        titan.setVelocity(titan.velocity().plus(saturn.velocity()));

        return universe;
    }

    @Override
    public void addBody(Body body) {

        allBodies.add(body);

        if (body instanceof Attractive)
            attractors.add((Attractive) body);

        if (body instanceof Moving)
            movingBodies.add((Moving) body);

        if (body instanceof SpaceShip) {
            spaceShips.add((SpaceShip) body);
            ((SpaceShip) body).setUniverse(this);
            addShip((SpaceShip) body);
        }
    }

    @Override
    public void addLaunch() {
        // TODO: implement
        SpaceShip fr = new SpaceShip("1", Color.WHITE, 962, this);
        SpaceShip sr = new SpaceShip("2", Color.WHITE, 1871, this);
        SpaceShip tr = new SpaceShip( "3", Color.WHITE, 1933, this);

        addBody(fr);
        addBody(sr);
        addBody(tr);

        shipListener.shipLaunched();
    }

    private Vector computeAcceleration(Body body, Vector acceleration, Attractive attractor) {
        acceleration = acceleration
                .plus(
                        body
                                .position()
                                .vectorTo(
                                        attractor
                                                .position()
                                )
                                .direction()
                                .times(
                                        Constants.G * attractor.mass() / Math.pow(
                                                body
                                                        .position()
                                                        .distanceTo(
                                                                attractor
                                                                        .position()
                                                        ),
                                                2
                                        )
                                )
                );
        return acceleration;
    }

    @Override
    public void iterateCelestials(double timeStep) {
        for (Moving body : movingBodies) {
            Vector acceleration = Vector.ZERO;
            for (Attractive attractor : attractors) {
                if (! (body instanceof SpaceShip)) {
                    if (body == attractor) continue;
                    if (attractor.mass() == 0) continue;
                    if (body.acceleration() == null) {
                        body.setAcceleration(acceleration);
                    }
                    acceleration = computeAcceleration(body, acceleration, attractor);
                    /*Vector vectorToAttractor = body.position().vectorTo(attractor.position());
                    Vector directionToAttractor = vectorToAttractor.direction();
                    double distance = vectorToAttractor.magnitude();
                    double accelerationMagnitude = Constants.G * attractor.mass() / Math.pow(distance, 2);
                    acceleration = acceleration.plus(directionToAttractor.times(accelerationMagnitude));*/
                }
            }
            integrator.integrate(body, acceleration, timeStep);
        }
    }

    @Override
    public void iterateShips(double timeStep) {
        for (Moving ss : spaceShips) {
            Vector acceleration = Vector.ZERO;
            for (Attractive attractor : attractors) {
                if (attractor.mass() == 0) continue;
                if (ss.acceleration() == null) {
                    ss.setAcceleration(acceleration);
                }
                if (((SpaceShip) ss).parent() != null) {
                    ((SpaceShip) ss).setRelativePosition();
                } else if (((SpaceShip) ss).isOn((Body) attractor)) {
                    ((SpaceShip) ss).setParent((Body) attractor);
                    ((SpaceShip) ss).setRelativePosition();
                } else {
                    acceleration = computeAcceleration(ss, acceleration, attractor);
                }
            }
            integrator.integrate(ss, acceleration, timeStep);
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
