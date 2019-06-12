package body;

import body.interfaces.Body;

import java.awt.*;

public abstract class BaseBody implements Body {
    private final String name;
    private final Color color;
    private final double mass;

    public String name() {
        return name;
    }

    public Color color() {
        return color;
    }

    public double mass() { return mass; }

    BaseBody(String name, Color color, double mass) {
        this.name = name;
        this.color = color;
        this.mass = mass;
    }
}
