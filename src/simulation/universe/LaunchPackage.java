package simulation.universe;

import body.SpaceShip;

public class LaunchPackage {
    private SpaceShip spaceShip;
    private final long time;

    LaunchPackage(SpaceShip spaceShip, long time) {
        this.spaceShip = spaceShip;
        this.time = time;
    }

    public SpaceShip ship() {
        assert spaceShip != null : "already launched";
        SpaceShip ship = spaceShip;
        spaceShip = null;
        return ship;
    }

    public long time() {
        return time;
    }
}
