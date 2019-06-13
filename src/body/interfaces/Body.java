package body.interfaces;

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
}
