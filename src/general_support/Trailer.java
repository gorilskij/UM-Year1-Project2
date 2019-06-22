package general_support;

import body.interfaces.Moving;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Trailer {
    private static final double MIN_DISTANCE = 1e6;

    private final List<Vector> trail = new ArrayList<>();
    private final Moving body;

    public Trailer(Moving body) {
        this.body = body;
    }

    public void push() {
        Vector position = body.position();

        if (!trail.isEmpty() && trail.get(trail.size() - 1).distanceTo(position) < MIN_DISTANCE)
            return;

        trail.add(position);
    }

    public void paint(Graphics g, Vector centerPosition, double scale) {
        g.setColor(new Color(0, 255, 0));
        // note: don't replace with foreach, throws exception, intellij lies
        for (int i = 0; i < trail.size(); i++) {
            Point.Double pos = trail.get(i).minus(centerPosition).toXYPoint();
            g.fillOval(
                    (int) (pos.x * scale - 1),
                    (int) (pos.y * scale - 1),
                    2, 2
            );
        }
    }
}
