package controllers;

import body.SpaceShip;
import general_support.Vector;
import ship_support.Thruster;

public interface Controller {

    /**
     * manipulate thrust system of a ship
     * name is shit, TODO: change the name
     * @param ship spaceship to thrust
     * @param velocity how much velocity to gain
     * @param destination where to fly
     */
    void control(SpaceShip ship, Vector velocity, String destination);

}
