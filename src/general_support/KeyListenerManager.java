package general_support;

import simulation.runner.BaseRunner;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

class KeyRunner extends BaseRunner {
    private final Lambda lambda;
    private final JComponent component;

    KeyRunner(Lambda lambda, JComponent component) {
        super(50_000_000); // TODO: maybe not 0
        this.lambda = lambda;
        this.component = component;
        pause();
    }

    public void doFrame() {
        lambda.call();
        component.repaint();
    }
}

public class KeyListenerManager {
    private final JComponent component;
    private final Map<Integer, KeyRunner> map = new HashMap<>();

    public KeyListenerManager(JComponent component) {
        this.component = component;

        component.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                KeyRunner runner = map.get(e.getKeyCode());
                if (runner != null)
                    runner.play();
            }

            public void keyReleased(KeyEvent e) {
                KeyRunner runner = map.get(e.getKeyCode());
                if (runner != null)
                    runner.pause();
            }
        });
    }

    public void addBinding(int keyCode, Lambda lambda) {
        KeyRunner runner = new KeyRunner(lambda, component);
        map.put(keyCode, runner);
    }
}
