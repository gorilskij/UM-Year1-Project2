package general_support;

public class LeapFrog implements Integrator {

    private Vector lastAcceleration = Vector.ZERO;

    @Override
    public void integrate(Vector position, Vector acceleration, Vector velocity, double timeStep) {
        position.plus(velocity
                .times(timeStep)
                .plus(acceleration.times(Math.pow(timeStep, 2) / 2))
        );
        velocity.plus(acceleration.averageWith(lastAcceleration).times(timeStep));
        lastAcceleration = acceleration;
    }
}
