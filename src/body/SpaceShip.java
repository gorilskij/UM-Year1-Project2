package body;

import body.interfaces.Moving;
import general_support.Vector;
import ship_support.ThrustSystem;
import simulation.universe.Universe;

import java.awt.*;

public class SpaceShip extends BaseBody implements Moving {
    public SpaceShip copy() {
        return new SpaceShip(name(), color(), mass(), universe());
    }

    private Vector position = null;
    private Vector velocity = null;
    private Vector pointing = null;
    private Vector acceleration = null;
    private Vector lastAcceleration = Vector.ZERO;
    private ThrustSystem thrustSystem;
    private double fuel_ejection;
    private Universe universe;

    //create's, set's



    public SpaceShip(String name, Color color, double mass, Universe universe) {
        super(name, color, mass);
        this.universe = universe;
    }

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

    public Vector lastAcceleration(){
        return this.lastAcceleration;
    }

    public void setAcceleration(Vector acceleration)
    {
        this.acceleration = acceleration;
    }

    public void setLastAcceleration(Vector lastAcceleration) {
        this.lastAcceleration = lastAcceleration;
    }

    public SpaceShip(String name, Color color, double mass) {
        super(name, color, mass);
    }

    public void setThrustSystem(ThrustSystem thrustSystem) {
        this.thrustSystem = thrustSystem;
    }

    public void thrust() {
        this.velocity = this.velocity.plus(this.pointing.times(this.thrustSystem.thrust(fuel_ejection, mass())));
    }

    public void rotate_right() {
        this.thrustSystem.rotate_right(fuel_ejection, mass());
    }

    public void rotate_left() {
        this.thrustSystem.rotate_right(fuel_ejection, mass());
//        velocity = velocity.plus(thrustSystem.thrust(1, mass()));
    }

    public Vector pointing() {
        return pointing;
    }

    public void setPointing(Vector pointing) {
        this.pointing = pointing;
    }

    public void start() {

    }




    public void paint(Graphics g, double scale) {
        // TODO: implement
    }
}
