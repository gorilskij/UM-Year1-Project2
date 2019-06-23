package body;

import body.interfaces.Body;
import general_support.Vector;

import java.awt.*;

public abstract class BaseBody implements Body {
    private final String name;
    private final Color color;
    private final double mass;
    protected static final double TimeStep = 100;

    public String name() {
        return name;
    }

    public Color color() {
        return color;
    }

    public double mass() { return mass; }

    public BaseBody(String name, Color color, double mass) {
        this.name = name;
        this.color = color;
        this.mass = mass;
    }

    public String toString() {
        return name;
    }
}
