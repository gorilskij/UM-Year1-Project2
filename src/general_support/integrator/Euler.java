package general_support.integrator;

import body.interfaces.Moving;
import general_support.Vector;

public class Euler implements Integrator {
    @Override
    public void integrate(Moving body, Vector acceleration, double timeStep) {
        Vector newPosition = body.position().plus(body.velocity().times(timeStep));
        Vector newVelocity = body.velocity().plus(acceleration.times(timeStep));

        body.setPosition(newPosition);
        body.setVelocity(newVelocity);
    }
}
