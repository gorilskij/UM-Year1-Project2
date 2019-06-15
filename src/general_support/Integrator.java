package general_support;

public interface Integrator {

    /**
     *  @param position Vector indicating current position
     * @param velocity Vector indicating current velocity
     * @param acceleration Vector indicating current acceleration
     * @param timeStep
     */
    void integrate(Vector position, Vector velocity, Vector acceleration, int timeStep);
}
