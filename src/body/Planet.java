package body;

import body.interfaces.*;
import general_support.PaintingTools;
import general_support.Trailer;
import general_support.Vector;

import java.awt.*;

public class Planet extends BaseBody implements Round, Moving, Attractive, Trailing {
    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private Vector lastAcceleration = Vector.ZERO;
    private final double radius;
    private final Trailer trailer = new Trailer(this, 1000, Math.PI * 1e5);

    public Trailer trailer() {
        return trailer;
    }

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

    public void paint(Graphics g, Vector centerPosition, int rotationDeg, double scale) {
        PaintingTools.paintCircularObject(g, scale, this, centerPosition, rotationDeg);
        trailer.paint(g, centerPosition, rotationDeg, scale);
    }

    @Override
    public Vector directionTo(Body body) {
        return position.vectorTo(body.position()).direction();
    }

    public Vector nextPosition() {
        return position
                .plus(velocity
                        .times(TimeStep)
                        .plus(acceleration
                                .times(Math.pow(
                                        TimeStep,
                                        2) / 2)
                        )
                );
    }
}
