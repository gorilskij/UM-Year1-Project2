package simulation.universe;

import body.SpaceShip;
import body.interfaces.*;
import data.BodyFactory;

import java.util.ArrayList;
import java.util.List;

public final class UniverseImpl implements Universe {

    private final List<Body> allBodies = new ArrayList<>();
    private final List<Body> attractors = new ArrayList<>();
    private final List<Moving> movingBodies = new ArrayList<>();

    private UniverseImpl() {} // shouldn't be used outside class

    public static Universe newSolarSystem() {
        Universe universe = new UniverseImpl();

        for (Body body : BodyFactory.createSolarSystem())
            universe.addBody(body);

        return universe;
    }

    public void addBody(Body body) {
        allBodies.add(body);

        if (body instanceof Moving) {
            movingBodies.add((Moving) body);
        } else {
            attractors.add(body);
        }
    }

    @Override
    public void addLaunch(SpaceShip ship) {
        // TODO: implement
    }
}
