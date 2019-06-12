package body;

import body.interfaces.Drawable;
import general_support.Vector;

import java.awt.*;

public class Star extends BaseBody implements Drawable {

    public Vector position() {
        return Vector.ZERO;
    }

    Star(String name, Color color, double mass) {
        super(name, color, mass);
    }

    public void draw(Graphics g) {
        // TODO: implement
    }
}