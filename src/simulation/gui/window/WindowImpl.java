package simulation.gui.window;

import body.interfaces.Body;
import simulation.Simulation;
import simulation.universe.Universe;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class WindowImpl implements Window {
    private static final String PLAY_TEXT = "Play";
    private static final String PAUSE_TEXT = "Pause";

    private static final double ZOOM = 1.2;

    private final Universe universe;

    private final JPanel space;
    private final JButton playPauseButton = new JButton();

    private final JLabel scaleLabel;

    private double scale = 1e-7;

    private String centerBodyName = null;

    private void setScale(double newScale) {
        scale = newScale;
        scaleLabel.setText(String.format(Locale.US, "1 : %.2e", 1 / newScale));
        paint();
    }

    public WindowImpl(Simulation simulation, Universe universe) {
        this.universe = universe;

        JFrame frame = new JFrame("going to titan");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        WindowImpl self = this;
        space = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                self.paintPanel(g);
            }
        };
        space.setBackground(Color.BLACK);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setLayout(new BoxLayout(bottomLeftPanel, BoxLayout.X_AXIS));

        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.X_AXIS));

        playPauseButton.addActionListener(e -> {
            if (playPauseButton.getText().equals(PLAY_TEXT))
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

        contentPane.add(space);

        bottomLeftPanel.add(playPauseButton);
        bottomLeftPanel.add(increaseScaleButton);
        bottomLeftPanel.add(decreaseScaleButton);

        bottomRightPanel.add(scaleLabel);

        contentPane.add(bottomLeftPanel);
        contentPane.add(bottomRightPanel);

        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(screenSize);
        frame.pack();
        frame.setVisible(true);
    }

    public void play() {
        playPauseButton.setText(PLAY_TEXT);
    }

    public void pause() {
        playPauseButton.setText(PAUSE_TEXT);
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
    }
}
