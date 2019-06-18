package body.spaceship.steering;

import body.spaceship.SpaceShip;
import general_support.Vector;

public class Action {
    private final SpaceShip spaceShip;
    private final int duration;
    private final Vector initialPointing;
    private final Vector finalPointing;
    private final double thrustPower;

    private double timePassed = 0;

    public Action(SpaceShip spaceShip, int duration, Vector finalPointing, double thrustPower) {
        if (duration <= 0)
            throw new IllegalStateException("duration must be > 0");

        this.spaceShip = spaceShip;
        this.duration = duration;
        initialPointing = spaceShip.pointing();
        this.finalPointing = finalPointing;
        this.thrustPower = thrustPower;
    }

    boolean isDone() {
        return timePassed >= duration;
    }

    // the resulting acceleration from this method is added to
    // the acceleration calculated in iteratePhysics
    // the method additionally performs rotation on the spaceship
    Vector getAccelerationAndPerformRotation(double timeStep) {
        timePassed += timeStep;

        double initialPointingWeight = (duration - timePassed) / duration;
        double finalPointingWeight = 1 - initialPointingWeight;

        Vector expectedPointing = initialPointing.times(initialPointingWeight).plus(
                finalPointing.times(finalPointingWeight)
        );

        spaceShip.setPointing(expectedPointing);

        // TODO: calculate thrust power and return
        throw new IllegalStateException("TODO");
    }
}
