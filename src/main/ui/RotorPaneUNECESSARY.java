package ui;

import javax.swing.*;
import java.awt.*;

public class RotorPaneUNECESSARY extends JPanel {

    private int value = 0;

    public RotorPaneUNECESSARY() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(100,100));

        makeRotorInteraction();
    }

    private void makeRotorInteraction() {
        JButton button = new JButton("Reset");
        JLabel label = new JLabel("Test");
        SpinnerModel spinnerModel = new SpinnerNumberModel(0,0,25,1);
        JSpinner spinner = new JSpinner(spinnerModel);

        spinner.setMaximumSize(new Dimension(50,50));
        spinner.setPreferredSize(new Dimension(20,20));


        label.setMaximumSize(new Dimension(300,20));
        label.setPreferredSize(new Dimension(100,20));

        button.setMaximumSize(new Dimension(60,60));
        button.setPreferredSize(new Dimension(20,20));

        button.addActionListener((event) -> {
            value = 0;
            spinner.setValue(value);
        });
        spinner.addChangeListener((event) -> {
            value = (int) spinner.getValue();
            label.setText("This is the value: " + value);
        });

        add(spinner);
        add(button);
        add(label);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Frame Demo");
        RotorPaneUNECESSARY rp = new RotorPaneUNECESSARY();

        frame.setLayout(new BorderLayout());
        frame.setSize(500,150);
        frame.setLocationRelativeTo(null);
        frame.add(rp, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setVisible(true);



    }
}
