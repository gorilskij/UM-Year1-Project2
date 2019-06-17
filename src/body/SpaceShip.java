package body;

import body.interfaces.Moving;
import body.interfaces.Round;
import general_support.PaintingTools;
import general_support.Vector;
import ship_support.ThrustSystem;
import simulation.universe.Universe;

import java.awt.*;

public class SpaceShip extends BaseBody implements Moving, Round {
    public SpaceShip copy() {
        return new SpaceShip(name(), color(), mass(), universe());
    }

    private Vector position = null;
    private Vector velocity = null;
    private Vector pointing = null;
    private Vector acceleration = null;
    private Vector lastAcceleration = Vector.ZERO;
    private ThrustSystem thrustSystem;
    private double fuel_ejection = 10;
    private Universe universe;
    private double radius;
    //create's, set's



    public SpaceShip(String name, Color color, double mass, Universe universe) {
        super(name, color, mass);
        this.universe = universe;
    }

    public double radius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
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

    public Universe universe() {
        return this.universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    public void paint(Graphics g, double scale) {
        PaintingTools.paintCircularObject(g, scale, this);
        /*g.setColor(color());
        Point.Double pos = position().toXYPoint();
        int radius = 10000000;
        int diameter = (int) Math.round(radius * 2 * scale);

        g.fillOval(
                (int) Math.round((pos.x - radius) * scale),
                (int) Math.round((pos.y - radius) * scale),
                diameter, diameter
        );

        PaintingTools.paintLabel(g, scale, pos, name());
        PaintingTools.paintHighlightCircle(g, scale, pos);*/
    }
}
