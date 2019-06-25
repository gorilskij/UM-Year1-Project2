package general_support.integrator;

import body.interfaces.Moving;
import general_support.Vector;
import simulation.universe.Universe;

public interface Integrator {
    void integrate(Moving body, Vector acceleration, double timeStep);
}
