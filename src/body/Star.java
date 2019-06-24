package body;

import body.interfaces.Attractive;
import body.interfaces.Body;
import body.interfaces.Round;
import general_support.PaintingTools;
import general_support.Rotation;
import general_support.Vector;

import java.awt.*;

public class Star extends BaseBody implements Round, Attractive {
    public Star copy() {
        return new Star(name(), mass(), radius, color());
    }

    private final double radius;

    public double radius() {
        return radius;
    }

    public Vector position() {
        return Vector.ZERO;
    }

    public Star(String name, double mass, double radius, Color color) {
        super(name, color, mass);
        this.radius = radius;
    }

    public void paint(Graphics g, Vector centerPosition, Rotation rotation, double scale) {
        PaintingTools.paintCircularObject(g, scale, this, centerPosition, rotation);
    }

    @Override
    public Vector directionTo(Body body) {
        return position().vectorTo(body.position()).direction();
    }
}