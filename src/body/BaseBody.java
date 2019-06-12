package body;

import java.awt.*;

public abstract class BaseBody {
    private final String name;
    private final Color color;

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    BaseBody(String name, Color color) {
        this.name = name;
        this.color = color;
    }
}
