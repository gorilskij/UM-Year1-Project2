package body.interfaces;

import general_support.Vector;

import java.awt.*;

public interface Body {
    /**
     *
     * @return name
     */
    String name();

    /**
     *
     * @return color
     */
    Color color();

    /**
     *
     * @return mass
     */
    double mass();

    /**
     *
     * @return current position
     */
    Vector position();

    /**
     *
     * @param position position to assign to
     */
    void setPosition(Vector position);
}
