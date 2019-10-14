package controllers;

import body.SpaceShip;
import simulation.universe.Universe;

public abstract class BaseController implements Controller {
    protected final Universe universe;
    protected final SpaceShip spaceShip;
    protected Controller nextController;

    BaseController(Universe universe, SpaceShip spaceShip) {
        this.universe = universe;
        this.spaceShip = spaceShip;
        this.nextController = null;
    }

}
