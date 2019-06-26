package simulation.universe;

import body.Planet;
import body.SpaceShip;
import body.interfaces.*;
import controllers.LandingController;
import controllers.LaunchController;
import controllers.PID;
import data.Constants;
import general_support.integrator.Euler;
import general_support.integrator.Integrator;
import general_support.Vector;
import data.BodyFactory;
import general_support.integrator.LeapFrog;
import simulation.Simulation;
import simulation.interfaces.ShipLaunched;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class UniverseImpl implements Universe {
    private final List<Body> allBodies = new ArrayList<>();
    private final List<Attractive> attractors = new ArrayList<>();
    private final List<Moving> movingBodies = new ArrayList<>();
    private final List<SpaceShip> spaceShips = new ArrayList<>();
    private final List<Trailing> trailingBodies = new ArrayList<>();

    private final List<LaunchPackage> queuedLaunches = new ArrayList<>();

    private Integrator integrator = new LeapFrog();

    public List<Body> allBodies() {
        return allBodies;
    }

    public List<Trailing> trailingBodies() {
        return trailingBodies;
    }

    private final Simulation simulation;

    public Simulation simulation() {
        return simulation;
    }

    public Body getBodyByName(String name) {
        for (Body body : allBodies)
            if (body.name().equals(name))
                return body;

        throw new IllegalStateException("body with name \"" + name + "\" not in universe");
    }

    private UniverseImpl(Simulation simulation) {
        this.simulation = simulation;
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

    public static Universe newSolarSystem(Simulation simulation) {
        Universe universe = new UniverseImpl(simulation);

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
            addShip((SpaceShip) body);
        }

        if (body instanceof Trailing)
            trailingBodies.add((Trailing) body);
    }

    private void launchShip(SpaceShip spaceShip) {
        Planet earth = (Planet) getBodyByName("earth");
        Vector sunToEarth = getBodyByName("sun").position().vectorTo(earth.position()).direction();
        spaceShip.setPosition(earth.position().plus(sunToEarth.times(earth.radius())));
        spaceShip.setDesiredPointing(sunToEarth);

        LaunchController launchController = new LaunchController(
                this,
                spaceShip,
                earth);

        PID pid1 = new PID(this,
                spaceShip,
                this.getBodyByName("saturn"),
                3E-12, 2.5E-11, 1E-3, 5E11,
                true,
                5E-5
        );

        PID pid2 = new PID(this,
                spaceShip,
                this.getBodyByName("titan"),
                3E-1, 2.5E-1, 1, 5E10,
                true,
                5E-5
        );

        LandingController landing = new LandingController(
                this,
                spaceShip,
                (Moving) getBodyByName("saturn"),
                (Moving) getBodyByName("titan")
        );


        launchController.setNextController(pid1);
        pid1.setNextController(pid2);
        pid2.setNextController(landing);

        spaceShip.setController(pid1);



        addBody(spaceShip);
        simulation.shipLaunched();
    }

    @Override
    public void addLaunch(String name, double mass, long time) { // time in seconds
        assert time >= simulation.timePassedS() : "tried to launch spaceShip in the past";

        queuedLaunches.add(new LaunchPackage(
                new SpaceShip(name, Color.WHITE, mass, simulation), time
        ));
    }

    private Vector computeAcceleration(Body body, Vector acceleration, Attractive attractor) {
        acceleration = acceleration.plus(body
                .position()
                .vectorTo(attractor.position())
                .direction()
                .times(
                        Constants.G * attractor.mass()
                                / Math.pow(body.position().distanceTo(attractor.position()), 2)
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
                }
            }

            integrator.integrate(body, acceleration, timeStep);
        }
    }

    @Override
    public void iterateShips(double timeStep) {
        for (SpaceShip ss : spaceShips) {
            Vector acceleration = Vector.ZERO;

            // turn n degrees towards desired pointing
            ss.adjustPointing();

            for (Attractive attractor : attractors) {
                if (ss.acceleration() == null) {
                    ss.setAcceleration(acceleration);
                }

                if (ss.parent() == null && ss.isOn((Body) attractor)) {
                    Vector relativeVelocity = ss.velocity().minus(((Moving) attractor).velocity());
                    if (relativeVelocity.magnitude() > 5e3)
                        throw new IllegalStateException("spaceship " + ss.name() + " crashed on " + ((Moving) attractor).name() + " with a speed of " + relativeVelocity.magnitude());

                    ss.setParent((Body) attractor);
                    ss.setController(null);

                    throw new IllegalStateException("landed on " + ((Moving) attractor).name() + " at " + relativeVelocity.magnitude() + "m/s");
                }

                if (ss.parent() != null) {
                    ss.setRelativePosition();
                    ss.setVelocity(Vector.ZERO);
                    acceleration = Vector.ZERO;
                } else {
                    acceleration = computeAcceleration(ss, acceleration, attractor);
                }
            }

            // important: spaceship only points in the right direction after the control method is called
            double controllerAccelerationMagnitude = ss.control();
            Vector controllerAcceleration = ss.pointing().times(controllerAccelerationMagnitude);

            if (controllerAcceleration.magnitude() > 1) {
                ss.setParent(null);
            }

            acceleration = acceleration.plus(controllerAcceleration);

            try {
                Moving titan = (Moving) getBodyByName("titan");
                if (ss.position().distanceTo(titan.position()) < 1e10) {
                    double decelerationMagnitude = Math.min(Math.abs(
                            ss.velocity().magnitude() - titan.velocity().magnitude()
                    ) / 1e6, 1);

                    acceleration = acceleration.plus(
                            ss.velocity().minus(titan.velocity()).direction().inverse().times(
                                    decelerationMagnitude
                            )
                    );
                }
            } catch (Exception e) {
            }

            integrator.integrate(ss, acceleration, timeStep);
        }

        // launch queued ships
        List<LaunchPackage> launched = new ArrayList<>();
        for (LaunchPackage p : queuedLaunches) {
            if (p.time() <= simulation.timePassedS()) {
                launchShip(p.ship());
                launched.add(p);
            }
        }

        for (LaunchPackage p : launched)
            queuedLaunches.remove(p);
    }

    public void iterateTrailers() {
        for (Trailing trailing : trailingBodies)
            trailing.trailer().push();
    }
}
