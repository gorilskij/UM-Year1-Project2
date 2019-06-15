package body;

import body.interfaces.Moving;
import general_support.Vector;
import ship_support.ThrustSystem;

import java.awt.*;

public class SpaceShip extends BaseBody implements Moving {
    public SpaceShip copy() {
        return new SpaceShip(name(), color(), mass());
    }

    private Vector position = null;
    private Vector velocity = null;
    private Vector pointing = null;
    private Vector acceleration = null;
    private ThrustSystem thrustSystem = null;

    //create's, set's

    public Vector position() {
        if (position == null)
            throw new IllegalStateException("tried to access null position");
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector velocity() {
        if (velocity == null)
            throw new IllegalStateException("tried to access null velocity");
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector acceleration() {
        if (acceleration == null)
            throw new IllegalStateException("tried to access null acceleration");
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public SpaceShip(String name, Color color, double mass) {
        super(name, color, mass);
    }

    public void setThrustSystem(ThrustSystem thrustSystem) {
        this.thrustSystem = thrustSystem;
    }

    public void thrust() {
//        velocity = velocity.plus(thrustSystem.thrust(1, mass()));
    }

    public Vector pointing() {
        return pointing;
    }

    public void setPointing(Vector pointing) {
        this.pointing = pointing;
    }

    public void rotate(Vector direction) {

    }




    public void paint(Graphics g, double scale) {
        // TODO: implement
    }
}
