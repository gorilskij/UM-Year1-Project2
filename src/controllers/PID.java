package controllers;

import body.SpaceShip;
import general_support.Vector;
import general_support.Numerical;
import java.util.List;


public class PID implements Controller {

    private Vector setPoint;
    private List<Double> errors;
    private double integral;
    private double lastError; // Could also retrieve this from errors. Technically a waste of memory but I like it.
    private double P;
    private double I;
    private double D;

    public PID(Vector setPoint, double P, double I, double D){
        this.setPoint = setPoint;
        this.P = P;
        this.I = I;
        this.D = D;
    }

    @Override
    public double control(SpaceShip ship, Vector velocity) {
        double error = this.proportional(ship.position());
        this.updateErrors(error);
        double integralError = this.updateIntegral(error);
        this.lastError = error;
        return P*error + I*integralError + D*velocity.magnitude();

    }

    private void updateErrors(double error){
        if (errors.size() < 3)
            errors.add(error);
        else{
            errors.add(error);
            errors.remove(0);
        }
    }

    private double proportional(Vector position){
        return position.distanceTo(this.setPoint);
    }

    private double updateIntegral(double error){
        if(errors.size() == 1)
            this.integral = 0;
        else if(errors.size() == 2)
            this.integral = error/2 + this.lastError/2;
        else
            this.integral = Numerical.updateTrapezoidIntegral(error, this.lastError, this.integral);

        return this.integral;
    }




}
