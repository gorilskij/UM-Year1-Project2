package simulation.gui.window;

import body.spaceship.SpaceShip;
import body.interfaces.Body;
import general_support.Vector;
import simulation.Simulation;
import simulation.universe.Universe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

import java.util.List;

public class WindowImpl implements Window {
    private boolean playing = false;
    private static final double ZOOM = 1.2;

    private final Simulation simulation;
    private final Universe universe;

    private final JPanel space;
    private final JButton playPauseButton = new JButton();
    private final BodySelector bodySelector = new BodySelector(this) {
        @Override
        public Body[] refreshList() {
            List<Body> bodies = new ArrayList<>();
            for (Body body : universe.allBodies()) {
                if (
                        body.name().toLowerCase().equals("sun")
                                || body.name().toLowerCase().equals("earth")
                                || body.name().toLowerCase().equals("saturn")
                                || body.name().toLowerCase().equals("titan")
                                || body instanceof SpaceShip
                ) {
                    bodies.add(body);
                }
            }

            Body[] array = new Body[bodies.size()];
            for (int i = 0; i < array.length; i++)
                array[i] = bodies.get(i);

            return array;
        }
    };

    private final JLabel scaleLabel;
    private final JLabel timePassedLabel;

    private double scale = 1e-9;

    private Body centerBody = null;

    public void setCenterBody(Body body) {
        if (body == null)
            throw new IllegalStateException("tried to set centerBody to null");
        centerBody = body;
        paint();
    }

    private void setScale(double newScale) {
        scale = newScale;
        scaleLabel.setText(String.format(Locale.US, "scale: 1 : %.2e", 1 / newScale));
        paint();
    }

    public WindowImpl(Simulation simulation, Universe universe) {
        this.simulation = simulation;
        this.universe = universe;
        bodySelector.refresh();

        JFrame frame = new JFrame("going to titan");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        space = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                WindowImpl.this.paintPanel(g);
            }
        };
        space.setBackground(Color.BLACK);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setMaximumSize(new Dimension(bottomPanel.getMaximumSize().width, 10));

        playPauseButton.addActionListener(e -> {
            if (playing)
                simulation.pause();
            else
                simulation.play();
        });

        JButton increaseScaleButton = new JButton("+");
        increaseScaleButton.addActionListener(e -> setScale(scale * ZOOM));

        JButton decreaseScaleButton = new JButton("-");
        decreaseScaleButton.addActionListener(e -> setScale(scale / ZOOM));

        JButton fastButton = new JButton("fast");
        fastButton.addActionListener(e -> simulation.setUniverseRunnerMinFrameTime(100));

        JButton slowButton = new JButton("slow");
        slowButton.addActionListener(e -> simulation.setUniverseRunnerMinFrameTime(0));

        scaleLabel = new JLabel();
        setScale(scale); // refresh scaleLabel

        timePassedLabel = new JLabel("-");

        contentPane.add(space);

        bottomPanel.add(playPauseButton);
        bottomPanel.add(increaseScaleButton);
        bottomPanel.add(decreaseScaleButton);
        bottomPanel.add(fastButton);
        bottomPanel.add(slowButton);

        JPanel bodySelectorPanel = new JPanel();
        bodySelectorPanel.setSize(new Dimension(0, 0));
        bodySelectorPanel.add(bodySelector);

        bottomPanel.add(bodySelectorPanel);

        bottomPanel.add(scaleLabel);
        bottomPanel.add(timePassedLabel);

        contentPane.add(bottomPanel);

        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(screenSize);
        frame.pack();
        frame.setVisible(true);
    }

    public void play() {
        playing = true;
        playPauseButton.setText("Pause");
    }

    public void pause() {
        playing = false;
        playPauseButton.setText("Play");
    }

    public void paint() {
        space.repaint();
    }

    public void shipLaunched() {
        bodySelector.refresh();
    }

    private void paintPanel(Graphics g) {
        g.translate(space.getWidth() / 2, space.getHeight() / 2);
        for (Body body : universe.allBodies()) {
            if (centerBody == null)
                body.paint(g, Vector.ZERO, scale);
            else
                body.paint(g, centerBody.position(), scale);
        }

        timePassedLabel.setText("  time passed: " + simulation.timePassedS() + "s");
    }
}
