package body.spaceship.steering;

import body.spaceship.SpaceShip;
import general_support.Vector;

public class SteeringImpl implements Steering {
    private final SpaceShip spaceShip;
    private Action currentAction = null;

    public SteeringImpl(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }

    @Override
    public void setCurrentAction(int duration, Vector finalPointing, int thrustPower) {
        currentAction = new Action(spaceShip, duration, finalPointing, thrustPower);
    }

    @Override
    public Vector getAccelerationAndPerformRotation(double timeStep) {
        if (currentAction == null || currentAction.isDone()) {
            currentAction = null;
            return Vector.ZERO;
        }

        return currentAction.getAccelerationAndPerformRotation(timeStep);
    }
}
