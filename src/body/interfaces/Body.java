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

    void paint(Graphics g, double scale);
}
