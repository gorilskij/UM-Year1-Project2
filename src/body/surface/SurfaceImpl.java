package body.surface;

import body.Planet;
import body.interfaces.Body;
import body.interfaces.Moving;
import body.interfaces.Round;
import general_support.Vector;

public class SurfaceImpl implements Surface {

    private final double radius;
    private final Body parent;
    public static final double EPSILON = 1;

    public SurfaceImpl(Round round) {
        this.parent = round;
        this.radius = round.radius();
    }

    public void interactWithBody(Body body) {
        if (parent.position().distanceTo(body.position()) - radius < EPSILON) {
            ((Moving) body).setVelocity(((Moving) parent).velocity());
        }
    }
}
