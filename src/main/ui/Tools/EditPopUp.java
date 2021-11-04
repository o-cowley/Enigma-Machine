package Tools;

import javax.swing.*;
import java.awt.*;

public class EditPopUp extends JPanel {
    JSpinner spinner;
    JButton done;
    JButton close;
    JLabel label;

    int number;

    public EditPopUp() {
        setLayout(new FlowLayout());

        setSize(600,600);

        initializeButtons();

        add(spinner);
        add(done);
        add(close);
        add(label);

    }

    private void initializeButtons() {
        number = 0;
        SpinnerModel spinnerModel = new SpinnerNumberModel(0,0,25,1);
        spinner = new JSpinner(spinnerModel);

        label = new JLabel("Hi");


        done = new JButton("Done");
        done.addActionListener((event) -> {
            this.remove(spinner);
            this.revalidate();
            this.repaint();
        });

        close = new JButton("Close");
        close.addActionListener((event) -> System.exit(0));
    }



    public static void main(String[] args) {
        JPopupMenu pane = new JPopupMenu();

        JPanel thingy = new EditPopUp();


        pane.add(thingy);

        pane.setVisible(true);
    }
}
