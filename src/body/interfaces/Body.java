package body.interfaces;

import general_support.Rotation;
import general_support.Trailer;
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
     * @param g graphics
     * @param centerPosition position of centered body
     * @param scale scale
     */
    void paint(Graphics g, Vector centerPosition, Rotation rotation, double scale);

    /**
     *
     * @param body celestial object of our universe
     * @return direction to body
     */
    Vector directionTo(Body body);
}
