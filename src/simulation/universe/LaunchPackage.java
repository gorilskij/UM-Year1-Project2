package simulation.universe;

import body.SpaceShip;
import controllers.Controller;

import java.util.List;

public class LaunchPackage {
    private SpaceShip spaceShip;
    private final List<Controller> controllers;
    private final long time;

    LaunchPackage(SpaceShip spaceShip, List<Controller> controllers, long time) {
        this.spaceShip = spaceShip;
        this.controllers = controllers;
        this.time = time;
    }

    public SpaceShip ship() {
        assert spaceShip != null : "already launched";
        SpaceShip ship = spaceShip;
        spaceShip = null;
        return ship;
    }

    public List<Controller> controllers() {
        return controllers;
    }

    public long time() {
        return time;
    }
}
