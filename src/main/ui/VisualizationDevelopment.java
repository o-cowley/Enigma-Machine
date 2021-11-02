package ui;

import javax.swing.*;
import java.awt.*;

public class VisualizationDevelopment extends JFrame {
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private JOptionPane testPop;

    private JPanel testPanel;
    private JButton[] keys;

    public VisualizationDevelopment() {
        testPop = new JOptionPane();
        testPop.showConfirmDialog(null, "Hello");
        testPop.setVisible(true);

        desktop = new JDesktopPane();
        setContentPane(desktop);
        setTitle("Hello this is me");
        setSize(400, 400);

        controlPanel = new JInternalFrame("Control", true, true, false);
        controlPanel.setLayout(new BorderLayout());

        testPanel = new JPanel();
        testPanel.setLayout(new GridLayout(3, 4));
        addButtons(testPanel);
        testPanel.setVisible(true);
        controlPanel.add(testPanel, BorderLayout.CENTER);

        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
        setVisible(true);
    }

    private void addButtons(JPanel p) {
        keys = new JButton[12];

        for (int i = 0; i < 9; i++) {
            keys[i] = new JButton(Integer.toString(i + 1));

            p.add(keys[i]);
        }

        keys[9] = new JButton("Clear");
        p.add(keys[9]);

        keys[10] = new JButton("0");
        p.add(keys[10]);

        keys[11] = new JButton("#");
        p.add(keys[11]);
    }




    public static void main(String[] args) {
        new VisualizationDevelopment();
    }



}
