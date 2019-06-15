package general_support.integrator;

import general_support.Vector;

public interface Integrator {

    /**
     *  @param position Vector indicating current position
     * @param velocity Vector indicating current velocity
     * @param acceleration Vector indicating current acceleration
     * @param timeStep timestep
     */
    void integrate(Vector position, Vector velocity, Vector acceleration, double timeStep);
}
