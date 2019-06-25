package body;

import body.interfaces.*;
import data.Constants;
import general_support.PaintingTools;
import general_support.Rotation;
import general_support.Trailer;
import general_support.Vector;

import java.awt.*;

public class Planet extends BaseBody implements Round, Moving, Attractive, Trailing {
    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private Vector lastAcceleration = Vector.ZERO;
    private final double radius;
    private final Trailer trailer = new Trailer(this, 1000, 1e5);

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

    public void paint(Graphics g, Vector centerPosition, Rotation rotation, double scale) {
        PaintingTools.paintCircularObject(g, scale, this, centerPosition, rotation);
        trailer.paint(g, centerPosition, rotation, scale);
    }

    @Override
    public Vector directionTo(Body body) {
        return position.vectorTo(body.position()).direction();
    }

    public Vector nextPosition(double n) {
        return position
                .plus(velocity
                        .times(TIME_STEP * n)
                        .plus(acceleration
                                .times(Math.pow(
                                        TIME_STEP * n,
                                        2) / 2)
                        )
                );
    }

    public Vector integrationResult(double numberOfTimeSteps, Body attractor) {
        Vector position = this.position();
        Vector velocity = this.velocity();
        Vector lastAcceleration = this.acceleration();
        for (double i = 0; i < numberOfTimeSteps; i += 1E-3) {
            position = position.plus(velocity
                    .times(TIME_STEP)
                    .plus(acceleration.times(Math.pow(TIME_STEP, 2) / 2))
            );
            velocity = velocity.plus(acceleration.averageWith(lastAcceleration).times(TIME_STEP));
            lastAcceleration = lastAcceleration.plus(position
                    .vectorTo(attractor.position())
                    .direction()
                    .times(
                            Constants.G * attractor.mass()
                                    / Math.pow(position.distanceTo(attractor.position()), 2)
                    )
            );
        }
        return position;
    }
}
