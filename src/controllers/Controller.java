package controllers;

import body.spaceship.SpaceShip;
import general_support.Vector;

public interface Controller {

    /**
     * manipulate thrust system of a ship
     * perform rotation within the method
     * @return acceleration magnitude to be applied in the direction the spaceship is pointing
     */
    double control();

}
