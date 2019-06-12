package simulation.universe;

import body.interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class BaseUniverse implements Universe {
    private final List<Body> allBodies = new ArrayList<>();
    private final List<Attractor> attractors = new ArrayList<>();
    private final List<Moving> movingBodies = new ArrayList<>();

    public void addBody(Body body) {
        allBodies.add(body);

        if (body instanceof Attractor)
            attractors.add((Attractor) body);

        if (body instanceof Moving)
            movingBodies.add((Moving) body);
    }
}
