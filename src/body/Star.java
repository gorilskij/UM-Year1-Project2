package body;

import general_support.CirclePainter;
import general_support.Vector;

import java.awt.*;

public class Star extends BaseBody {
    public Star copy() {
        return new Star(name(), mass(), radius, color());
    }

    public final double radius;

    public Vector position() {
        return Vector.ZERO;
    }

    public Star(String name, double mass, double radius, Color color) {
        super(name, color, mass);
        this.radius = radius;
    }

    public void paint(Graphics g) {
        CirclePainter.paintCircle(g, position(), radius, color());
    }
}