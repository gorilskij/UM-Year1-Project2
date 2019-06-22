package controllers;

import body.Planet;
import body.SpaceShip;
import general_support.Vector;
import simulation.universe.Universe;

public class LaunchController extends BaseController {
    private final Planet fromPlanet;
    private final double altitude; // above surface

    public LaunchController(Universe universe, SpaceShip spaceShip, Planet fromPlanet, double altitude) {
        super(universe, spaceShip);

        assert altitude > fromPlanet.radius();

        this.fromPlanet = fromPlanet;
        this.altitude = altitude;

        pointUp();
    }

    // altitude above surface
    private double currentAltitude() {
        return spaceShip.position().distanceTo(fromPlanet.position()) - fromPlanet.radius();
    }

    private Vector currentVelocity() {
        return spaceShip.velocity().minus(fromPlanet.velocity());
    }

    private Vector upDirection() {
        return fromPlanet.position().vectorTo(spaceShip.position()).direction();
    }

    // up from earth
    private Vector verticalVelocity() {
        return currentVelocity().componentInDirectionOf(upDirection());
    }

    private void pointUp() {
        spaceShip.setPointing(upDirection());
    }

    private void pointAtAngle(double angle) {
        assert angle >= 0 && angle <= 90;
        Vector up = fromPlanet.position().vectorTo(spaceShip.position()).direction();
        Vector deg90 = fromPlanet.velocity().direction();
        Vector actual = up.times((90 - angle) / 90).plus(deg90.times(angle / 90));
        spaceShip.setPointing(actual);
    }

    private double firstPhaseControl() {
        pointUp();
        if (verticalVelocity().magnitude() < 100) {
            return Math.min(10, 100 - verticalVelocity().magnitude());
        }

        return 0;
    }

    public double control(double ignored) {

        if (currentAltitude() < altitude)
            return firstPhaseControl();

        spaceShip.setController(new PID(universe,
                spaceShip,
                universe.getBodyByName("titan"),
                1E-11, 1E-17, 1E-4
        ));
        return 0;
    }
}
