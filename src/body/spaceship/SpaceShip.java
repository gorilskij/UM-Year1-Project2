package body.spaceship;

import body.BaseBody;
import body.interfaces.Body;
import body.interfaces.Moving;
import body.interfaces.Round;
import body.spaceship.steering.Steering;
import body.spaceship.steering.SteeringImpl;
import body.surface.SurfaceImpl;
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
    private Body parent;

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

    public Body parent() {
        return parent;
    }

    public void setParent(Body parent) {
        this.parent = parent;
    }

    public void paint(Graphics g, double scale) {
        g.setColor(color());
        Point.Double pos = position.toXYPoint();
        PaintingTools.paintHighlightCircle(g, scale, pos);
        PaintingTools.paintLabel(g, scale, pos, name());
    }

    public boolean isOn(Body body) {
        if ((position.distanceTo(body.position()) - ((Round) body).radius()) < SurfaceImpl.EPSILON) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return vector from planet center to the spaceship
     */
    private Vector relativePosition() {
        assert parent != null : "null parent";
        Vector parentPosition = parent.position();
        Vector parentToShip = position.minus(parentPosition);
        Vector directionToShip = parentToShip.direction();
        return directionToShip.times(((Round) parent).radius());
    }

    public void setRelativePosition() {
        this
                .setPosition(
                        (this)
                                .parent()
                                .position()
                                .plus(
                                        (this)
                                                .relativePosition()
                                )
                );
    }
}