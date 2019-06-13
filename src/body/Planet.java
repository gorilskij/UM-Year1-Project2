package body;

import body.interfaces.Drawable;
import body.interfaces.Moving;
import general_support.Vector;

import java.awt.*;

public class Planet extends BaseBody implements Moving, Drawable {

    private Vector position;
    private Vector velocity;
    private Vector acceleration = null;
    public final double radius;

    public Planet copy() {
        return new Planet(name(), mass(), radius, position, velocity, color());
    }

    //create's, set's

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

    public Planet(String name, double mass, double radius, Vector position, Vector velocity, Color color) {
        super(name, color, mass);
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
    }

    public void draw(Graphics g) {
        // TODO: implement
    }
}
