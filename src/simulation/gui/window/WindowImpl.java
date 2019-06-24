package simulation.gui.window;

import body.SpaceShip;
import body.interfaces.Body;
import body.interfaces.Trailing;
import general_support.Rotation;
import simulation.Simulation;
import simulation.universe.Universe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

import java.util.List;

public class WindowImpl implements Window {
    private boolean playing = false;
    private static final double ZOOM_FACTOR = 1.5;

    private final Simulation simulation;
    private final Universe universe;

    private Rotation rotation = new Rotation(0, 0);

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

    private Body centerBody;

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
        centerBody = universe.getBodyByName("sun");

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
        increaseScaleButton.addActionListener(e -> setScale(scale * ZOOM_FACTOR));

        JButton decreaseScaleButton = new JButton("-");
        decreaseScaleButton.addActionListener(e -> setScale(scale / ZOOM_FACTOR));


        JButton fastButton = new JButton("fast");
        fastButton.addActionListener(e -> simulation.setUniverseRunnerMinFrameTime(0));

        JButton slowButton = new JButton("slow");
        slowButton.addActionListener(e -> simulation.setUniverseRunnerMinFrameTime(100_000));

        JButton slow2Button = new JButton("slow 2");
        slow2Button.addActionListener(e -> simulation.setUniverseRunnerMinFrameTime(5_000_000));

        JButton slow3Button = new JButton("slow 3");
        slow3Button.addActionListener(e -> simulation.setUniverseRunnerMinFrameTime(10_000_000));


        JButton clearTrailsButton = new JButton("clear trails");
        clearTrailsButton.addActionListener(e -> {
            for (Trailing trailing : universe.trailingBodies())
                trailing.trailer().clear();
        });


        JSlider horizontalRotationSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        JLabel horizontalRotationLabel = new JLabel("  0°");
        horizontalRotationSlider.addChangeListener(e -> {
            rotation = rotation.withHorizontal(horizontalRotationSlider.getValue());
            horizontalRotationLabel.setText(String.format("%3d°", (int) rotation.horizontal));
            paint();
        });

        JSlider verticalRotationSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        JLabel verticalRotationLabel = new JLabel("  0°");
        verticalRotationSlider.addChangeListener(e -> {
            rotation = rotation.withVertical(verticalRotationSlider.getValue());
            verticalRotationLabel.setText(String.format("%3d°", (int) rotation.vertical));
            paint();
        });

        scaleLabel = new JLabel();
        setScale(scale); // refresh scaleLabel

        timePassedLabel = new JLabel("-");

        contentPane.add(space);

        bottomPanel.add(playPauseButton);
        bottomPanel.add(increaseScaleButton);
        bottomPanel.add(decreaseScaleButton);
        bottomPanel.add(fastButton);
        bottomPanel.add(slowButton);
        bottomPanel.add(slow2Button);
        bottomPanel.add(slow3Button);
        bottomPanel.add(clearTrailsButton);

        JPanel bodySelectorPanel = new JPanel();
        bodySelectorPanel.setSize(new Dimension(0, 0));
        bodySelectorPanel.add(bodySelector);

        bottomPanel.add(bodySelectorPanel);

        bottomPanel.add(scaleLabel);
        bottomPanel.add(timePassedLabel);

        bottomPanel.add(horizontalRotationLabel);
        bottomPanel.add(horizontalRotationSlider);

        bodySelectorPanel.add(verticalRotationLabel);
        bottomPanel.add(verticalRotationSlider);

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

        List<Body> allBodies = universe.allBodies();
        for (int i = 0; i < allBodies.size(); i++)
            allBodies.get(i).paint(g, centerBody.position(), rotation, scale);

        timePassedLabel.setText("  time passed: " + simulation.timePassedS() + "s");
    }
}
