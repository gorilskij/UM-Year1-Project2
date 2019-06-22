package body;

import body.interfaces.Body;
import body.interfaces.Moving;
import body.interfaces.Round;
import body.surface.SurfaceImpl;
import controllers.Controller;
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

    // can be changed for different parts of the journey
    private Controller controller = null;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Body parent() {
        return parent;
    }

    public void setParent(Body parent) {
        this.parent = parent;
    }

    public double control() {
        if (controller != null)
            return controller.control();
        else
            return 0;
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
        // convert to unit vector just in case
        this.pointing = pointing.direction();
    }

    public Universe universe() {
        return this.universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    public void paint(Graphics g, Vector centerPosition, double scale) {
        g.setColor(color());
        Point.Double pos = position.minus(centerPosition).toXYPoint();
        PaintingTools.paintHighlightCircle(g, scale, pos);
        PaintingTools.paintLabel(g, scale, pos, name());
        PaintingTools.paintPointing(g, scale, pos, pointing);
    }

    @Override
    public Vector directionTo(Body body) {
        return position.vectorTo(body.position()).direction();
    }

    public boolean isOn(Body body) {
        return ((position.distanceTo(body.position()) - ((Round) body).radius()) < SurfaceImpl.EPSILON);
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
