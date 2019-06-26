package general_support.integrator;

import body.interfaces.Body;
import body.interfaces.Moving;
import data.Constants;
import general_support.Vector;
import simulation.universe.Universe;

public class LeapFrog implements Integrator {


    @Override
    public void integrate(Moving body, Vector acceleration, double timeStep) {
        Vector newPosition = body
                .position()
                .plus(body.velocity().times(timeStep))
                .plus(body.lastAcceleration().times(Math.pow(timeStep, 2) / 2));

        Vector newVelocity = body
                .velocity()
                .plus(acceleration.plus(body.lastAcceleration()).times(timeStep / 2));

        body.setPosition(newPosition);
        body.setVelocity(newVelocity);
        body.setLastAcceleration(acceleration);

//        body.setPosition(body.position().plus(
//                body.velocity()
//                .times(timeStep)
//                .plus(acceleration.times(Math.pow(timeStep, 2) / 2))
//        ));
//        body.setVelocity(body.velocity().plus(acceleration.averageWith(body.lastAcceleration()).times(timeStep)));
//        body.setLastAcceleration(acceleration);
    }


}
