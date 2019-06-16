package body;

import body.interfaces.Attractive;
import body.interfaces.Moving;
import body.interfaces.Round;
import general_support.PaintingTools;
import general_support.Vector;

import java.awt.*;

public class Planet extends BaseBody implements Round, Moving, Attractive {
    private Vector position;
    private Vector velocity;
    private Vector acceleration = null;
    private Vector lastAcceleration = Vector.ZERO;
    private final double radius;

    public double radius() {
        return radius;
    }

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

    public Vector lastAcceleration(){
        return this.lastAcceleration;
    }

    public void setLastAcceleration(Vector lastAcceleration) {
        this.lastAcceleration = lastAcceleration;
    }

    public Planet(String name, double mass, double radius, Vector position, Vector velocity, Color color) {
        super(name, color, mass);
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
    }

    public void paint(Graphics g, double scale) {
        PaintingTools.paintCircularObject(g, scale, this);
    }
}
