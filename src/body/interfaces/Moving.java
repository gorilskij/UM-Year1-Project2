package body.interfaces;

import general_support.Vector;

public interface Moving extends Body {

    void setPosition(Vector position);

    /**
     *
     * @return velocity
     */
    Vector velocity();

    /**
     * changes velocity of the body
     * @param velocity has to be Vector
     *
     */
    void setVelocity(Vector velocity);

    /**
     *
     * @return acceleration
     */
    Vector acceleration();

    /**
     * changes acceleration of the body
     * @param acceleration has to be Vector
     *
     */
    void setAcceleration(Vector acceleration);
}
