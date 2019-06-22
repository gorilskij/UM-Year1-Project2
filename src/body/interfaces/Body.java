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

    Body copy();

    /**
     *
     * @return current position
     */
    Vector position();

    /**
     *
     * @param body celestial object of our universe
     * @return direction to body
     */
    Vector directionTo(Body body);

    /**
     *
     * @param g graphics
     * @param centerPosition centered pos
     * @param scale scale
     */
    void paint(Graphics g, Vector centerPosition, double scale);
}
