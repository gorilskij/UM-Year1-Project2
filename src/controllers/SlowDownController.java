package controllers;

import body.Planet;
import body.SpaceShip;
import general_support.Vector;
import simulation.universe.Universe;

public class SlowDownController extends BaseController {
    private final Planet destination;

    public SlowDownController(Universe universe, SpaceShip spaceShip, Planet destination) {
        super(universe, spaceShip);
        this.destination = destination;
    }

    @Override
    public double control(double timeStep) {
        System.out.println("slowdown");

        Vector desiredVelocity = spaceShip
                .position()
                .vectorTo(destination.position())
                .direction()
                .times(
                        spaceShip.position().distanceTo(destination.position())
                );

        Vector acceleration = desiredVelocity.minus(spaceShip.velocity());

        spaceShip.setDesiredPointing(acceleration.direction());

        return Math.min(acceleration.magnitude(), 0.01);
    }

    private Controller nextController = null;

    @Override
    public void setNextController(Controller nextController) {
        this.nextController = nextController;
    }
}
