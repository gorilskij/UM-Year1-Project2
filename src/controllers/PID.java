package controllers;

import body.SpaceShip;
import general_support.Vector;
import general_support.Numerical;
import java.util.List;


public class PID implements Controller {

    private Trajectory trajectory;
    private List<Double> errors;
    private double integral;
    private double lastError; // Could also retrieve this from errors. Technically a waste of memory but I like it.

    @Override
    public void control(SpaceShip ship, Vector velocity) {
        double error = this.proportional(ship.position());
        this.updateErrors(error);
        double integralError = this.updateIntegral(error);
        double derivativeError = this.derivative();
        this.lastError = error;

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
        return position.distanceTo(trajectory.nextSetPoint());
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

    private double derivative(){
        assert this.errors.size() > 0;
        assert this.errors.size() < 4;
        if(errors.size() == 1)
            return 0;
        else if(errors.size() == 2)
            return Numerical.TwoPointDerivativeBackWard(this.errors);
        else
            return Numerical.ThreePointDerivativeBackWard(this.errors);
    }

    private class Trajectory{

        private Vector[] setPoints;

        Vector nextSetPoint(){
            return Vector.ZERO;
        }

    }
}
