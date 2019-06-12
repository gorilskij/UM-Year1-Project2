package body;

import body.interfaces.Drawable;
import general_support.Vector;

import java.awt.*;

public class Star extends BaseBody implements Drawable {

    private double radius;

    public Vector position() {
        return Vector.ZERO;
    }

    public double radius() {
        return radius;
    }

    private void setRadius(double radius) {
        this.radius = radius;
    }

    public Star(String name, double mass, double radius, Color color) {
        super(name, color, mass);
        setRadius(radius);
    }

    public void draw(Graphics g) {
        // TODO: implement
    }
}