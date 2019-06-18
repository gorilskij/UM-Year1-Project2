package body.spaceship.steering;

import general_support.Vector;

public interface Steering {
    void setCurrentAction(int duration, Vector finalPointing, int thrustPower);
    Vector getAccelerationAndPerformRotation(double timeStep);
}
