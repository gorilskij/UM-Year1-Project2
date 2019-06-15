package ship_support;

public class Thruster {

    private double exhaust_velocity = 3250.0;

    public double thrust(double fuel_consump, double mass) {
        return exhaust_velocity * Math.log(mass/(mass - fuel_consump));
    }

}
