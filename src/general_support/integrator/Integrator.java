package general_support.integrator;

import body.interfaces.Moving;
import general_support.Vector;

public interface Integrator {
    void integrate(Moving body, Vector acceleration, double timeStep);
}
