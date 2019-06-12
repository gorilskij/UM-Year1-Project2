package body;

import body.interfaces.Attractor;
import body.interfaces.Drawable;
import body.interfaces.Moving;
import general_support.Vector;

import java.awt.*;

public class Planet extends BaseBody implements Attractor, Moving, Drawable {
    private final double mass;

    public double getMass() {
        return mass;
    }

    private Vector position = null;
    private Vector velocity = null;

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    Planet(String name, Color color, double mass) {
        super(name, color);
        this.mass = mass;
    }

    public void draw(Graphics g) {
        // TODO: implement
    }
}
