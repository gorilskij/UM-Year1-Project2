package body.spaceship;

import body.BaseBody;
import body.interfaces.Moving;
import body.interfaces.Round;
import body.spaceship.steering.Steering;
import body.spaceship.steering.SteeringImpl;
import general_support.PaintingTools;
import general_support.Vector;
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
    private Universe universe;
    private double radius;

    private final Steering steering = new SteeringImpl(this);

    public Steering steering() {
        return steering;
    }


    public SpaceShip(String name, Color color, double mass, Universe universe) {
        super(name, color, mass);
        this.universe = universe;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vector position() {
        assert position != null;
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector velocity() {
        assert velocity != null;
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector acceleration() {
        assert acceleration != null;
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

    public Vector pointing() {
        return pointing;
    }

    public void setPointing(Vector pointing) {
        this.pointing = pointing;
    }

    public Universe universe() {
        return this.universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    public void paint(Graphics g, double scale) {
        g.setColor(color());
        Point.Double pos = position.toXYPoint();
        PaintingTools.paintHighlightCircle(g, scale, pos);
        PaintingTools.paintLabel(g, scale, pos, name());
    }

    public boolean isOn(Round round) {
        return (position.distanceTo(round.position()) - round.radius()) < 0.1;
    }
}
