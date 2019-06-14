package general_support;

public interface Integrator {

    /**
     *
     * @param position Vector indicating current position
     * @param acceleration Vector indicating current acceleration
     * @param velocity Vector indicating current velocity
     */
    void integrate(Vector position, Vector velocity, Vector acceleration, double timeStep);
}
