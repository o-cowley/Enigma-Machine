package ui;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.EventObject;

public class RotorPane extends JPanel {

    private int value = 0;

    public RotorPane() {

        makeRotorInteraction();

        setLayout(new GridLayout(1, 1));
        setVisible(true);
    }

    private void makeRotorInteraction() {
        JButton button = new JButton("Reset");
        JLabel label = new JLabel("Test");
        SpinnerModel spinnerModel = new SpinnerNumberModel(0,0,25,1);
        JSpinner spinner = new JSpinner(spinnerModel);

        button.addActionListener((event) -> {
            value = 0;
            spinner.setValue(value);
        });

        spinner.setMaximumSize(new Dimension(100,100));
        spinner.addChangeListener((event) -> {
            value = (int) spinner.getValue();
            label.setText("This is the value: " + value);
        });

        add(spinner);
        add(button);
        add(label);



    }

    private void changeThing(EventObject e) {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Frame Demo");
        RotorPane rp = new RotorPane();

        frame.setSize(600,100);
        frame.add(rp);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setVisible(true);



    }
}
