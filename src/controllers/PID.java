package controllers;

import body.Planet;
import body.SpaceShip;
import body.interfaces.Body;
import body.interfaces.Moving;
import general_support.LinearAlgebra;
import general_support.Vector;
import general_support.Numerical;
import simulation.universe.Universe;

import java.util.ArrayList;
import java.util.List;


public class PID extends BaseController {

    private Body trackedBody;
    private List<Double> errors;
    private double integral;
    private double lastError; // Could also retrieve this from errors. Technically a waste of memory but I like it.
    private double P;
    private double I;
    private double D;
    private double closest;
    private static final double OVERSHOT = 5;

    private static final double MAX_ACCELERATION = 6.35E-5;

    public PID(Universe universe, SpaceShip spaceShip, Body body, double P, double I, double D, double closest) {
        super(universe, spaceShip);
        this.errors = new ArrayList<>();
        this.trackedBody = body;
        this.P = P;
        this.I = I;
        this.D = D;
        this.closest = closest;
    }

    public PID(Universe universe, SpaceShip spaceShip, Body body, List<Double> errors, double P, double I, double D, double closest) {
        super(universe, spaceShip);
        this.errors = errors;
        this.trackedBody = body;
        this.P = P;
        this.I = I;
        this.D = D;
        this.closest = closest;
    }


    @Override
    public double control(double timeStep) {
        double error = this.proportional(spaceShip.position());

        if (error == 0)
            return 0;

        Vector vectorToTitan = spaceShip.nextPosition().vectorTo(trackedBody.position());
        Vector futureVectorToTitan = spaceShip.nextPosition().vectorTo(((Planet) trackedBody).nextPosition());
        Vector directionTotTitan = vectorToTitan.direction();
        Vector futureDirectionToTitan = futureVectorToTitan.direction();
        spaceShip.setDesiredPointing(LinearAlgebra.rotateTo(directionTotTitan, futureDirectionToTitan, directionTotTitan.angleBetween(futureDirectionToTitan) + OVERSHOT));

        this.updateErrors(error);
        double integralError = this.updateIntegral(error);
        this.lastError = error;
        double derivativeError = derivative(timeStep);
        double acceleration = P * error + I * integralError + D * derivativeError;

        if (vectorToTitan.magnitude() < closest && super.nextController != null) {
            this.spaceShip.setController(this.nextController);
            this.nextController = null;
        }

        return Math.min(MAX_ACCELERATION, acceleration);
    }

    public void setNextController(Controller c) {
        super.nextController = c;
    }

    private void updateErrors(double error) {
        if (errors.size() < 3)
            errors.add(error);
        else {
            errors.add(error);
            errors.remove(0);
        }
    }

    private double proportional(Vector position) {
        return position.distanceTo(trackedBody.position());
    }

    private double updateIntegral(double error) {
        if (errors.size() == 1)
            this.integral = 0;
        else if (errors.size() == 2)
            this.integral = error / 2 + this.lastError / 2;
        else
            this.integral = Numerical.updateTrapezoidIntegral(error, this.lastError, this.integral);

        return this.integral;
    }

    private double derivative(double h) {
        if (errors.size() == 1)
            return 0;
        else if (errors.size() == 2)
            return Numerical.TwoPointDerivativeBackWard(errors, h);
        else
            return Numerical.ThreePointDerivativeBackWard(errors, h);
    }

    public List<Double> getErrors() {
        return errors;
    }
}