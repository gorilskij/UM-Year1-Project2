package body.surface;

import body.Planet;
import body.interfaces.Body;
import body.interfaces.Round;

public class SurfaceImpl implements Surface {

    private final double radius;

    public SurfaceImpl(Round round) {
        this.radius = round.radius();
    }
}
