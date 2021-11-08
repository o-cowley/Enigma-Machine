package ui.Tools;

import model.InUseRotors;

import javax.swing.*;
import java.awt.*;

// A PopUp window that prompts a user to adjust a selected rotor's internal setting before encryption
public class EditPopUp extends JFrame {
    //TODO: needs to know the main managing frame for functionality access

    JPanel pane;
    JSpinner spinner;
    JButton done;
    JButton close;
    JLabel label;

    //EFFECTS: A constructor that initializes an Edit popup to allow a user to set a rotor's internal setting
    public EditPopUp(Point p, int rotor, int initialSetting) {
        this.setLayout(new GridBagLayout());

        pane = new JPanel(new GridBagLayout());
        initializeButtons(rotor, initialSetting);
        addButtons();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(pane.getPreferredSize());
        setResizable(false);
        setLocation(p);
        setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: sets up the buttons, text, settings, size, and components to display on the popup
    private void initializeButtons(int rotorLabel, int initialSetting) {
        SpinnerModel spinnerModel = new SpinnerNumberModel(0,0,25,1);
        spinner = new JSpinner(spinnerModel);
        setDimensions(spinner,75,50);

        JComponent editor = spinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setColumns(4);

        label = new JLabel("<html>This is for rotor type: " + rotorLabel + "<br> the previous setting was: "
                + initialSetting + "</html>");
        setDimensions(label, 50, 50);

        done = new JButton("Done");
        setDimensions(done, 50, 50);
        done.addActionListener((event) -> {
            System.out.println(spinner.getValue());
        });

        close = new JButton("Close");
        setDimensions(spinner,50,50);
        close.addActionListener((event) -> {
            this.dispose();
        });
    }

    //MODIFIES: this
    //EFFECTS: adds the buttons and other Swing components to the panel
    private void addButtons() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.weightx = 1;
        setGridPos(c,0,0);
        c.gridwidth = 2;
        pane.add(spinner, c);
        c.gridwidth = 1;
        setGridPos(c,2,0);
        pane.add(close, c);
        setGridPos(c,3,0);
        pane.add(done, c);
        setGridPos(c,0,1);
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(label, c);
        setGridPos(c,0,0);
        add(pane, c);
    }

    //MODIFIES: this, c
    //EFFECTS: sets a given components preferred size according to x and y dimensions
    private void setDimensions(Component c, int x, int y) {
        c.setPreferredSize(new Dimension(x, y));
    }

    //MODIFIES: this, c
    //EFFECTS: sets the constraints of x and y position to use the GridBagConstraints object to add a component to
    // the main panel
    private void setGridPos(GridBagConstraints c, int x, int y) {
        c.gridx = x;
        c.gridy = y;
    }



    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setLocationRelativeTo(null);

        JButton but = new JButton("pop");
        but.setPreferredSize(new Dimension(100,100));
        but.addActionListener((event) -> new EditPopUp(new Point(mainFrame.getX() + 100, mainFrame.getY()),
                5, 5));

        mainFrame.add(but);

        mainFrame.setSize(mainFrame.getPreferredSize());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }



}
