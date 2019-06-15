package simulation.gui.window;

import simulation.PlayPause;
import simulation.Simulation;
import simulation.universe.Universe;

import javax.swing.*;
import java.awt.*;

public class WindowImpl implements Window {
    private static final String PLAY_TEXT = "Play";
    private static final String PAUSE_TEXT = "Pause";

    private final Simulation simulation;
    private JButton playPauseButton = new JButton();

    public WindowImpl(Simulation simulation) {
        this.simulation = simulation;

        JFrame frame = new JFrame("going to titan");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        Window self = this;
        JPanel space = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                self.paint(g, simulation.universe());
            }
        };
        space.setBackground(Color.BLACK);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        playPauseButton.addActionListener(e -> {
            if (playPauseButton.getText().equals(PLAY_TEXT))
                simulation.pause();
            else
                simulation.play();
        });

        contentPane.add(space);

        bottomPanel.add(playPauseButton);
        contentPane.add(bottomPanel);

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

    public void paint(Graphics g, Universe universe) {

    }
}
