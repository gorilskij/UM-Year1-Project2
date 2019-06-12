package body.interfaces;

import body.interfaces.granular.GetPosition;
import general_support.Vector;

public interface Moving extends GetPosition {
    void setPosition(Vector position);

    Vector getVelocity();
    void setVelocity(Vector velocity);
}
