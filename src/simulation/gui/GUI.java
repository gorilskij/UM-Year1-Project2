package simulation.gui;

import simulation.PlayPause;
import simulation.universe.Universe;

public interface GUI extends PlayPause {
    void iterateGraphics(Universe universe);
}
