package body;

import body.interfaces.Attractor;
import body.interfaces.Drawable;
import general_support.Vector;

import java.awt.*;

public class Star extends BaseBody implements Attractor, Drawable {
    private final double mass;

    public double getMass() {
        return mass;
    }

    public Vector getPosition() {
        return Vector.ZERO;
    }

    Star(String name, Color color, double mass) {
        super(name, color);
        this.mass = mass;
    }

    public void draw(Graphics g) {
        // TODO: implement
    }
}