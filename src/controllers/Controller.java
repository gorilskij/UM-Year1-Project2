package controllers;

import body.spaceship.SpaceShip;
import general_support.Vector;

public interface Controller {

    /**
     * manipulate thrust system of a ship
     * name is shit, TODO: change the name
     * @param ship spaceship to thrust
     */
    void control(SpaceShip ship);

}
