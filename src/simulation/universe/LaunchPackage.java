package simulation.universe;

import body.SpaceShip;
import controllers.Controller;

import java.util.ArrayList;
import java.util.List;

public class LaunchPackage {
    private SpaceShip spaceShip;
    private String launchBody;
    private List<Controller> controllers;
    private final long time;

    LaunchPackage(SpaceShip spaceShip, String launchBody, long time) {
        this.spaceShip = spaceShip;
        this.time = time;
        this.launchBody = launchBody;
        this.controllers = new ArrayList<>();
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

    public String launchBody(){ return launchBody;}

    public void addController(Controller c) {
        controllers.add(c);
        if (controllers.size() > 1)
            controllers.get(controllers.size()-1).setNextController(c);
        else
            spaceShip.setController(c);
    }

    public List<Controller> getControllers() {
        return controllers;
    }
}
