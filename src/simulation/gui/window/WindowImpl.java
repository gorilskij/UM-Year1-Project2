package simulation.gui.window;

import body.interfaces.Body;
import simulation.Simulation;
import simulation.universe.Universe;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class WindowImpl implements Window {
    private boolean playing = false;
    private static final double ZOOM = 1.2;

    private final Simulation simulation;
    private final Universe universe;

    private final JPanel space;
    private final JButton playPauseButton = new JButton();

    private final JLabel scaleLabel;
    private final JLabel timePassedLabel;

    private double scale = 1e-9;

    private String centerBodyName = null;

    private void setScale(double newScale) {
        scale = newScale;
        scaleLabel.setText(String.format(Locale.US, "scale: 1 : %.2e", 1 / newScale));
        paint();
    }

    public WindowImpl(Simulation simulation, Universe universe) {
        this.simulation = simulation;
        this.universe = universe;

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
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

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

        scaleLabel = new JLabel();
        setScale(scale); // refresh scaleLabel

        timePassedLabel = new JLabel("-");

        contentPane.add(space);

        bottomPanel.add(playPauseButton);
        bottomPanel.add(increaseScaleButton);
        bottomPanel.add(decreaseScaleButton);

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

    private void translateToCenterBody(Graphics g) {
        if (centerBodyName == null) return;

        Body body = universe.getBodyByName(centerBodyName);
        Point.Double pos = body.position().toXYPoint(scale);
        g.translate((int) -pos.x, (int) -pos.y);
    }

    private void paintPanel(Graphics g) {
        g.translate(space.getWidth() / 2, space.getHeight() / 2);
//        translateToCenterBody(g);
        for (Body body : universe.allBodies())
            body.paint(g, scale);

        timePassedLabel.setText("  time passed: " + simulation.timePassedS() + "s");
    }
}
