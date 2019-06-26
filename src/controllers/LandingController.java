package controllers;

import body.SpaceShip;
import body.interfaces.Moving;
import body.interfaces.Round;
import general_support.Vector;
import simulation.universe.Universe;

public class LandingController extends BaseController {
    private final Moving parent;
    private final Moving target;

    public LandingController(Universe universe, SpaceShip spaceShip, Moving parent, Moving target) {
        super(universe, spaceShip);
        this.parent = parent;
        this.target = target;
    }

    // above sea level
    private double altitudeASL(Moving body) {
        return spaceShip.position().distanceTo(body.position()) - ((Round) body).radius();
    }

    private Vector relativeVelocity(Moving body) {
        return spaceShip.velocity().minus(body.velocity());
    }

    private Vector acceleration(Moving body, double speedTowardsBody) {
        Vector velocityToTarget = spaceShip
                .position()
                .vectorTo(body.position())
                .times(speedTowardsBody);

        return velocityToTarget.minus(relativeVelocity(body));
    }

    private double point(Moving body, double speedTowardsBody) {
        Vector acceleration = acceleration(body, speedTowardsBody);
        spaceShip.setDesiredPointing(acceleration.direction());
        return acceleration.magnitude();
    }

    private double pointReverse(Moving body, double speedFromBody) {
        Vector acceleration = acceleration(body, speedFromBody).inverse();
        spaceShip.setDesiredPointing(acceleration.direction());
        return acceleration.magnitude();
    }

    private void pointRetrograde(Moving body) {
        spaceShip.setDesiredPointing(relativeVelocity(body).inverse().direction());
    }

    private Vector relativeVelocityDown(Moving body) {
        return relativeVelocity(body).componentInDirectionOf(
                spaceShip.position().vectorTo(body.position()).direction()
        );
    }

    private int state = 0;

    private void state(int s) {
        if (state != s) {
            System.out.println("state " + s);
            state = s;
        }
    }

    public double control(double ignored) {
        if (altitudeASL(target) > 1e9 && relativeVelocity(parent).magnitude() > 1e4) {
            state(10);
            pointRetrograde(parent);
            return 0.2;
        }

        if (altitudeASL(target) > 1e9) {
            state(1);
            return Math.min(0.5, point(target, 100));
        }

        if (relativeVelocity(target).magnitude() > 5e3) {
            state(20);
            pointRetrograde(target);
            return 0.1;
        }

        if (altitudeASL(target) > 1e8) {
            state(2);
            return Math.min(0.2, point(target, 50));
        }

        if (relativeVelocity(target).magnitude() > 1e4) {
            state(30);
            pointRetrograde(target);
            return 0.5;
        }

        if (altitudeASL(target) < 3e5 && relativeVelocityDown(target).magnitude() > 1e2) {
            state(54);
            pointRetrograde(target);
            return Math.min(relativeVelocity(target).magnitude(), 20);
        }

        if (altitudeASL(target) < 5e5 && relativeVelocityDown(target).magnitude() > 1e2) {
            state(53);
            pointRetrograde(target);
            return Math.min(relativeVelocity(target).magnitude(), 5);
        }

        if (altitudeASL(target) < 5e6 && relativeVelocityDown(target).magnitude() > 1e2) {
            state(52);
            pointRetrograde(target);
            return Math.min(relativeVelocity(target).magnitude(), 1);
        }

        if (altitudeASL(target) < 5e7 && relativeVelocityDown(target).magnitude() > 1e3) {
            state(51);
            pointRetrograde(target);
            return Math.min(relativeVelocity(target).magnitude(), 0.1);
        }

        if (relativeVelocity(target).magnitude() > 1e3) {
            System.out.println("speed: " + relativeVelocity(target).magnitude());
            System.out.println("asl: " + altitudeASL(target));
            pointRetrograde(target);
            if (altitudeASL(target) > 5e6) {
                state(41);
                return 0.1;
            } else {
                state(42);
                return Math.min(0.5, relativeVelocity(target).magnitude());
            }
        }

        state(0);
        return 0;
    }

//    @Override
//    public double control(double timeStep) {
////        System.out.println("here");
////        System.out.println("altitude: " + altitudeASL());
//
////        if (altitudeASL(target) < 1e8) {
////            state(1);
////            return point(target, 100) / 100;
////        }
//
//        if (altitudeASL(target) < 1e8) {
//            state(0);
//            spaceShip.setDesiredPointing(relativeVelocity(target).inverse().direction());
//            return 0.5;
//        }
//
//        if (altitudeASL(target) < 1e8) {
//            if (relativeVelocity(target).magnitude() > 1e3) {
//                state(2);
//                spaceShip.setDesiredPointing(relativeVelocity(target).inverse().direction());
//                return 0.1;
//            } else {
//                state(22);
//                spaceShip.setDesiredPointing(relativeVelocity(target).inverse().direction());
//                return 0.01;
//            }
//        }
//
//        if (altitudeASL(parent) < 1e8) {
//            state(30);
//            return Math.min(10, pointReverse(parent, 1e4));
//        }
//
//        if (altitudeASL(target) > 1e6) {
//            if (relativeVelocity(target).magnitude() > 1e4) {
//                state(33);
//                spaceShip.setDesiredPointing(relativeVelocity(target).inverse().direction());
//                return 0.1;
//            } else {
//                state(3);
//                return Math.min(0.1, point(target, 10));
//            }
//        }
//
//        if (altitudeASL(target) > 1e5) {
//            state(4);
//            return Math.min(0.4, point(target, 10_000));
//        }
//
//        if (altitudeASL(target) > 1e3) {
//            state(6);
//            return Math.min(0.1, point(target, 5000));
//        }
//
//        if (altitudeASL(target) > 1) {
//            state(7);
//            return Math.min(1e-6, 1);
//        }
//
//        if (altitudeASL(parent) > 3e9) {
//            state(10);
//            return Math.min(0.05, point(parent, altitudeASL(parent) / 1e10));
//        }
//
//        return 0;
//    }

    private Controller nextController = null;

    @Override
    public void setNextController(Controller c) {
        nextController = c;
    }
}
