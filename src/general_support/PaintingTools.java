package general_support;

import body.interfaces.Round;

import java.awt.*;

public final class PaintingTools {
    public static Vector convert(Vector vector, Vector centerPosition, Rotation rotation) {
        Vector positioned = vector.minus(centerPosition);

        // don't rotate is vector is at/near origin
        if (positioned.magnitude() < 1e5)
            return positioned;

        Vector rotatedH = positioned.rotateAboutY(rotation.horizontal);
//        return rotatedH.rotateTo(new Vector(0, 0, 1).rotateAboutY(rotation.horizontal), rotation.vertical);
        return rotatedH.rotateAboutX(rotation.vertical);
    }

    public static void paintCircularObject(
            Graphics g,
            double scale,
            Round object,
            Vector centerPosition,
            Rotation rotation
    ) {
        g.setColor(object.color());
        Point.Double pos = convert(object.position(), centerPosition, rotation).toXYPoint();

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
