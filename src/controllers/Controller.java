package controllers;



public interface Controller {

    /**
     * manipulate thrust system of a ship
     */
    double control(double timeStep);

    /**
     * specify which controller to use after current one
     */
    void setNextController(Controller c);

}
