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
    private boolean future;
    private static final double OVERSHOT = 5;

    private final double MAX_ACCELERATION;
    private static double FUTURE_STEP_PREDICTION = 1000;
    private static final double FUTURE_STEP_SIZE = 1E-3;

    public PID(Universe universe, SpaceShip spaceShip, Body body, double P, double I, double D, double closest, boolean future, double maxAcceleration) {
        super(universe, spaceShip);
        this.errors = new ArrayList<>();
        this.trackedBody = body;
        this.P = P;
        this.I = I;
        this.D = D;
        this.closest = closest;
        this.future = future;
        this.MAX_ACCELERATION = maxAcceleration;
    }

    public PID(Universe universe, SpaceShip spaceShip, Body body, List<Double> errors, double P, double I, double D, double closest, boolean future, double maxAcceleration) {
        super(universe, spaceShip);
        this.errors = errors;
        this.trackedBody = body;
        this.P = P;
        this.I = I;
        this.D = D;
        this.closest = closest;
        this.future = future;
        this.MAX_ACCELERATION = maxAcceleration;
    }


    @Override
    public double control(double timeStep) {
        double error = this.proportional(spaceShip.position());

        if (error == 0)
            return 0;

        Vector vectorToTitan = spaceShip.nextPosition().vectorTo(trackedBody.position());
        Vector futureVectorToTitan = spaceShip.position().vectorTo(((Planet) trackedBody).nextPosition(FUTURE_STEP_PREDICTION));
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
        if (future && FUTURE_STEP_PREDICTION > 0) {
            FUTURE_STEP_PREDICTION -= FUTURE_STEP_SIZE;
            return position.distanceTo(((Planet) trackedBody).nextPosition(FUTURE_STEP_PREDICTION));
        }

        else {
//        if (future && FUTURE_STEP_PREDICTION > 0) {
//            if (futurePositionOfTrackedBody(FUTURE_STEP_PREDICTION) != trackedBody.position()) {
//                System.out.println("not equal" + FUTURE_STEP_PREDICTION);
//            }
//            FUTURE_STEP_PREDICTION -= FUTURE_STEP_SIZE;
//            return position.distanceTo(futurePositionOfTrackedBody(FUTURE_STEP_PREDICTION));
//        }
            return position.distanceTo(trackedBody.position());
        }
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

    public Vector futurePositionOfTrackedBody(double numberOfTimeSteps) {
        return ((Planet) trackedBody).integrationResult(numberOfTimeSteps, universe.getBodyByName("sun"));
    }
}