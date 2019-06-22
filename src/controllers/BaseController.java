package controllers;

import body.spaceship.SpaceShip;
import simulation.universe.Universe;

public abstract class BaseController implements Controller {
    protected final Universe universe;
    protected final SpaceShip spaceShip;

    BaseController(Universe universe, SpaceShip spaceShip) {
        this.universe = universe;
        this.spaceShip = spaceShip;
    }
}
