package ship_support;

public class ThrustSystem {

    // TODO: add more thrusters(if we need it for rotation)
    private Thruster t_left = new Thruster();
    private Thruster t_right = new Thruster();
    private Thruster t = new Thruster();

    public double thrust(double f, double m) {
        return  t.thrust(f, m);
    }

    public double rotate_right(double f, double m) {
        return t_left.thrust(f, m);
    }

    public double rotate_left(double f, double m) {
        return t_right.thrust(f, m);
    }
}
