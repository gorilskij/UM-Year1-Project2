package body;

import body.interfaces.Drawable;
import body.interfaces.Moving;
import general_support.Vector;

import java.awt.*;

public class Planet extends BaseBody implements Moving, Drawable {

    private Vector position = null;
    private Vector velocity = null;
    private Vector acceleration = null;
    private double radius;

    //get's, set's

    public Vector position() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector velocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector acceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public double radius() {
        return radius;
    }

    private void setRadius(double radius) {
        this.radius = radius;
    }

    public Planet(String name, double mass, double radius, Vector position, Vector velocity, Color color) {
        super(name, color, mass);
        setPosition(position);
        setVelocity(velocity);
        setRadius(radius);
    }

    public void draw(Graphics g) {
        // TODO: implement
    }
}
