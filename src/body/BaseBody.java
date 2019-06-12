package body;

import body.interfaces.Body;

import java.awt.*;

public abstract class BaseBody implements Body {
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
