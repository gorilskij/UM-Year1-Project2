package body.interfaces;

import general_support.Vector;
import java.awt.*;

public interface Drawable extends Body {

    /**
     *
     * @param g Graphics
     */
    void draw(Graphics g);

    /**
     *
     * @return position
     */
    Vector position();
}
