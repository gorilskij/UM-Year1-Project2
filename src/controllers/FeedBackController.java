package controllers;

import body.SpaceShip;
import data.BodyFactory;
import general_support.Vector;

public class FeedBackController implements Controller {

    @Override
    public void control(SpaceShip ship, Vector velocity, String destintation) {
        Vector our_destination = BodyFactory.find(destintation).position();
        Vector ship_position = ship.position();

        Vector vectorToTitan =  ship_position.vectorTo(our_destination);
        Vector directionTotTitan = vectorToTitan.direction();
        if (!ship.pointing().equals(directionTotTitan)) {
            ship.setPointing(directionTotTitan);
        }
        ship.thrust();
    }
}
