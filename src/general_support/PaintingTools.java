package general_support;

import body.interfaces.Round;

import java.awt.*;

public final class PaintingTools {
    public static void paintCircularObject(Graphics g, double scale, Round object, Vector centerPosition) {
        g.setColor(object.color());
        Point.Double pos = object.position().minus(centerPosition).toXYPoint();
        int diameter = (int) Math.round(object.radius() * 2 * scale);

        g.fillOval(
                (int) Math.round((pos.x - object.radius()) * scale),
                (int) Math.round((pos.y - object.radius()) * scale),
                diameter, diameter
        );

        paintHighlightCircle(g, scale, pos);
        paintLabel(g, scale, pos, object.name());
    }

    // assumes the color is already set
    public static void paintHighlightCircle(Graphics g, double scale, Point.Double pos) {
        g.drawOval(
                (int) Math.round(pos.x * scale) - 5,
                (int) Math.round(pos.y * scale) - 5,
                10, 10
        );
    }

    public static void paintLabel(Graphics g, double scale, Point.Double pos, String name) {
        g.setColor(Color.WHITE);
        g.drawString(
                name,
                (int) Math.round(pos.x * scale) - 5,
                (int) Math.round(pos.y * scale) - 5
        );
    }

    public static void paintPointing(Graphics g, Color color, int length, Point.Double pos, Vector pointing) {
        g.setColor(color);
        Point.Double vec = pointing.direction().toXYPoint();
        g.drawLine(
                (int) pos.x, (int) pos.y,
                (int) (pos.x + vec.x * length),
                (int) (pos.y + vec.y * length)
        );
    }
}
