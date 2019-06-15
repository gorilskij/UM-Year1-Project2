package general_support.integrator;

import general_support.Vector;

public class LeapFrog implements Integrator {

    private Vector lastAcceleration = Vector.ZERO;

    @Override
    public void integrate(Vector position, Vector velocity, Vector acceleration, int timeStep) {
        position.plus(velocity
                .times(timeStep)
                .plus(acceleration.times(Math.pow(timeStep, 2) / 2))
        );
        velocity.plus(acceleration.averageWith(lastAcceleration).times(timeStep));
        lastAcceleration = acceleration;
    }
}
