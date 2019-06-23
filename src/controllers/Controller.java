package controllers;



public interface Controller {

    /**
     * manipulate thrust system of a ship
     */
    double control(double timeStep);

}
