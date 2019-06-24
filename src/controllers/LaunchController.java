package controllers;

import body.Planet;
import body.SpaceShip;
import general_support.Vector;
import simulation.universe.Universe;

public class LaunchController extends BaseController {
    private final Planet fromPlanet;

    public LaunchController(Universe universe, SpaceShip spaceShip, Planet fromPlanet) {
        super(universe, spaceShip);
        this.fromPlanet = fromPlanet;
    }

    // altitude above surface
    private double currentAltitude() {
        return spaceShip.position().distanceTo(fromPlanet.position()) - fromPlanet.radius();
    }
    private Vector upDirection() {
        return fromPlanet.position().vectorTo(spaceShip.position()).direction();
    }

    private void pointUp() {
        spaceShip.setDesiredPointing(upDirection());
    }

    public void setNextController(Controller c){
        super.nextController = c;
    }

    public double control(double ignored) {
        if (currentAltitude() < fromPlanet.radius() + 10_000) {
            pointUp();
            return 5;
        }

        // when done (close enough to titan)
        spaceShip.setController(nextController);
        return 0;
    }
}
