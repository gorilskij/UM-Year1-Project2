package body.Exceptions;

import body.SpaceShip;
import body.interfaces.Body;
import body.interfaces.Moving;

public abstract class SpaceShipException extends RuntimeException {
    public SpaceShipException(String whatHappened, SpaceShip spaceShip, Body body) {
        super(spaceShip.name()
                + " "
                + whatHappened
                + " on "
                + body.name()
                + " with a speed of "
                + spaceShip.velocity().distanceTo(((Moving) body).velocity())
                + "m/s"
        );
    }

    public static final class Crashed extends SpaceShipException {
        public Crashed(SpaceShip spaceShip, Body body) {
            super("crashed", spaceShip, body);
        }
    }

    public static final class Landed extends SpaceShipException {
        public Landed(SpaceShip spaceShip, Body body) {
            super("successfully landed", spaceShip, body);
        }
    }
}
