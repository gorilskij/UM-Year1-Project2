//package controllers;
//
//import body.SpaceShip;
//import body.interfaces.Body;
//import general_support.Vector;
//
//public class FeedBackController implements Controller {
//
//    @Override
//    public double control(SpaceShip ship, Vector velocity) {
//        Vector ship_position = ship.position();
//        Body destination = ship.universe().getBodyByName("titan");
//        return 1;
////        Vector vectorToTitan =  ship_position.vectorTo(our_destination);
////        Vector directionTotTitan = vectorToTitan.direction();
////        if (!ship.pointing().equals(directionTotTitan)) {
////            ship.setPointing(directionTotTitan);
////        ship.thrust();
//    //  }
//    }
//}
