package body.interfaces;

import general_support.Vector;

public interface Moving {
    Vector getPosition();
    Vector getVelocity();

    void setPosition(Vector position);
    void setVelocity(Vector velocity);
}
