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

    // up from earth
    private Vector verticalVelocity() {
        return null;
    }

    private void pointUp() {
        spaceShip.setPointing(fromPlanet.position().vectorTo(spaceShip.position()).direction());
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
//        if ()
        return 0;
    }

    public double control() {
//        System.out.println("Control instant");
//        System.out.println("pos: " + spaceShip.position());
//        System.out.println("alt: " + currentAltitude());
//        System.out.println("vel: " + spaceShip.velocity());

        if (currentAltitude() < altitude) {
            pointUp();
            return 20;
        }

//        if (currentAltitude() < altitude / 2) {
//
//        }

        spaceShip.setController(null);
        return 0;
    }
}
