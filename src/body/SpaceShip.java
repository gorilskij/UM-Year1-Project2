package body;

import body.interfaces.Body;
import body.interfaces.Moving;
import body.interfaces.Round;
import body.interfaces.Trailing;
import body.surface.SurfaceImpl;
import controllers.Controller;
import general_support.*;
import simulation.Simulation;
import simulation.universe.Universe;

import java.awt.*;

public class SpaceShip extends BaseBody implements Moving, Trailing {
    public SpaceShip copy() {
        return new SpaceShip(name(), color(), mass(), simulation);
    }

    private Vector position = null;
    private Vector velocity = null;
    private Vector pointing = null;
    private Vector desiredPointing = null;
    private Vector acceleration = null;
    private Vector lastAcceleration = Vector.ZERO;
    private final Simulation simulation;
    private final Universe universe;
    private double radius;
    private Body parent;
    private Vector directionOnParent;
    private final Trailer trailer = new Trailer(this, 1e6);
    private final static double MAX_VELOCITY = 5E4;

    public Trailer trailer() {
        return trailer;
    }

    // for next position/velocity/acceleration

    private static final double POINTING_SPEED = 2; // degrees rotated at each time step

    public void adjustPointing() {
        double angle = pointing.angleBetween(desiredPointing);
        pointing = LinearAlgebra.rotateTo(pointing, desiredPointing, Math.min(POINTING_SPEED, angle)).direction();
    }
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
        if (parent == null)
            directionOnParent = null;
        else
            directionOnParent = parent.directionTo(this);
    }

    public double control() {
        if (controller != null)
            return controller.control(simulation.timeStep());
        else
            return 0;
    }

    public SpaceShip(String name, Color color, double mass, Simulation simulation) {
        super(name, color, mass);
        this.simulation = simulation;
        universe = simulation.universe();
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
        if (velocity.magnitude() <= MAX_VELOCITY) {
            this.velocity = velocity;
        } else {
            this.velocity = velocity.direction().times(MAX_VELOCITY);
        }
    }

    public Vector acceleration() {
        return acceleration;
    }

    public Vector lastAcceleration() {
        return this.lastAcceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public void setLastAcceleration(Vector lastAcceleration) {
        this.lastAcceleration = lastAcceleration;
    }

    public Vector pointing() {
        return pointing;
    }

    public void setDesiredPointing(Vector desiredPointing) {
        // convert to unit vector just in case
        this.desiredPointing = desiredPointing.direction();

        if (pointing == null)
            pointing = desiredPointing.direction();
    }

    public Universe universe() {
        return this.universe;
    }

    public void paint(Graphics g, Vector centerPosition, Rotation rotation, double scale) {
        g.setColor(color());

        Point.Double pos = PaintingTools.convert(position, centerPosition, rotation).toXYPoint();

        PaintingTools.paintHighlightCircle(g, scale, pos);
        PaintingTools.paintLabel(g, scale, pos, name());

        Vector rotatedPointing = PaintingTools.convert(pointing, Vector.ZERO, rotation);
        Vector rotatedDesiredPointing = PaintingTools.convert(desiredPointing, Vector.ZERO, rotation);

        PaintingTools.paintPointing(g, Color.RED, 100, pos, rotatedPointing);
        PaintingTools.paintPointing(g, Color.GREEN, 80, pos, rotatedDesiredPointing);

        trailer.paint(g, centerPosition, rotation, scale);
    }

    @Override
    public Vector directionTo(Body body) {
        return position.vectorTo(body.position()).direction();
    }

    public boolean isOn(Body body) {
        return ((position.distanceTo(body.position()) - ((Round) body).radius()) < SurfaceImpl.EPSILON);
    }

    /**
     * @return vector from planet center to the spaceship
     */
    private Vector relativePosition() {
        assert parent != null && directionOnParent != null : "null parent";

        return directionOnParent.times(((Round) parent).radius());
    }

    public void setRelativePosition() {
        setPosition(parent()
                .position()
                .plus(relativePosition())
        );
    }

    public Vector desiredPointing() {
        return desiredPointing;
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

    public void slowDown() {
        setVelocity(velocity.times(1/2));
        setAcceleration(acceleration.times(1/2));
    }
}
