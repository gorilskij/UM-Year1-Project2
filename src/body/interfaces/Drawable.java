package body.interfaces;

import body.interfaces.granular.GetPosition;

import java.awt.*;

public interface Drawable extends GetPosition {
    void draw(Graphics g);
}
