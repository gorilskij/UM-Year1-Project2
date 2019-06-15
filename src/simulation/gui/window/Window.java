package simulation.gui.window;

import simulation.PlayPause;
import simulation.universe.Universe;

import java.awt.*;

public interface Window extends PlayPause {
    void paint(Graphics g, Universe universe);
}
