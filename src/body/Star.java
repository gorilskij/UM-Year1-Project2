package body;

import body.interfaces.Attractor;

import java.awt.*;

public class Star extends BaseBody implements Attractor {
    private final double mass;

    public double getMass() {
        return mass;
    }

    Star(String name, Color color, double mass) {
        super(name, color);
        this.mass = mass;
    }
}
