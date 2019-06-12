package body;

import body.interfaces.Drawable;
import body.interfaces.Moving;
import general_support.Vector;

import java.awt.*;

public class SpaceShip extends BaseBody implements Moving, Drawable {

    private Vector position = null;
    private Vector velocity = null;
    private Vector acceleration = null;

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

    SpaceShip(String name, Color color, double mass) {
        super(name, color, mass);
    }

    public void draw(Graphics g) {
        // TODO: implement
    }
}
