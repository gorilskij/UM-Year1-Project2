package controllers;

import body.SpaceShip;
import body.interfaces.Moving;
import general_support.Vector;
import general_support.Numerical;
import simulation.universe.Universe;

import java.util.ArrayList;
import java.util.List;


public class PID extends BaseController {

    private Moving setPoint;
    private List<Double> errors;
    private double integral;
    private double lastError; // Could also retrieve this from errors. Technically a waste of memory but I like it.
    private double P;
    private double I;
    private double D;

    public PID(Universe universe, SpaceShip spaceShip,Moving setPoint, double P, double I, double D){
        super(universe, spaceShip);
        errors = new ArrayList<>();
        this.setPoint = setPoint;
        this.P = P;
        this.I = I;
        this.D = D;
    }

    @Override
    public double control() {
        double error = this.proportional(spaceShip.position());
        if (error == 0)
            return 0;

        Vector vectorToTitan =  spaceShip.position().vectorTo(setPoint.position());
         Vector directionTotTitan = vectorToTitan.direction();
         System.out.println("spaceship position: " + spaceShip.position());
         System.out.println("titan distance :" + vectorToTitan);
         System.out.println("titan direction: " + directionTotTitan);
        if (!spaceShip.pointing().equals(directionTotTitan))
            spaceShip.setPointing(directionTotTitan);

        this.updateErrors(error);
        double integralError = this.updateIntegral(error);
        this.lastError = error;
        double derivativeError = (-1) *setPoint.velocity().vectorTo(spaceShip.velocity()).magnitude();
        System.out.println("Derivative: " + derivativeError);
        double acceleration =  P*error + I*integralError + D*derivativeError;
        System.out.println("acceleration: " + acceleration);
        if (acceleration > 7)
            return 7;
        return acceleration;

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
        return position.distanceTo(this.setPoint.position());
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
