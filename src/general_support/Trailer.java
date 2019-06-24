package general_support;

import body.SpaceShip;
import body.interfaces.Moving;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Trailer {
    private final List<Vector> trail = new ArrayList<>();
    private final Moving body;
    private final int limit;
    private final double minDistance;

    private final Color color;

    // default unlimited, min distance 1e9m
    public Trailer(Moving body, double minDistance) {
        this(body, -1, minDistance);
    }

    public Trailer(Moving body, int limit, double minDistance) {
        this.body = body;
        this.limit = limit;
        this.minDistance = minDistance;

        if (body instanceof SpaceShip)
            color = Color.GREEN;
        else
            color = body.color();
    }

    public void push() {
        Vector position = body.position();

        synchronized (trail) {
            assert body.position() != null : "position null";
            assert trail.isEmpty() || trail.get(trail.size() - 1) != null : "null in trail " + trail;

            if (!trail.isEmpty() && trail.get(trail.size() - 1).distanceTo(position) < minDistance * body.velocity().magnitude())
                return;

            trail.add(position);
        }
    }

    private void paintVector(Vector vector, Graphics g, Vector centerPosition, Rotation rotation, double scale) {
        Point.Double pos = PaintingTools.convert(vector, centerPosition, rotation).toXYPoint();
        g.fillOval(
                (int) (pos.x * scale - 1),
                (int) (pos.y * scale - 1),
                2, 2
        );
    }

    public void paint(Graphics g, Vector centerPosition, Rotation rotation, double scale) {
        g.setColor(color);

        // note: don't replace with foreach, throws exception, intellij lies
        // (ConcurrentModificationException)
        for (int i = 0; i < trail.size() - 1; i++) {
            Vector vec = trail.get(i);
            Vector nxt = trail.get(i + 1);
            Point.Double vecPos = PaintingTools.convert(vec, centerPosition, rotation).toXYPoint();
            Point.Double nxtPos = PaintingTools.convert(nxt, centerPosition, rotation).toXYPoint();
            g.drawLine(
                    (int) (vecPos.x * scale - 1),
                    (int) (vecPos.y * scale - 1),
                    (int) (nxtPos.x * scale - 1),
                    (int) (nxtPos.y * scale - 1)
            );
        }

        if (limit > 0) {
            synchronized (trail) {
                if (trail.size() > limit)
                    trail.subList(0, trail.size() - limit).clear();
            }
        }
    }

    public void clear() {
        trail.clear();
    }
}
