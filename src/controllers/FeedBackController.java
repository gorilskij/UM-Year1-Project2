package controllers;

import body.spaceship.SpaceShip;
import general_support.Vector;

public class FeedBackController implements Controller {

    @Override
    public void control(SpaceShip ship) {
        if (ship.pointing() == null) {
            ship
                    .setPointing(
                            ship
                                    .directionTo(
                                            ship
                                                    .universe()
                                                    .getBodyByName("titan")
                                    )
                    );
        }
        double angleToRotate = ship
                .pointing()
                .angleBetween(
                        ship
                                .directionTo(
                                        ship
                                                .universe()
                                                .getBodyByName("titan")
                                )
                );
    }
}
