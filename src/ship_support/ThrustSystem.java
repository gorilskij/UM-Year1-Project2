package ship_support;

public class ThrustSystem {

    // TODO: add more thrusters(if we need it for rotation)
    private Thruster t1 = new Thruster();

    public double thrust(double f, double m) {
        return  t1.thrust(f, m);
    }


}
