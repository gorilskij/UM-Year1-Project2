package general_support;

import body.SpaceShip;
import body.interfaces.Moving;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Trailer {
    private static final double MIN_DISTANCE = 1e9;

    private final List<Vector> trail = new ArrayList<>();
    private final Moving body;

    private final Color color;

    public Trailer(Moving body) {
        this.body = body;

        if (body instanceof SpaceShip)
            color = Color.GREEN;
        else
            color = body.color();
    }

    public void push() {
        Vector position = body.position();

        if (!trail.isEmpty() && trail.get(trail.size() - 1).distanceTo(position) < MIN_DISTANCE)
            return;

        trail.add(position);
    }

    public void paint(Graphics g, Vector centerPosition, int rotationDeg, double scale) {
        g.setColor(color);
        // note: don't replace with foreach, throws exception, intellij lies
        // (ConcurrentModificationException)
        for (int i = 0; i < trail.size(); i++) {
            Point.Double pos = PaintingTools.convert(trail.get(i), centerPosition, rotationDeg).toXYPoint();
            g.fillOval(
                    (int) (pos.x * scale - 1),
                    (int) (pos.y * scale - 1),
                    2, 2
            );
        }
    }

    public void clear() {
        trail.clear();
    }
}
