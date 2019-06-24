package controllers;

import body.SpaceShip;
import simulation.universe.Universe;

public abstract class BaseController implements Controller {
    protected final Universe universe;
    final SpaceShip spaceShip;
    Controller nextController;

    BaseController(Universe universe, SpaceShip spaceShip) {
        this.universe = universe;
        this.spaceShip = spaceShip;
        this.nextController = null;
    }
}
