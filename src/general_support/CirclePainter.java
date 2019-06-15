package general_support;

import java.awt.*;

public final class CirclePainter {
    public static void paintCircle(Graphics g, Vector position, double radius, Color color) {
        g.setColor(color);
        Point.Double pos = position.toXYPoint();
        int diameter = (int) Math.round(radius * 2);
        g.fillOval(
                (int) Math.round(pos.x - radius),
                (int) Math.round(pos.y - radius),
                diameter, diameter
        );
    }
}
