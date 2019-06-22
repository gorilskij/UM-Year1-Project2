package controllers;

import body.Planet;
import body.spaceship.SpaceShip;
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

    public double control() {
        System.out.println("Control instant");
        System.out.println("pos: " + spaceShip.position());
        System.out.println("alt: " + currentAltitude());
        System.out.println("vel: " + spaceShip.velocity());

        if (currentAltitude() < altitude / 100) {
            pointUp();
            return 20;
        }

        if (currentAltitude() < altitude / 50)  {
            pointAtAngle(10);
            return 10;
        }

        if (currentAltitude() < altitude) {
            return 0;
        }

        if (currentAltitude() >= altitude) {
            pointAtAngle(90);
            return 5;
        }

//        if (currentAltitude() < altitude / 2) {
//
//        }
        throw new IllegalStateException("done at altitude: " + currentAltitude());
    }
}
